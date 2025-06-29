package com.voting.service;

import com.voting.dto.VoteRequest;
import com.voting.entity.*;
import com.voting.repository.*;
import com.voting.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private ElectionRepository electionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private BlockchainTransactionRepository blockchainTransactionRepository;
    
    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private BlockchainService blockchainService;

    public Vote castVote(VoteRequest request, Long voterId) {
        // Check if election exists and is active
        Election election = electionRepository.findById(request.getElectionId())
                .orElseThrow(() -> new RuntimeException("Election not found"));
        
        if (!election.isActive()) {
            throw new RuntimeException("Election is not active");
        }

        // Check if voter exists
        User voter = userRepository.findById(voterId)
                .orElseThrow(() -> new RuntimeException("Voter not found"));

        // Check if voter has already voted
        if (voteRepository.existsByElectionIdAndVoterId(request.getElectionId(), voterId)) {
            throw new RuntimeException("Voter has already voted in this election");
        }

        // Check if candidate exists
        Candidate candidate = candidateRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // Create ballot data
        String ballotData = request.getCandidateId().toString();
        String encryptedBallot = encryptionUtil.encryptBallot(ballotData);
        String ballotHash = encryptionUtil.generateBallotHash(ballotData);

        // Create vote
        Vote vote = new Vote(election, voter, encryptedBallot, ballotHash);
        vote = voteRepository.save(vote);

        // Real blockchain transaction
        try {
            var receipt = blockchainService.sendTransaction(vote.getId() + ballotHash);
            BlockchainTransaction transaction = new BlockchainTransaction(vote, receipt.getTransactionHash());
            transaction.setBlockNumber(receipt.getBlockNumber().longValue());
            transaction.setGasUsed(receipt.getGasUsed().longValue());
            transaction.setStatus(receipt.isStatusOK() ? BlockchainTransaction.TransactionStatus.CONFIRMED : BlockchainTransaction.TransactionStatus.FAILED);
            blockchainTransactionRepository.save(transaction);
            // Update vote with real transaction ID
            vote.setTransactionId(receipt.getTransactionHash());
            vote.setIsVerified(receipt.isStatusOK());
        } catch (Exception e) {
            // If blockchain fails, mark as failed
            BlockchainTransaction transaction = new BlockchainTransaction(vote, "BLOCKCHAIN_FAILED");
            transaction.setStatus(BlockchainTransaction.TransactionStatus.FAILED);
            blockchainTransactionRepository.save(transaction);
            vote.setIsVerified(false);
        }
        return voteRepository.save(vote);
    }

    public Optional<Vote> getVoteById(Long id) {
        return voteRepository.findById(id);
    }

    public List<Vote> getVotesByElection(Long electionId) {
        return voteRepository.findAll().stream()
                .filter(vote -> vote.getElection().getId().equals(electionId))
                .collect(Collectors.toList());
    }

    public Map<Long, Long> getElectionResults(Long electionId) {
        List<Vote> votes = getVotesByElection(electionId);
        
        return votes.stream()
                .collect(Collectors.groupingBy(
                    vote -> {
                        try {
                            String decryptedBallot = encryptionUtil.decryptBallot(vote.getEncryptedBallot());
                            return Long.parseLong(decryptedBallot);
                        } catch (Exception e) {
                            return -1L; // Invalid vote
                        }
                    },
                    Collectors.counting()
                ));
    }

    public boolean verifyVote(String transactionId) {
        return voteRepository.findAll().stream()
                .anyMatch(vote -> transactionId.equals(vote.getTransactionId()) && vote.isVerified());
    }

    public long getTotalVotesForElection(Long electionId) {
        return getVotesByElection(electionId).size();
    }
} 
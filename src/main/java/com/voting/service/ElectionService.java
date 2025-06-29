package com.voting.service;

import com.voting.dto.ElectionRequest;
import com.voting.entity.Election;
import com.voting.entity.User;
import com.voting.repository.ElectionRepository;
import com.voting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    public Optional<Election> getElectionById(Long id) {
        return electionRepository.findById(id);
    }

    public Election createElection(ElectionRequest request, Long createdByUserId) {
        User createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Election election = new Election();
        election.setTitle(request.getTitle());
        election.setDescription(request.getDescription());
        election.setStartDate(request.getStartDate());
        election.setEndDate(request.getEndDate());
        election.setMaxVotesPerVoter(request.getMaxVotesPerVoter());
        election.setCreatedBy(createdBy);
        election.setStatus(Election.ElectionStatus.DRAFT);

        return electionRepository.save(election);
    }

    public Election updateElectionStatus(Long electionId, Election.ElectionStatus status) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Election not found"));
        election.setStatus(status);
        return electionRepository.save(election);
    }

    public List<Election> getActiveElections() {
        LocalDateTime now = LocalDateTime.now();
        return electionRepository.findAll().stream()
                .filter(election -> election.isActive())
                .toList();
    }

    public boolean isElectionActive(Long electionId) {
        return electionRepository.findById(electionId)
                .map(Election::isActive)
                .orElse(false);
    }
} 
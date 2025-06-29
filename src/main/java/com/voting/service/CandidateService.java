package com.voting.service;

import com.voting.dto.CandidateRequest;
import com.voting.entity.Candidate;
import com.voting.entity.Election;
import com.voting.repository.CandidateRepository;
import com.voting.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private ElectionRepository electionRepository;

    public List<Candidate> getCandidatesByElection(Long electionId) {
        return candidateRepository.findByElectionId(electionId);
    }

    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    public Candidate createCandidate(CandidateRequest request) {
        Election election = electionRepository.findById(request.getElectionId())
                .orElseThrow(() -> new RuntimeException("Election not found"));

        Candidate candidate = new Candidate();
        candidate.setElection(election);
        candidate.setName(request.getName());
        candidate.setParty(request.getParty());
        candidate.setDescription(request.getDescription());
        candidate.setImageUrl(request.getImageUrl());
        candidate.setIsActive(true);

        return candidateRepository.save(candidate);
    }

    public Candidate updateCandidate(Long id, CandidateRequest request) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        candidate.setName(request.getName());
        candidate.setParty(request.getParty());
        candidate.setDescription(request.getDescription());
        candidate.setImageUrl(request.getImageUrl());

        return candidateRepository.save(candidate);
    }

    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setIsActive(false);
        candidateRepository.save(candidate);
    }
} 
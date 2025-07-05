package com.voting.service;

import com.voting.dto.CandidateRequest;
import com.voting.dto.CandidateResponse;
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

    public List<CandidateResponse> getCandidatesByElection(Long electionId) {
        return candidateRepository.findByElectionId(electionId).stream().map(this::toDto).toList();
    }

    public Optional<CandidateResponse> getCandidateById(Long id) {
        return candidateRepository.findById(id).map(this::toDto);
    }

    public CandidateResponse createCandidate(CandidateRequest request) {
        if (candidateRepository.existsByElectionIdAndName(request.getElectionId(), request.getName())) {
            throw new RuntimeException("Candidate with this name already exists for this election");
        }
        Election election = electionRepository.findById(request.getElectionId())
                .orElseThrow(() -> new RuntimeException("Election not found"));

        Candidate candidate = new Candidate();
        candidate.setElection(election);
        candidate.setName(request.getName());
        candidate.setParty(request.getParty());
        candidate.setDescription(request.getDescription());
        candidate.setImageUrl(request.getImageUrl());
        candidate.setIsActive(true);

        Candidate saved = candidateRepository.save(candidate);
        return toDto(saved);
    }

    public CandidateResponse updateCandidate(Long id, CandidateRequest request) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        candidate.setName(request.getName());
        candidate.setParty(request.getParty());
        candidate.setDescription(request.getDescription());
        candidate.setImageUrl(request.getImageUrl());

        Candidate saved = candidateRepository.save(candidate);
        return toDto(saved);
    }

    public void deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setIsActive(false);
        candidateRepository.save(candidate);
    }

    private CandidateResponse toDto(Candidate candidate) {
        return new CandidateResponse(
            candidate.getId(),
            candidate.getElection() != null ? candidate.getElection().getId() : null,
            candidate.getName(),
            candidate.getParty(),
            candidate.getDescription(),
            candidate.getImageUrl(),
            candidate.getIsActive(),
            candidate.getCreatedAt(),
            candidate.getUpdatedAt()
        );
    }
} 
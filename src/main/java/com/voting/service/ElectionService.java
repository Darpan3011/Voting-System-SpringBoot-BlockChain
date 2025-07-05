package com.voting.service;

import com.voting.dto.ElectionRequest;
import com.voting.dto.ElectionResponse;
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

    public List<ElectionResponse> getAllElections() {
        return electionRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<ElectionResponse> getElectionById(Long id) {
        return electionRepository.findById(id).map(this::toDto);
    }

    public ElectionResponse createElection(ElectionRequest request, Long createdByUserId) {
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

        Election saved = electionRepository.save(election);
        return toDto(saved);
    }

    public ElectionResponse updateElectionStatus(Long electionId, Election.ElectionStatus status) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Election not found"));
        election.setStatus(status);
        Election saved = electionRepository.save(election);
        return toDto(saved);
    }

    public List<ElectionResponse> getActiveElections() {
        return electionRepository.findAll().stream()
                .filter(Election::isActive)
                .map(this::toDto)
                .toList();
    }

    public boolean isElectionActive(Long electionId) {
        return electionRepository.findById(electionId)
                .map(Election::isActive)
                .orElse(false);
    }

    private ElectionResponse toDto(Election election) {
        return new ElectionResponse(
                election.getId(),
                election.getTitle(),
                election.getDescription(),
                election.getStartDate(),
                election.getEndDate(),
                election.getStatus() != null ? election.getStatus().name() : null,
                election.getMaxVotesPerVoter(),
                election.getCreatedBy() != null ? election.getCreatedBy().getUsername() : null,
                election.getCreatedAt(),
                election.getUpdatedAt()
        );
    }
} 
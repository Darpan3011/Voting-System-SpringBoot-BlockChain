package com.voting.repository;

import com.voting.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByElectionIdAndVoterId(Long electionId, Long voterId);
    boolean existsByElectionIdAndVoterId(Long electionId, Long voterId);
} 
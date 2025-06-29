package com.voting.controller;

import com.voting.dto.VoteRequest;
import com.voting.entity.Vote;
import com.voting.service.VoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVote(@PathVariable Long id) {
        return voteService.getVoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cast")
    public ResponseEntity<Vote> castVote(@Valid @RequestBody VoteRequest request, @RequestParam Long voterId) {
        return ResponseEntity.ok(voteService.castVote(request, voterId));
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<Long> getTotalVotesForElection(@PathVariable Long electionId) {
        return ResponseEntity.ok(voteService.getTotalVotesForElection(electionId));
    }

    @GetMapping("/election/{electionId}/results")
    public ResponseEntity<Map<Long, Long>> getElectionResults(@PathVariable Long electionId) {
        return ResponseEntity.ok(voteService.getElectionResults(electionId));
    }

    @GetMapping("/verify/{transactionId}")
    public ResponseEntity<Boolean> verifyVote(@PathVariable String transactionId) {
        return ResponseEntity.ok(voteService.verifyVote(transactionId));
    }
} 
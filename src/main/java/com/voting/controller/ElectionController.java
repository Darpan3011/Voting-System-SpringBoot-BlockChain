package com.voting.controller;

import com.voting.dto.ElectionRequest;
import com.voting.entity.Election;
import com.voting.repository.ElectionRepository;
import com.voting.service.ElectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @Autowired
    private ElectionRepository electionRepository;
    
    @Autowired
    private ElectionService electionService;

    @GetMapping
    public ResponseEntity<List<Election>> getAllElections() {
        return ResponseEntity.ok(electionService.getAllElections());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Election>> getActiveElections() {
        return ResponseEntity.ok(electionService.getActiveElections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Election> getElection(@PathVariable Long id) {
        return electionService.getElectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Election> createElection(@Valid @RequestBody ElectionRequest request, @RequestParam Long createdByUserId) {
        return ResponseEntity.ok(electionService.createElection(request, createdByUserId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Election> updateElectionStatus(@PathVariable Long id, @RequestParam String status) {
        Election.ElectionStatus electionStatus = Election.ElectionStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(electionService.updateElectionStatus(id, electionStatus));
    }
} 
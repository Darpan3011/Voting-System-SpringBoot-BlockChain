package com.voting.dto;

import java.time.LocalDateTime;

public class VoteResponse {
    private Long id;
    private Long electionId;
    private Long candidateId;
    private LocalDateTime createdAt;
    private String transactionId;
    private Boolean isVerified;

    public VoteResponse() {}

    public VoteResponse(Long id, Long electionId, Long candidateId, LocalDateTime createdAt, String transactionId, Boolean isVerified) {
        this.id = id;
        this.electionId = electionId;
        this.candidateId = candidateId;
        this.createdAt = createdAt;
        this.transactionId = transactionId;
        this.isVerified = isVerified;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getElectionId() { return electionId; }
    public void setElectionId(Long electionId) { this.electionId = electionId; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public Boolean getIsVerified() { return isVerified; }
    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }
} 
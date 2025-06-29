package com.voting.dto;

import jakarta.validation.constraints.NotNull;

public class VoteRequest {
    
    @NotNull(message = "Election ID is required")
    private Long electionId;
    
    @NotNull(message = "Candidate ID is required")
    private Long candidateId;
    
    // Constructors
    public VoteRequest() {}
    
    public VoteRequest(Long electionId, Long candidateId) {
        this.electionId = electionId;
        this.candidateId = candidateId;
    }
    
    // Getters and Setters
    public Long getElectionId() {
        return electionId;
    }
    
    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }
    
    public Long getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
    
    @Override
    public String toString() {
        return "VoteRequest{" +
                "electionId=" + electionId +
                ", candidateId=" + candidateId +
                '}';
    }
}
 
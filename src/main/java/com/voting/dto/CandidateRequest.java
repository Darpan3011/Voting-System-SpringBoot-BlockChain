package com.voting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CandidateRequest {
    
    @NotNull(message = "Election ID is required")
    private Long electionId;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String party;
    
    private String description;
    
    private String imageUrl;
    
    // Constructors
    public CandidateRequest() {}
    
    public CandidateRequest(Long electionId, String name, String party, String description) {
        this.electionId = electionId;
        this.name = name;
        this.party = party;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getElectionId() {
        return electionId;
    }
    
    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getParty() {
        return party;
    }
    
    public void setParty(String party) {
        this.party = party;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    @Override
    public String toString() {
        return "CandidateRequest{" +
                "electionId=" + electionId +
                ", name='" + name + '\'' +
                ", party='" + party + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
} 
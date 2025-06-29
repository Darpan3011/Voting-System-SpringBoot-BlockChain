package com.voting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "votes")
@EntityListeners(AuditingEntityListener.class)
public class Vote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;
    
    @NotBlank
    @Column(name = "encrypted_ballot", columnDefinition = "TEXT", nullable = false)
    private String encryptedBallot;
    
    @NotBlank
    @Column(name = "ballot_hash", nullable = false)
    private String ballotHash;
    
    @Column(name = "transaction_id", unique = true)
    private String transactionId;
    
    @Column(name = "is_verified")
    private Boolean isVerified = false;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BlockchainTransaction> blockchainTransactions = new ArrayList<>();
    
    // Constructors
    public Vote() {}
    
    public Vote(Election election, User voter, String encryptedBallot, String ballotHash) {
        this.election = election;
        this.voter = voter;
        this.encryptedBallot = encryptedBallot;
        this.ballotHash = ballotHash;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Election getElection() {
        return election;
    }
    
    public void setElection(Election election) {
        this.election = election;
    }
    
    public User getVoter() {
        return voter;
    }
    
    public void setVoter(User voter) {
        this.voter = voter;
    }
    
    public String getEncryptedBallot() {
        return encryptedBallot;
    }
    
    public void setEncryptedBallot(String encryptedBallot) {
        this.encryptedBallot = encryptedBallot;
    }
    
    public String getBallotHash() {
        return ballotHash;
    }
    
    public void setBallotHash(String ballotHash) {
        this.ballotHash = ballotHash;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public Boolean getIsVerified() {
        return isVerified;
    }
    
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<BlockchainTransaction> getBlockchainTransactions() {
        return blockchainTransactions;
    }
    
    public void setBlockchainTransactions(List<BlockchainTransaction> blockchainTransactions) {
        this.blockchainTransactions = blockchainTransactions;
    }
    
    // Helper methods
    public boolean isVerified() {
        return Boolean.TRUE.equals(isVerified);
    }
    
    public boolean hasTransactionId() {
        return transactionId != null && !transactionId.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", electionId=" + (election != null ? election.getId() : null) +
                ", voterId=" + (voter != null ? voter.getId() : null) +
                ", transactionId='" + transactionId + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
} 
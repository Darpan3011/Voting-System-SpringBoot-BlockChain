package com.voting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "blockchain_transactions")
@EntityListeners(AuditingEntityListener.class)
public class BlockchainTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;
    
    @NotBlank
    @Column(name = "transaction_hash", unique = true, nullable = false)
    private String transactionHash;
    
    @Column(name = "block_number")
    private Long blockNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;
    
    @Column(name = "gas_used")
    private Long gasUsed;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public BlockchainTransaction() {}
    
    public BlockchainTransaction(Vote vote, String transactionHash) {
        this.vote = vote;
        this.transactionHash = transactionHash;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Vote getVote() {
        return vote;
    }
    
    public void setVote(Vote vote) {
        this.vote = vote;
    }
    
    public String getTransactionHash() {
        return transactionHash;
    }
    
    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
    
    public Long getBlockNumber() {
        return blockNumber;
    }
    
    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    
    public Long getGasUsed() {
        return gasUsed;
    }
    
    public void setGasUsed(Long gasUsed) {
        this.gasUsed = gasUsed;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Helper methods
    public boolean isConfirmed() {
        return TransactionStatus.CONFIRMED.equals(status);
    }
    
    public boolean isPending() {
        return TransactionStatus.PENDING.equals(status);
    }
    
    public boolean isFailed() {
        return TransactionStatus.FAILED.equals(status);
    }
    
    @Override
    public String toString() {
        return "BlockchainTransaction{" +
                "id=" + id +
                ", transactionHash='" + transactionHash + '\'' +
                ", blockNumber=" + blockNumber +
                ", status=" + status +
                '}';
    }
    
    public enum TransactionStatus {
        PENDING, CONFIRMED, FAILED
    }
} 
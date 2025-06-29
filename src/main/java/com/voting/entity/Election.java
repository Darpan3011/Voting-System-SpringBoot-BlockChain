package com.voting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "elections")
@EntityListeners(AuditingEntityListener.class)
public class Election {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ElectionStatus status = ElectionStatus.DRAFT;
    
    @Column(name = "max_votes_per_voter")
    private Integer maxVotesPerVoter = 1;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Candidate> candidates = new ArrayList<>();
    
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();
    
    // Constructors
    public Election() {}
    
    public Election(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public ElectionStatus getStatus() {
        return status;
    }
    
    public void setStatus(ElectionStatus status) {
        this.status = status;
    }
    
    public Integer getMaxVotesPerVoter() {
        return maxVotesPerVoter;
    }
    
    public void setMaxVotesPerVoter(Integer maxVotesPerVoter) {
        this.maxVotesPerVoter = maxVotesPerVoter;
    }
    
    public User getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
    
    public List<Candidate> getCandidates() {
        return candidates;
    }
    
    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
    
    public List<Vote> getVotes() {
        return votes;
    }
    
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
    
    // Helper methods
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return ElectionStatus.ACTIVE.equals(status) && 
               now.isAfter(startDate) && 
               now.isBefore(endDate);
    }
    
    public boolean isDraft() {
        return ElectionStatus.DRAFT.equals(status);
    }
    
    public boolean isClosed() {
        return ElectionStatus.CLOSED.equals(status) || 
               ElectionStatus.RESULTS_PUBLISHED.equals(status);
    }
    
    public boolean hasStarted() {
        return LocalDateTime.now().isAfter(startDate);
    }
    
    public boolean hasEnded() {
        return LocalDateTime.now().isAfter(endDate);
    }
    
    public long getTotalVotes() {
        return votes.size();
    }
    
    public boolean canVote() {
        return isActive() && !hasEnded();
    }
    
    @Override
    public String toString() {
        return "Election{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
    
    public enum ElectionStatus {
        DRAFT, ACTIVE, CLOSED, RESULTS_PUBLISHED
    }
} 
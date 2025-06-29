package com.voting.repository;

import com.voting.entity.BlockchainTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockchainTransactionRepository extends JpaRepository<BlockchainTransaction, Long> {
    Optional<BlockchainTransaction> findByTransactionHash(String transactionHash);
} 
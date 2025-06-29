package com.voting.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import jakarta.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class BlockchainService {

    @Value("${blockchain.ethereum.node-url}")
    private String nodeUrl;

    @Value("${blockchain.ethereum.private-key}")
    private String privateKey;

    @Value("${blockchain.ethereum.chain-id}")
    private long chainId;

    private Web3j web3j;
    private Credentials credentials;
    private TransactionManager txManager;

    @PostConstruct
    public void init() {
        web3j = Web3j.build(new HttpService(nodeUrl));
        credentials = Credentials.create(privateKey);
        txManager = new RawTransactionManager(web3j, credentials, chainId);
    }

    /**
     * Sends a simple transaction with the vote hash as data (for demo purposes).
     * @param data The data to store (e.g., vote hash)
     * @return TransactionReceipt if successful
     */
    public TransactionReceipt sendTransaction(String data) throws Exception {
        // Send a simple transaction to self with data as input
        String to = credentials.getAddress();
        BigInteger value = BigInteger.ZERO;
        String hexData = Numeric.toHexString(data.getBytes());
        String txHash = txManager.sendTransaction(
                DefaultGasProvider.GAS_PRICE,
                DefaultGasProvider.GAS_LIMIT,
                to,
                hexData,
                value
        ).getTransactionHash();

        // Wait for receipt
        Optional<TransactionReceipt> receiptOpt = Optional.empty();
        int attempts = 40;
        while (attempts-- > 0 && receiptOpt.isEmpty()) {
            EthGetTransactionReceipt receiptResp = web3j.ethGetTransactionReceipt(txHash).send();
            receiptOpt = receiptResp.getTransactionReceipt();
            if (receiptOpt.isEmpty()) Thread.sleep(1500);
        }
        return receiptOpt.orElseThrow(() -> new RuntimeException("Transaction not mined in time"));
    }
} 
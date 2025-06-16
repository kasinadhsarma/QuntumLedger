package com.quantumledger.service;

import com.quantumledger.core.Block;
import com.quantumledger.core.Blockchain;
import com.quantumledger.core.Transaction;
import com.quantumledger.persistence.DatabaseManager;
import java.util.List;
import java.util.ArrayList;

public class LedgerService {
    private final Blockchain blockchain;
    private final DatabaseManager dbManager;
    private final List<Transaction> pendingTransactions;
    
    public LedgerService(int difficulty) {
        this.blockchain = new Blockchain(difficulty);
        this.dbManager = new DatabaseManager();
        this.pendingTransactions = new ArrayList<>();
    }
    
    public void addTransaction(Transaction transaction) {
        // Validate transaction
        if (transaction.isValid()) {
            pendingTransactions.add(transaction);
        } else {
            throw new IllegalArgumentException("Invalid transaction");
        }
    }
    
    public void mineBlock() {
        if (!pendingTransactions.isEmpty()) {
            // Create new block with pending transactions
            blockchain.addBlock(new ArrayList<>(pendingTransactions));
            
            // Save block to database
            Block latestBlock = blockchain.getLatestBlock();
            dbManager.saveBlock(latestBlock);
            
            // Save transactions
            for (Transaction transaction : pendingTransactions) {
                dbManager.saveTransaction(transaction);
            }
            
            // Clear pending transactions
            pendingTransactions.clear();
        }
    }
    
    public Block getBlock(String hash) {
        return dbManager.getBlockByHash(hash);
    }
    
    public List<Block> getAllBlocks() {
        return dbManager.getAllBlocks();
    }
    
    public Transaction getTransaction(String hash) {
        return dbManager.getTransactionByHash(hash);
    }
    
    public List<Transaction> getTransactionsByBlock(String blockHash) {
        return dbManager.getTransactionsByBlock(blockHash);
    }
    
    public boolean verifyChain() {
        return blockchain.isChainValid();
    }
    
    public void shutdown() {
        dbManager.close();
        DatabaseManager.shutdown();
    }
}
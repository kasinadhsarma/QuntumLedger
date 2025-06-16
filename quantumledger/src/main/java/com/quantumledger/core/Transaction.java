package com.quantumledger.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private String transactionId;
    
    @Column(nullable = false)
    private String sender;
    
    @Column(nullable = false)
    private String recipient;
    
    @Column(nullable = false)
    private double amount;
    
    @Column(nullable = false)
    private long timestamp;
    
    @Column
    private String signature;

    // Required by JPA
    protected Transaction() {}

    public Transaction(String sender, String recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.timestamp = Instant.now().getEpochSecond();
        this.transactionId = calculateHash();
    }

    private String calculateHash() {
        return com.quantumledger.util.HashUtil.sha256(
            sender +
            recipient +
            amount +
            timestamp
        );
    }

    public void signTransaction(String privateKey) {
        // TODO: Implement signature logic using privateKey
        this.signature = "Signature placeholder"; // This will be replaced with actual signature
    }

    public boolean verifySignature() {
        // TODO: Implement signature verification
        return true; // This will be replaced with actual verification
    }
    
    public boolean isValid() {
        // Basic validation rules
        return sender != null && 
               recipient != null && 
               amount > 0 && 
               verifySignature();
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public double getAmount() { return amount; }
    public long getTimestamp() { return timestamp; }
    public String getSignature() { return signature; }
}

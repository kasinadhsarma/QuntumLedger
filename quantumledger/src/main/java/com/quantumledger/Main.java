package com.quantumledger;

import com.quantumledger.core.Block;
import com.quantumledger.core.Blockchain;
import com.quantumledger.core.Transaction;
import com.quantumledger.service.LedgerService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting QuantumLedger...");
        
        // Initialize services with mining difficulty of 4
        LedgerService ledgerService = new LedgerService(4);
        
        // TODO: Initialize other services and start the application
        
        System.out.println("QuantumLedger is running!");
    }
}

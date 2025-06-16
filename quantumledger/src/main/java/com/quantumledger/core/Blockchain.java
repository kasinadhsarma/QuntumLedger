package com.quantumledger.core;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;
    private int difficulty;
    
    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = difficulty;
        // Create genesis block
        createGenesisBlock();
    }
    
    private void createGenesisBlock() {
        Block genesisBlock = new Block("0", new ArrayList<>());
        genesisBlock.mineBlock(difficulty);
        chain.add(genesisBlock);
    }
    
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }
    
    public void addBlock(List<Transaction> transactions) {
        Block previousBlock = getLatestBlock();
        Block newBlock = new Block(previousBlock.getHash(), transactions);
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }
    
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);
            
            // Check if current block's hash is valid
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            
            // Check if current block points to previous block's hash
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }
    
    public List<Block> getChain() {
        return chain;
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
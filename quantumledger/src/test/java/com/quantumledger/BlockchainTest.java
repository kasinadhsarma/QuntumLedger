package com.quantumledger;

import com.quantumledger.core.Block;
import com.quantumledger.core.Transaction;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BlockchainTest {
    
    @Test
    public void testBlockCreation() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("sender1", "recipient1", 100.0));
        
        Block block = new Block("0", transactions);
        assertNotNull(block.getHash());
        assertEquals("0", block.getPreviousHash());
        assertEquals(1, block.getTransactions().size());
    }
    
    @Test
    public void testBlockMining() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("sender1", "recipient1", 100.0));
        
        Block block = new Block("0", transactions);
        block.mineBlock(4); // Mine with difficulty 4
        
        assertTrue(block.getHash().substring(0, 4).equals("0000"));
    }
}

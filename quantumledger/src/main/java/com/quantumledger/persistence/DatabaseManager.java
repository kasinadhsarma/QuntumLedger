package com.quantumledger.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.quantumledger.core.Block;
import com.quantumledger.core.Transaction;
import java.util.List;

public class DatabaseManager {
    private static final String PERSISTENCE_UNIT_NAME = "QuantumLedgerPU";
    private static EntityManagerFactory factory;
    private EntityManager entityManager;

    public DatabaseManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        this.entityManager = factory.createEntityManager();
    }

    public void saveBlock(Block block) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(block);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public Block getBlockByHash(String hash) {
        return entityManager.createQuery(
            "SELECT b FROM Block b WHERE b.hash = :hash", Block.class)
            .setParameter("hash", hash)
            .getSingleResult();
    }

    public List<Block> getAllBlocks() {
        return entityManager.createQuery(
            "SELECT b FROM Block b ORDER BY b.timestamp", Block.class)
            .getResultList();
    }

    public void saveTransaction(Transaction transaction) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(transaction);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public Transaction getTransactionByHash(String hash) {
        return entityManager.createQuery(
            "SELECT t FROM Transaction t WHERE t.hash = :hash", Transaction.class)
            .setParameter("hash", hash)
            .getSingleResult();
    }

    public List<Transaction> getTransactionsByBlock(String blockHash) {
        return entityManager.createQuery(
            "SELECT t FROM Transaction t WHERE t.block.hash = :blockHash", Transaction.class)
            .setParameter("blockHash", blockHash)
            .getResultList();
    }

    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
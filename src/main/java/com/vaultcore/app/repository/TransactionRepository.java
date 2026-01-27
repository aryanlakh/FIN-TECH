package com.vaultcore.app.repository;

import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(UUID transactionId, String type, String status) {
        jdbcTemplate.update(
            """
            INSERT INTO txn  (id, type, status, created_at)
            VALUES (?, ?, ?, NOW())
            """,
            transactionId, type, status
        );
    }

}

package com.vaultcore.app.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vaultcore.app.repository.RepositoryClass.LedgerRepository;

@Repository
public class LedgerRepositoryImpl implements LedgerRepository {

    private final JdbcTemplate jdbcTemplate;

    public LedgerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(UUID transactionId,
                       UUID accountId,
                       String entryType,
                       BigDecimal amount) {

        jdbcTemplate.update(
            """
            INSERT INTO ledger_entry
            (id, transaction_id, account_id, entry_type, amount, created_at)
            VALUES (?, ?, ?, ?, ?, NOW())
            """,
            UUID.randomUUID(),
            transactionId,
            accountId,
            entryType,
            amount
        );
    }
    @Override
    public BigDecimal getBalance(UUID accountId) {
    return jdbcTemplate.queryForObject(
        """
        SELECT COALESCE(SUM(
            CASE
                WHEN entry_type = 'CREDIT' THEN amount
                ELSE -amount
            END
        ), 0)
        FROM ledger_entry
        WHERE account_id = ?
        """,
        BigDecimal.class,
        accountId
    );
}

}

package com.vaultcore.app.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Repository;

public class RepositoryClass {
  @Repository
public interface LedgerRepository {
    void insert(UUID transactionId,
                UUID accountId,
                String entryType,
                BigDecimal amount);


    BigDecimal getBalance(UUID accountId);

    
            
}

}
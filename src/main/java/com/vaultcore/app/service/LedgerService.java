package com.vaultcore.app.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import com.vaultcore.app.repository.RepositoryClass.LedgerRepository;
import com.vaultcore.app.repository.TransactionRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;



@Service
public class LedgerService {
     private final LedgerRepository ledgerRepository;
     private final TransactionRepository transactionRepository;

    public LedgerService(LedgerRepository ledgerRepository,
                         TransactionRepository transactionRepository) {
        this.ledgerRepository = ledgerRepository;
        this.transactionRepository = transactionRepository;
    }

        
  @Transactional(isolation = Isolation.SERIALIZABLE)
   public void transfer(UUID fromAccount,
                     UUID toAccount,
                     BigDecimal amount) {

    BigDecimal balance = ledgerRepository.getBalance(fromAccount);

    if (balance.compareTo(amount) < 0) {
        throw new IllegalStateException("Insufficient balance");
    }

    UUID txnId = UUID.randomUUID();

    transactionRepository.create(txnId, "TRANSFER", "SUCCESS");
    ledgerRepository.insert(txnId, fromAccount, "DEBIT", amount);
    ledgerRepository.insert(txnId, toAccount, "CREDIT", amount);
}
@Transactional
   public void deposit(UUID accountId, BigDecimal amount) {
    UUID txnId = UUID.randomUUID();
    ledgerRepository.insert(txnId, accountId, "CREDIT", amount);
}

@Transactional(readOnly = true)
   public BigDecimal getBalance(UUID accountId) {
    return ledgerRepository.getBalance(accountId);
}

@Transactional(isolation = Isolation.SERIALIZABLE)
public void withdraw(UUID accountId, BigDecimal amount) {

    BigDecimal balance = ledgerRepository.getBalance(accountId);
    if (balance.compareTo(amount) < 0) {
        throw new IllegalStateException("Insufficient balance");
    }

    UUID txnId = UUID.randomUUID();
    ledgerRepository.insert(txnId, accountId, "DEBIT", amount);
}




}

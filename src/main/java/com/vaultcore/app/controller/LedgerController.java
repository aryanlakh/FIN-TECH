package com.vaultcore.app.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaultcore.app.controller.dto.BalanceRequest;
import com.vaultcore.app.controller.dto.DepositRequest;
import com.vaultcore.app.controller.dto.TransferRequest;
import com.vaultcore.app.controller.dto.WithdrawRequest;
import com.vaultcore.app.service.LedgerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest req) {
        ledgerService.transfer(
            req.fromAccount(),
            req.toAccount(),
            req.amount()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@Valid @RequestBody DepositRequest req) {
    ledgerService.deposit(req.accountId(), req.amount());
    return ResponseEntity.ok().build();
}

@PostMapping("/balance")
public ResponseEntity<BigDecimal> getBalance(@Valid @RequestBody BalanceRequest req) {
    return ResponseEntity.ok(
        ledgerService.getBalance(req.accountId())
    );
}

@PostMapping("/withdraw")
public ResponseEntity<Void> withdraw(@Valid @RequestBody WithdrawRequest req) {
    ledgerService.withdraw(req.accountId(), req.amount());
    return ResponseEntity.ok().build();
}



}

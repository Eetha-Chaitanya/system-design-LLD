package com.systemdesign.lld.domain.transaction.strategy;

import com.systemdesign.lld.domain.dispenser.CashDispenser;
import com.systemdesign.lld.domain.transaction.Transaction;
import com.systemdesign.lld.domain.transaction.TransactionResult;

public class DepositTransaction extends Transaction {
    @Override
    public TransactionResult execute(CashDispenser cashDispenser) {
        if(amount < 0){
            return TransactionResult.failure("Amount cannot be negative");
        }
        boolean credited = bank.deposit(card, amount);
        if(!credited){
            return TransactionResult.failure("Deposit failed");
        }
        return TransactionResult.success(String.format("Processed the amount. New available balance: Rs. %.2f", bank.getAvailableBalance(card)));
    }
}

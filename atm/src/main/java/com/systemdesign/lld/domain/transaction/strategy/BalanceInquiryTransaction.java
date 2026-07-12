package com.systemdesign.lld.domain.transaction.strategy;

import com.systemdesign.lld.domain.dispenser.CashDispenser;
import com.systemdesign.lld.domain.transaction.Transaction;
import com.systemdesign.lld.domain.transaction.TransactionResult;

public class BalanceInquiryTransaction extends Transaction {
    @Override
    public TransactionResult execute(CashDispenser cashDispenser) {
        return TransactionResult.success(String.format("Available Balance: %.2f", bank.getAvailableBalance(card)));
    }
}

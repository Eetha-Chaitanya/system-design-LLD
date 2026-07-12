package com.systemdesign.lld.domain.transaction.strategy;

import com.systemdesign.lld.domain.dispenser.CashDispenser;
import com.systemdesign.lld.domain.transaction.Transaction;
import com.systemdesign.lld.domain.transaction.TransactionResult;

import java.util.List;

public class MiniStatementTransaction extends Transaction {
    @Override
    public TransactionResult execute(CashDispenser cashDispenser) {
        List<String> lines = bank.getMiniStatement(card);
        String miniStatement = lines.isEmpty() ? "No recent transactions" : String.join("\n ", lines);
        return TransactionResult.success(miniStatement);
    }
}

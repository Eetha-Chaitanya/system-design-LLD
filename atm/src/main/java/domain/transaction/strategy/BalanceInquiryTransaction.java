package domain.transaction.strategy;

import domain.dispenser.CashDispenser;
import domain.transaction.Transaction;
import domain.transaction.TransactionResult;

public class BalanceInquiryTransaction extends Transaction {
    @Override
    public TransactionResult execute(CashDispenser cashDispenser) {
        return TransactionResult.success(String.format("Available Balance: %.2f", bank.getAvailableBalance(card)));
    }
}

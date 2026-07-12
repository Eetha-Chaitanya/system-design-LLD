package com.systemdesign.lld.atm.state;

import com.systemdesign.lld.atm.ATM;
import com.systemdesign.lld.domain.transaction.Transaction;
import com.systemdesign.lld.domain.transaction.TransactionFactory;
import com.systemdesign.lld.domain.transaction.TransactionResult;
import com.systemdesign.lld.domain.transaction.TransactionType;

public class AuthenticatedState implements ATMState {
    @Override
    public void selectTransaction(ATM atm, TransactionType transactionType, int amount) {
        Transaction transaction = TransactionFactory.createTransaction(atm.getCurrentCard(), atm.getCurrentBank(), amount, transactionType);
        TransactionResult result = transaction.execute(atm.getCashDispenser());
        System.out.println("Transaction result: "+result);
    }

    @Override
    public void ejectCard(ATM atm) {
        System.out.println("Card has been ejected");
        atm.setCurrentCard(null);
        atm.setCurrentState(new IdleState());
    }
}

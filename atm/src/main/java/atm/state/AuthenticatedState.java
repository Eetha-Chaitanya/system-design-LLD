package atm.state;

import atm.ATM;
import domain.transaction.Transaction;
import domain.transaction.TransactionFactory;
import domain.transaction.TransactionResult;
import domain.transaction.TransactionType;

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

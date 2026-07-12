package com.systemdesign.lld.atm.state;

import com.systemdesign.lld.bank.Bank;
import com.systemdesign.lld.atm.ATM;

public class HasCardState implements ATMState {
    @Override
    public void enterPin(ATM atm, String pin) {
        Bank bank = atm.getBankRouter().resolve(atm.getCurrentCard());
        boolean valid = bank.authenticate(atm.getCurrentCard(), pin);
        if(valid){
            atm.setCurrentBank(bank);
            atm.setCurrentState(new AuthenticatedState());
            System.out.println("Pin has been accepted. Authenticated ");
        }
        else{
            System.out.println("Pin has been refused. Please try again");
            atm.setCurrentCard(null);
            atm.setCurrentState(new IdleState());
        }
    }

    @Override
    public void ejectCard(ATM atm) {
        System.out.println("Card has been ejected");
        atm.setCurrentCard(null);
        atm.setCurrentState(new IdleState());
    }
}

package domain.dispenser;

import domain.dispenser.chainofresponsibility.DispenseChain;

public class CashDispenser {
    private final DispenseChain dispenseChain;
    public CashDispenser(DispenseChain dispenseChain) {
        this.dispenseChain = dispenseChain;
    }

    public void dispense(int amount) {
        dispenseChain.dispense(amount);
    }

    public boolean canDispense(int amount) {
        if(amount % 100 != 0){
            return false;
        }
        return dispenseChain.canDispense(amount);
    }
}

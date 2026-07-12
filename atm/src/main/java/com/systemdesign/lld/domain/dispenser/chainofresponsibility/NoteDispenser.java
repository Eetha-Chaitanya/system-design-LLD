package com.systemdesign.lld.domain.dispenser.chainofresponsibility;

public class NoteDispenser implements DispenseChain{
    private final int noteValue;
    private int numOfNotes;
    private DispenseChain nextChain;

    NoteDispenser(int noteValue, int numOfNotes) {
        this.noteValue = noteValue;
        this.numOfNotes = numOfNotes;
    }

    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.nextChain = nextChain;
    }

    @Override
    public synchronized boolean canDispense(int amount) {
        if(amount < 0){
            return false;
        }
        if(amount == 0){
            return true;
        }
        int numOfNotesToUse = Math.min(amount/noteValue, numOfNotes);
        int remainingAmount = amount - (numOfNotesToUse *  noteValue);
        if(remainingAmount == 0){
            return true;
        }
        if(this.nextChain != null){
            return nextChain.canDispense(remainingAmount);
        }
        return false;
    }

    @Override
    public synchronized void dispense(int amount) {
        if(amount > noteValue){
            int numOfNotesToDispense = Math.min(amount/noteValue, numOfNotes);
            int remainingAmount = amount - (numOfNotesToDispense *  noteValue);
            if(numOfNotesToDispense > 0){
                this.numOfNotes -= numOfNotesToDispense;
            }
            System.out.println("Note: "+this.noteValue+" Dispensed Notes:"+numOfNotesToDispense+" Remaining notes: "+this.numOfNotes);
            if(remainingAmount > 0 && this.nextChain != null){
                this.nextChain.dispense(remainingAmount);
            }
        }
        else if(this.nextChain != null){
            nextChain.dispense(amount);
        }
    }
}

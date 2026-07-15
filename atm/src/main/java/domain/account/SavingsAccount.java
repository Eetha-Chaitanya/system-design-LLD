package domain.account;

public class SavingsAccount extends Account {
    private double balance;

    public SavingsAccount(String accountId, String bankId) {
        super(accountId, bankId);
    }

    public SavingsAccount(String accountId, String bankId, double balance) {
        super(accountId, bankId);
        this.balance = balance;
    }

    @Override
    public double getAvailableBalance() {
        return this.balance;
    }

    @Override
    public synchronized boolean debit(double amount) {
        if(this.balance < amount) {
            return false;
        }
        this.balance -= amount;
        this.recordStatement(String.format("DEBIT: Rs %.2f | Remaining Balance: Rs %.2f", amount, this.balance));
        return true;
    }

    @Override
    public synchronized boolean credit(double amount) {
        if(amount < 0) {
            return false;
        }
        this.balance += amount;
        this.recordStatement(String.format("CREDIT: Rs %.2f | Remaining Balance: Rs %.2f", amount, this.balance));
        return true;
    }

}

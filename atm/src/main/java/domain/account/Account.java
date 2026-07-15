package domain.account;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected final String accountId;
    protected final String bankId;

    protected final List<String> statement =  new ArrayList<>();

    public Account(String accountId, String bankId) {
        this.accountId = accountId;
        this.bankId = bankId;
    }

    public String getAccountId() {
        return accountId;
    }
    public String getBankId() {
        return bankId;
    }

    public abstract boolean debit(double amount);
    public abstract boolean credit(double amount);

    public abstract double getAvailableBalance();

    protected void recordStatement(String statement) {
        this.statement.add(statement);
    }
    public List<String> getMiniStatement() {
        int fromIndex = Math.max(0, this.statement.size() - 5);
        return this.statement.subList(fromIndex, this.statement.size());
    }

}

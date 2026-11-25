public abstract class Account {

    protected int accountNumber;
    protected double balance;
    protected Customer customer;

    public Account(int accountNumber,double balance,Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void deposit(double amount) {

        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }

        return false;
    }

    public Transaction createTransaction(int id, double amount, String type) {

        return new Transaction(id, amount, type);
    }

    public abstract void applyInterest();

    public abstract void displayDetails();
}

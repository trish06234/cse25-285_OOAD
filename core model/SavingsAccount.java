public class SavingsAccount extends Account {

    private final double monthlyInterestRate = 0.0005;

    public SavingsAccount(int accountNumber,double balance,Customer customer) {

        super(accountNumber, balance, customer);
    }

    @Override
    public boolean withdraw(double amount) {

        System.out.println("Withdrawals are only allowed once every 2 years.");

        return false;
    }

    @Override
    public void applyInterest() {

        balance += balance * monthlyInterestRate;
    }

    @Override
    public void displayDetails() {

        System.out.println("\n SAVINGS ACCOUNT ");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: P" + balance);
        System.out.println("Monthly Interest: 0.05%");
    }
}
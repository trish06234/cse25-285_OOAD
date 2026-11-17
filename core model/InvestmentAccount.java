public class InvestmentAccount extends Account {

    private final double monthlyInterestRate = 0.05;

    public InvestmentAccount(int accountNumber,double balance,Customer customer) {

        super(accountNumber, balance, customer);

        if (balance < 500) {
            throw new IllegalArgumentException("Minimum opening deposit is P500.");
        }
    }

    @Override
    public void applyInterest() {
        balance += balance * monthlyInterestRate;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n INVESTMENT ACCOUNT ");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: P" + balance);
        System.out.println("Monthly Interest: 5%");
    }
}
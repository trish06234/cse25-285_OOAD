public class ChequeAccount extends Account {

    private String sourceOfIncome;

    public ChequeAccount(int accountNumber,double balance,Customer customer,String sourceOfIncome) {

        super(accountNumber, balance, customer);

        this.sourceOfIncome = sourceOfIncome;
    }

    @Override
    public void applyInterest() {

    }

    @Override
    public void displayDetails() {

        System.out.println("\n CHEQUE ACCOUNT");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: P" + balance);
        System.out.println("Income Source: " + sourceOfIncome);
    }
}
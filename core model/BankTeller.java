public class BankTeller {

    private String tellerID;
    private String branch;

    public BankTeller(String tellerID, String branch) {
        this.tellerID = tellerID;
        this.branch = branch;
    }

    public String getTellerID() {
        return tellerID;
    }

    public String getBranch() {
        return branch;
    }

    public void processDeposit(Account account, double amount) {
        account.deposit(amount);
        System.out.println("Deposit Successful");
        System.out.println("New Balance: P" + account.getBalance());
    }

    public void processWithdrawal(Account account, double amount) {

        if (account.withdraw(amount)) {
            System.out.println("Withdrawal Successful");
            System.out.println("New Balance: P" + account.getBalance());
        } else {
            System.out.println("Insufficient Funds");
        }
    }

    public void openAccount(Customer customer) {
        customer.registerAccount();
        System.out.println("Account Opened");
    }

    public void closeAccount() {
        System.out.println("Account Closed");
    }

    public void displayCustomerInfo(Customer customer) {

        System.out.println("Customer Information");
        System.out.println("Postal Address: " + customer.getPostalAddress());
        System.out.println("Physical Address: " + customer.getPhysicalAddress());
        System.out.println("Cellphone Number: " + customer.getCellphoneNumber());
    }
}

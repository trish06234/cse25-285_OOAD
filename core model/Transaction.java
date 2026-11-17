public class Transaction {

    private int transactionID;
    private double amount;
    private String transactionType;

    public Transaction(int transactionID,double amount,String transactionType) {

        this.transactionID = transactionID;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void displayTransaction() {

        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Amount: P" + amount);
        System.out.println("Type: " + transactionType);
    }
}
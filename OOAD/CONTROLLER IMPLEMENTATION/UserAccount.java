import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {

    private String accountNumber;
    private String password;
    private String idNumber;
    private String fullName;
    private String dateOfBirth;
    private String address;
    private String email;
    private String postal;
    private String income;
    private String employment;
    private String accountType;
    private String phone;
    private String creationDate;
    private double balance;
    private List<String> transactionHistory;

    public UserAccount(String accountNumber, String password, String idNumber, String fullName, String dateOfBirth, String address, String email, String postal, String income, String employment,
            String accountType, String phone, String creationDate, double balance, List<String> history) {

        this.accountNumber = accountNumber;
        this.password = password;
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.postal = postal;
        this.income = income;
        this.employment = employment;
        this.accountType = accountType;
        this.phone = phone;

        this.creationDate = (creationDate != null && !creationDate.isEmpty())? creationDate : LocalDate.now().toString();

        this.balance = balance;

        this.transactionHistory = (history != null) ? history : new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public boolean canWithdraw(double amount) {

        LocalDate accountDate = LocalDate.parse(creationDate);
        LocalDate currentDate = LocalDate.now();

        if (accountType.equalsIgnoreCase("Savings Account")) {

            long yearsElapsed = ChronoUnit.YEARS.between(accountDate, currentDate);

            if (yearsElapsed < 2) {
                return false;
            }

        } else if (accountType.equalsIgnoreCase("Investment Account")) {

            long monthsElapsed =ChronoUnit.MONTHS.between(accountDate, currentDate);

            if (monthsElapsed < 6) {
                return false;
            }
        }

        if (accountType.equalsIgnoreCase("Investment Account") || accountType.equalsIgnoreCase("Cheque Account")) {

            if (amount >= balance) {
                return false;
            }

        } else {

            if (amount > balance) {
                return false;
            }
        }

        return true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPostal() {
        return postal;
    }

    public String getIncome() {
        return income;
    }

    public String getEmployment() {
        return employment;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getPhone() {
        return phone;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}

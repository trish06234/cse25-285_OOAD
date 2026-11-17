import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    private String accountNumber;
    private String password;
    private String idNumber;
    private String fullName;
    private String Date Of Birth;
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

    public UserAccount(String accountNumber, String password, String idNumber, 
                       String fullName, String Date Of Birth, String address, String email, String postal, 
                       String income, String employment, String accountType, String phone, 
                       String creationDate, double balance, List<String> history) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.Date Of Birth = Date Of Birth;
        this.address = address;
        this.email = email;
        this.postal = postal;
        this.income = income;
        this.employment = employment;
        this.accountType = accountType;
        this.phone = phone;
        this.creationDate = (creationDate != null && !creationDate.isEmpty()) ? creationDate : LocalDate.now().toString();
        this.balance = balance;
        this.transactionHistory = (history != null) ? history : new ArrayList<>();
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean canWithdraw(double amount) {
        LocalDate accountDate = LocalDate.parse(this.creationDate);
        LocalDate currentDate = LocalDate.now();

        if (this.accountType.equalsIgnoreCase("Savings Account")) {
            long yearsElapsed = ChronoUnit.YEARS.between(accountDate, currentDate);
            if (yearsElapsed < 2) return false; 
        } else if (this.accountType.equalsIgnoreCase("Investment Account")) {
            long monthsElapsed = ChronoUnit.MONTHS.between(accountDate, currentDate);
            if (monthsElapsed < 6) return false; 
        }

        if (this.accountType.equalsIgnoreCase("Investment Account") || this.accountType.equalsIgnoreCase("Cheque Account")) {
            if (amount >= this.balance) return false; 
        } else {
            if (amount > this.balance) return false; 
        }

        return true;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
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
    public String getDate Of Birth() { 
    return Date Of Birth; 
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
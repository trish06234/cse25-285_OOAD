import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "bank.txt";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Customer[] customers = new Customer[100];
        Account[] accounts = new Account[100];

        int customerCount = 0;
        int accountNumberCounter = 1000;

        customerCount = loadFromFile(customers, accounts);
        if (customerCount > 0) {
            int maxAccNum = 999;
            for (int i = 0; i < customerCount; i++) {
                if (accounts[i] != null && accounts[i].getAccountNumber() > maxAccNum) {
                    maxAccNum = accounts[i].getAccountNumber();
                }
            }
            accountNumberCounter = maxAccNum + 1;
        }

        while (true) {
            System.out.println("\nBANKING SYSTEM");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    if (customerCount >= 100) {
                        System.out.println("\nSystem is full! Cannot register more customers.");
                        break;
                    }

                    System.out.println("\nREGISTER ");

                    System.out.print("Full Name: ");
                    String name = input.nextLine();

                    System.out.print("PIN: ");
                    int pin = input.nextInt();
                    input.nextLine();

                    System.out.print("Postal Address: ");
                    String postalAddress = input.nextLine();

                    System.out.print("Physical Address: ");
                    String physicalAddress = input.nextLine();

                    System.out.print("Cellphone Number: ");
                    String cellphoneNumber = input.nextLine();

                    Customer customer = new Customer(name,pin,postalAddress,physicalAddress,cellphoneNumber);

                    System.out.println("\nChoose Account Type");
                    System.out.println("1. Savings Account");
                    System.out.println("2. Investment Account");
                    System.out.println("3. Cheque Account");
                    System.out.print("Choice: ");

                    int accountChoice = input.nextInt();
                    input.nextLine();

                    Account account = null;

                    if (accountChoice == 1) {
                        account = new SavingsAccount(
                                accountNumberCounter,
                                0,
                                customer
                        );
                    } else if (accountChoice == 2) {
                        account = new InvestmentAccount(
                                accountNumberCounter,
                                500,
                                customer
                        );
                    } else if (accountChoice == 3) {
                        System.out.print("Source Of Income: ");
                        String sourceOfIncome = input.nextLine();

                        account = new ChequeAccount(
                                accountNumberCounter,
                                0,
                                customer,
                                sourceOfIncome
                        );
                    }

                    customers[customerCount] = customer;
                    accounts[customerCount] = account;

                    saveToFile(customers, accounts, customerCount);

                    System.out.println("\nRegistration Successful!");
                    System.out.println("Your Account Number is: " + accountNumberCounter);

                    customerCount++;
                    accountNumberCounter++;

                    break;

                case 2:
                    System.out.println("\nLOGIN");

                    System.out.print("Enter Account Number: ");
                    int accountNumber = input.nextInt();

                    System.out.print("Enter PIN: ");
                    int loginPin = input.nextInt();

                    boolean loggedIn = false;
                    int loggedInIndex = -1;

                    for (int i = 0; i < customerCount; i++) {
                        if (accounts[i] != null &&
                            accounts[i].getAccountNumber() == accountNumber &&
                            customers[i].getPin() == loginPin) {

                            loggedIn = true;
                            loggedInIndex = i;
                            break;
                        }
                    }

                    if (!loggedIn) {
                        System.out.println("Invalid Login Details");
                        break;
                    }

                    Account currentAccount = accounts[loggedInIndex];
                    int menuChoice;

                    do {
                        System.out.println("\n MAIN MENU ");
                        System.out.println("1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("3. View Balance");
                        System.out.println("4. View Account Details");
                        System.out.println("5. Logout");
                        System.out.print("Enter Choice: ");

                        menuChoice = input.nextInt();

                        switch (menuChoice) {
                            case 1:
                                System.out.print("Amount: ");
                                double depositAmount = input.nextDouble();
                                currentAccount.deposit(depositAmount);
                                saveToFile(customers, accounts, customerCount);
                                System.out.println("Deposit Successful");
                                break;

                            case 2:
                                System.out.print("Amount: ");
                                double withdrawAmount = input.nextDouble();
                                if (currentAccount.withdraw(withdrawAmount)) {
                                    saveToFile(customers, accounts, customerCount); 
                                    System.out.println("Withdrawal Successful");
                                } else {
                                    System.out.println("WITHDRAWAL IS NOT ALLOWED ON THIS ACCOUNT, WAIT FOR AGREED PERIOD");
                                }
                                break;

                            case 3:
                                System.out.println("Balance: P" + currentAccount.getBalance());
                                break;

                            case 4:
                                currentAccount.displayDetails();
                                break;

                            case 5:
                                System.out.println("Logging Out...");
                                break;

                            default:
                                System.out.println("Invalid Option");
                        }

                    } while (menuChoice != 5);

                    break;

                case 3:
                    System.out.println("Thank You For Using The Apple Banking System");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    public static void saveToFile(Customer[] customers, Account[] accounts, int count) {
        try (PrintWriter writer = new PrintWriter(new File(FILE_NAME))) {
            for (int i = 0; i < count; i++) {
              if (accounts[i] != null && customers[i] != null) {
                    String type = "";
                    String extra = "";

                    if (accounts[i] instanceof SavingsAccount) {
                        type = "Savings";
                    } else if (accounts[i] instanceof InvestmentAccount) {
                        type = "Investment";
                    } else if (accounts[i] instanceof ChequeAccount) {
                        type = "Cheque";
if (accounts[i] instanceof ChequeAccount) {
    type = "Cheque";
    extra = ""; 
 }                 
}
                    writer.println(
                        accounts[i].getAccountNumber() + ";" + accounts[i].getBalance() + ";" + type + ";" + customers[i].getName() + ";" + customers[i].getPin() + ";" + customers[i].getPostalAddress() + ";" + customers[i].getPhysicalAddress() + ";" +
                        customers[i].getCellphoneNumber() + ";" + extra
                    );
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public static int loadFromFile(Customer[] customers, Account[] accounts) {//load-from method
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return 0; 
        }

        int count = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine() && count < 100) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(";");

                if (parts.length >= 8) {
                    int accNum = Integer.parseInt(parts[0]);
                    double balance = Double.parseDouble(parts[1]);
                    String type = parts[2];
                    String name = parts[3];
                    int pin = Integer.parseInt(parts[4]);
                    String postal = parts[5];
                    String physical = parts[6];
                    String phone = parts[7];

                    Customer customer = new Customer(name, pin, postal, physical, phone);
                    Account account = null;

                    if (type.equals("Savings")) {
                        account = new SavingsAccount(accNum, balance, customer);
                    } else if (type.equals("Investment")) {
                        account = new InvestmentAccount(accNum, balance, customer);
                    } else if (type.equals("Cheque")) {
                        String extra = (parts.length > 8) ? parts[8] : "";
                        account = new ChequeAccount(accNum, balance, customer, extra);
                    }

                    customers[count] = customer;
                    accounts[count] = account;
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading data from file: " + e.getMessage());
        }
        return count;
    }
}
package com.example.applebanksystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main extends Application {

    private static final String DATA_FILE = "applebank.txt";
    private static final SecureRandom random = new SecureRandom();

    private static String selectedProfileType = "Personal";

    public static class UserAccount {
        String accountNumber;
        String password;
        String profileType;
        String idNumber;
        String fullName;
        String dob;
        String address;
        String email;

        String postal;
        String income;
        String employment;
        String accountType;
        String phone;
        String companyProfile;
        String creationDate;
        double balance;
        List<String> transactionHistory;

        public UserAccount(String accountNumber, String password, String profileType, String idNumber, String fullName, String dob, String address, String email, String postal,
                           String income, String employment, String accountType, String phone, String companyProfile, String creationDate, double balance, List<String> history) {
            this.accountNumber = accountNumber;
            this.password = password;
            this.profileType = (profileType != null && !profileType.isEmpty()) ? profileType : "Personal";
            this.idNumber = idNumber;
            this.fullName = fullName;
            this.dob = dob;

            this.address = address;
            this.email = email;
            this.postal = postal;
            this.income = income;
            this.employment = employment;
            this.accountType = accountType;
            this.phone = phone;
            this.companyProfile = (companyProfile != null) ? companyProfile : "";
            this.creationDate = (creationDate != null && !creationDate.isEmpty()) ? creationDate : LocalDate.now().toString();
            this.balance = balance;
            this.transactionHistory = (history != null) ? history : new ArrayList<>();
        }
    }

    private static final Map<String, UserAccount> registeredUsers = new HashMap<>();
    private static String currentLoggedInUser = "";

    @FXML private Label regTitleLabel;
    @FXML private Label regIdLabel;
    @FXML private Label regNameLabel;
    @FXML private Label regDobLabel;
    @FXML private Label regIncomeLabel;
    @FXML private Label regEmploymentLabel;

    @FXML private TextField regIdField;
    @FXML private TextField regNameField;
    @FXML private TextField regDobField;
    @FXML private TextField regAddressField;
    @FXML private TextField regEmailField;
    @FXML private TextField regPostalField;
    @FXML private TextField regIncomeField;
    @FXML private TextField regEmploymentField;
    @FXML private ComboBox<String> regAccountTypeCombo;
    @FXML private TextField regPhoneField;
    @FXML private TextArea regCompanyProfileArea;

    @FXML private TextField accountNumberField;
    @FXML private TextField passwordField;

    @FXML private Label detailProfileTypeLabel;
    @FXML private Label detailIdLabel;
    @FXML private Label detailNameLabel;
    @FXML private Label detailDobLabel;
    @FXML private Label detailAddressLabel;
    @FXML private Label detailEmailLabel;
    @FXML private Label detailPostalLabel;
    @FXML private Label detailIncomeLabel;
    @FXML private Label detailEmploymentLabel;
    @FXML private Label detailAccountTypeLabel;
    @FXML private Label detailPhoneLabel;

    @FXML private TextField depositAmountField;
    @FXML private TextField withdrawAmountField;
    @FXML private Label balanceDisplayLabel;
    @FXML private TextArea historyTextArea;

    @Override
    public void start(Stage primaryStage) {
        loadUsersFromFile();
        openStage("login_view.fxml", "Apple Banking System - Home", primaryStage);
    }

    private String generateUniqueAccountNumber() {
        String accNum;
        do {
            int num = 10000000 + random.nextInt(90000000);
            accNum = String.valueOf(num);
        } while (registeredUsers.containsKey(accNum));
        return accNum;
    }

    private String generateStrictPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char c = (char) ('A' + random.nextInt(26));
            sb.append(c);
        }
        for (int i = 0; i < 6; i++) {
            int n = random.nextInt(10);
            sb.append(n);
        }
        return sb.toString();
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (UserAccount user : registeredUsers.values()) {
                String joinedHistory = String.join(";;", user.transactionHistory);
                String line = String.join("|",
                        sanitize(user.accountNumber),
                        sanitize(user.password),
                        sanitize(user.profileType),
                        sanitize(user.idNumber),
                        sanitize(user.fullName),
                        sanitize(user.dob),
                        sanitize(user.address),

                        sanitize(user.email),
                        sanitize(user.postal),
                        sanitize(user.income),
                        sanitize(user.employment),
                        sanitize(user.accountType),
                        sanitize(user.phone),
                        sanitize(user.companyProfile),
                        sanitize(user.creationDate),
                        String.valueOf(user.balance),
                        sanitize(joinedHistory)
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsersFromFile() {
        registeredUsers.clear();
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length >= 16) {
                    String accNum = parts[0];
                    String pass = parts[1];
                    String profileType = parts[2];
                    String id = parts[3];
                    String name = parts[4];
                    String dob = parts[5];

                    String address = parts[6];
                    String email = parts[7];
                    String postal = parts[8];
                    String income = parts[9];
                    String employment = parts[10];
                    String accType = parts[11];
                    String phone = parts[12];
                    String companyProfile = (parts.length >= 17) ? parts[13] : "";
                    String creationDate = (parts.length >= 17) ? parts[14] : parts[13];
                    double balance = Double.parseDouble((parts.length >= 17) ? parts[15] : parts[14]);

                    List<String> history = new ArrayList<>();
                    String historyPart = (parts.length >= 17) ? parts[16] : parts[15];
                    if (!historyPart.isEmpty()) {
                        String[] historyEntries = historyPart.split(";;");
                        for (String entry : historyEntries) {
                            history.add(entry);
                        }
                    }

                    UserAccount user = new UserAccount(
                            accNum, pass, profileType, id, name, dob, address, email, postal,
                            income, employment, accType, phone, companyProfile, creationDate, balance, history
                    );
                    registeredUsers.put(accNum, user);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private String sanitize(String input) {
        if (input == null) return "";
        return input.replace("|", "/").replace("\n", " ").replace("\r", "");
    }

    private void openStage(String fxmlFile, String title, Stage existingStage) {
        try {
            if (fxmlFile.equals("register_individual.fxml")) {
                boolean isBusiness = "Business".equalsIgnoreCase(selectedProfileType);
                if (isBusiness) {
                    fxmlFile = "register_business.fxml";
                }
            }

            URL fileUrl = Main.class.getResource(fxmlFile);
            if (fileUrl == null) {
                System.out.println("ERROR: Could not find FXML file: " + fxmlFile);
                return;
            }
            FXMLLoader loader = new FXMLLoader(fileUrl);
            loader.setController(this);
            Parent root = loader.load();

            UserAccount user = registeredUsers.get(currentLoggedInUser);

            if (fxmlFile.equals("view_account_details.fxml") && user != null) {
                boolean isBus = "Business".equalsIgnoreCase(user.profileType);
                if (detailProfileTypeLabel != null) detailProfileTypeLabel.setText("Account Category: " + user.profileType);
                if (detailIdLabel != null) detailIdLabel.setText((isBus ? "Registration Number: " : "ID Number: ") + user.idNumber);
                if (detailNameLabel != null) detailNameLabel.setText((isBus ? "Company Name: " : "Full Name: ") + user.fullName);
                if (detailDobLabel != null) detailDobLabel.setText((isBus ? "Incorporation Date: " : "Date of Birth: ") + user.dob);

                if (detailAddressLabel != null) detailAddressLabel.setText("Physical Address: " + user.address);
                if (detailEmailLabel != null) detailEmailLabel.setText("Email Address: " + user.email);
                if (detailPostalLabel != null) detailPostalLabel.setText("Postal Address: " + user.postal);
                if (detailIncomeLabel != null) detailIncomeLabel.setText((isBus ? "Annual Turnover: " : "Source of Income: ") + user.income);
                if (detailEmploymentLabel != null) detailEmploymentLabel.setText((isBus ? "Shareholder Details: " : "Employment Details: ") + user.employment);
                if (detailAccountTypeLabel != null) detailAccountTypeLabel.setText("Account Type: " + user.accountType);
                if (detailPhoneLabel != null) detailPhoneLabel.setText("Cellphone Number: " + user.phone);
            }

            if (fxmlFile.equals("view_balance.fxml") && user != null) {
                if (balanceDisplayLabel != null) {
                    balanceDisplayLabel.setText(String.format("Current Balance: P %,.2f", user.balance));
                }
            }

            if (fxmlFile.equals("view_history.fxml") && user != null) {
                if (historyTextArea != null) {
                    StringBuilder sb = new StringBuilder();
                    for (String record : user.transactionHistory) {
                        sb.append(record).append("\n\n");
                    }
                    historyTextArea.setText(sb.toString());
                }
            }

            Stage stage = (existingStage != null) ? existingStage : new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeCurrentStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showPopup(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.getButtonTypes().add(javafx.scene.control.ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    public void loginAction(ActionEvent event) {
        loadUsersFromFile();
        closeCurrentStage(event);
        openStage("login_form.fxml", "Apple Banking System - Login", null);
    }

    @FXML
    public void registerAction(ActionEvent event) {
        List<String> choices = new ArrayList<>();
        choices.add("Personal (Individual)");
        choices.add("Business (Company)");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Personal (Individual)", choices);
        dialog.setTitle("Choose Registration Type");
        dialog.setHeaderText("Choose Registration Type");
        dialog.setContentText("Select whether you are registering a Personal or Business account:");

        dialog.getDialogPane().setGraphic(null);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().startsWith("Business")) {
                selectedProfileType = "Business";
            } else {
                selectedProfileType = "Personal";
            }

            closeCurrentStage(event);
            openStage("register_individual.fxml", "Apple Banking System - Registration", null);
        }
    }

    @FXML
    public void closeAction(ActionEvent event) {
        closeCurrentStage(event);
    }

    @FXML
    public void submitAction(ActionEvent event) {
        String accNum = accountNumberField != null ? accountNumberField.getText().trim() : "";
        String pass = passwordField != null ? passwordField.getText().trim() : "";

        if (registeredUsers.containsKey(accNum) && registeredUsers.get(accNum).password.equals(pass)) {
            currentLoggedInUser = accNum;
            closeCurrentStage(event);
            openStage("main_menu.fxml", "Apple Banking System - Dashboard", null);
        } else {
            showPopup("Login Failed", "Authentication Error", "Account not registered or incorrect password!");
        }
    }

    @FXML
    public void resetAction(ActionEvent event) {
        if (accountNumberField != null) accountNumberField.clear();
        if (passwordField != null) passwordField.clear();
    }

    @FXML
    public void continueAction(ActionEvent event) {
        String id = regIdField != null ? regIdField.getText().trim() : "";
        String name = regNameField != null ? regNameField.getText().trim() : "";
        String dob = regDobField != null ? regDobField.getText().trim() : "";
        String address = regAddressField != null ? regAddressField.getText().trim() : "";
        String email = regEmailField != null ? regEmailField.getText().trim() : "";

        String postal = regPostalField != null ? regPostalField.getText().trim() : "";
        String income = regIncomeField != null ? regIncomeField.getText().trim() : "";
        String employment = regEmploymentField != null ? regEmploymentField.getText().trim() : "";
        String accountType = (regAccountTypeCombo != null && regAccountTypeCombo.getValue() != null) ? regAccountTypeCombo.getValue() : "Savings Account";
        String phone = regPhoneField != null ? regPhoneField.getText().trim() : "";
        String companyProfile = (regCompanyProfileArea != null) ? regCompanyProfileArea.getText().trim() : "";

        String generatedAccNum = generateUniqueAccountNumber();
        String generatedPassword = generateStrictPassword();
        String creationDate = LocalDate.now().toString();

        List<String> initialHistory = new ArrayList<>();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        initialHistory.add(String.format("[%s] %s Account Opened (%s) | Initial Balance: P 0.00", timestamp, selectedProfileType, accountType));

        UserAccount newUser = new UserAccount(
                generatedAccNum, generatedPassword, selectedProfileType, id, name, dob, address, email,
                postal, income, employment, accountType, phone, companyProfile, creationDate, 0.00, initialHistory
        );

        registeredUsers.put(generatedAccNum, newUser);
        saveUsersToFile();

        showPopup(
                "Registration Successful",
                "Save Your Login Credentials",
                selectedProfileType + " Account created and saved to applebank.txt!\n\n" +
                        "Account Number: " + generatedAccNum + "\n" +
                        "Password: " + generatedPassword
        );

        closeCurrentStage(event);
        openStage("login_form.fxml", "Apple Banking System - Login", null);
    }

    @FXML
    public void cancelAction(ActionEvent event) {
        closeCurrentStage(event);
        openStage("login_view.fxml", "Apple Banking System - Home", null);
    }

    @FXML
    public void openDeposit(ActionEvent event) {
        openStage("deposit.fxml", "Apple Banking System - Deposit", null);
    }

    @FXML
    public void openWithdraw(ActionEvent event) {
        openStage("withdraw.fxml", "Apple Banking System - Withdraw", null);
    }

    @FXML
    public void openViewBalance(ActionEvent event) {
        openStage("view_balance.fxml", "Apple Banking System - Balance Inquiry", null);
    }

    @FXML
    public void openViewAccountDetails(ActionEvent event) {
        openStage("view_account_details.fxml", "Apple Banking System - Account Details", null);
    }

    @FXML
    public void openViewHistory(ActionEvent event) {
        openStage("view_history.fxml", "Apple Banking System - Transaction History", null);
    }

    @FXML
    public void logoutAction(ActionEvent event) {
        currentLoggedInUser = "";
        closeCurrentStage(event);
        openStage("login_view.fxml", "Apple Banking System - Home", null);
    }

    @FXML
    public void processDeposit(ActionEvent event) {
        UserAccount user = registeredUsers.get(currentLoggedInUser);
        if (user == null || depositAmountField == null) return;

        try {
            double amount = Double.parseDouble(depositAmountField.getText().trim());
            if (amount <= 0) {
                showPopup("Invalid Amount", null, "Please enter an amount greater than P 0.00.");
                return;
            }
            user.balance += amount;
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            user.transactionHistory.add(String.format("[%s] DEPOSIT: +P %,.2f | New Balance: P %,.2f", timestamp, amount, user.balance));

            saveUsersToFile();
            showPopup("Deposit Successful", null, String.format("Deposited P %,.2f.\nNew Balance: P %,.2f", amount, user.balance));
            closeCurrentStage(event);
        } catch (NumberFormatException e) {
            showPopup("Input Error", null, "Please enter a valid numeric amount.");
        }
    }

    @FXML
    public void processWithdraw(ActionEvent event) {
        UserAccount user = registeredUsers.get(currentLoggedInUser);
        if (user == null || withdrawAmountField == null) return;

        LocalDate accountDate = LocalDate.parse(user.creationDate);
        LocalDate currentDate = LocalDate.now();

        if (user.accountType.equalsIgnoreCase("Savings Account")) {
            long yearsElapsed = ChronoUnit.YEARS.between(accountDate, currentDate);
            if (yearsElapsed < 2) {
                showPopup("Withdrawal Error", null, "WITHDRAWAL IS NOT ALLOWED ON THIS ACCOUNT, WAIT UNTIL AGREED PERIOD");
                closeCurrentStage(event);
                return;
            }
        } else if (user.accountType.equalsIgnoreCase("Investment Account")) {
            long monthsElapsed = ChronoUnit.MONTHS.between(accountDate, currentDate);
            if (monthsElapsed < 6) {
                showPopup("Withdrawal Error", null, "WITHDRAWAL IS NOT ALLOWED ON THIS ACCOUNT, WAIT UNTIL AGREED PERIOD");
                closeCurrentStage(event);
                return;
            }
        }

        try {
            double amount = Double.parseDouble(withdrawAmountField.getText().trim());
            if (amount <= 0) {
                showPopup("Invalid Amount", null, "Please enter an amount greater than P 0.00.");
                return;
            }

            if (user.accountType.equalsIgnoreCase("Investment Account") || user.accountType.equalsIgnoreCase("Cheque Account")) {
                if (amount >= user.balance) {
                    showPopup("Transaction Terminated", null, "Transaction terminated: Withdrawal amount exceeds or equals available balance.");
                    closeCurrentStage(event);
                    return;
                }
            } else {
                if (amount > user.balance) {
                    showPopup("Insufficient Funds", null, String.format("Withdrawal exceeds available balance (P %,.2f).", user.balance));
                    return;
                }
            }

            user.balance -= amount;
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            user.transactionHistory.add(String.format("[%s] WITHDRAWAL: -P %,.2f | New Balance: P %,.2f", timestamp, amount, user.balance));

            saveUsersToFile();
            showPopup("Withdrawal Successful", null, String.format("Withdrew P %,.2f.\nNew Balance: P %,.2f", amount, user.balance));
            closeCurrentStage(event);
        } catch (NumberFormatException e) {
            showPopup("Input Error", null, "Please enter a valid numeric amount.");
        }
    }

    @FXML
    public void cancelTransaction(ActionEvent event) {
        closeCurrentStage(event);
    }
    public static void main(String[] args) {
        launch(args);
    }
}

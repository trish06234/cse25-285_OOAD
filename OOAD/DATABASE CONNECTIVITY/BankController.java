import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BankController {

    private static final SecureRandom random = new SecureRandom();
    
    private static Map<String, UserAccount> registeredUsers = new HashMap<>();
    private static String currentLoggedInUser = "";

    @FXML private TextField accountNumberField;
    @FXML private TextField passwordField;
    
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

    public static void loadUsersFromFile() {
        registeredUsers = AccountDAO.loadAllAccounts();
    }

    @FXML
    public void loginAction(ActionEvent event) {
        loadUsersFromFile();
        closeStage(event);
        openStage("login_form.fxml", "Apple Banking System - Login");
    }

    @FXML
    public void registerAction(ActionEvent event) {
        closeStage(event);
        openStage("register_individual.fxml", "Apple Banking System - Registration");
    }

    @FXML
    public void submitAction(ActionEvent event) {
        loadUsersFromFile();
        String accNum = accountNumberField != null ? accountNumberField.getText().trim() : "";
        String pass = passwordField != null ? passwordField.getText().trim() : "";

        if (registeredUsers.containsKey(accNum) && registeredUsers.get(accNum).getPassword().equals(pass)) {
            currentLoggedInUser = accNum;
            closeStage(event);
            openStage("main_menu.fxml", "Apple Banking System - Dashboard");
        } else {
            showAlert("Login Failed", "Authentication Error", "Account not registered or incorrect password!");
        }
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
        String accountType = regAccountTypeCombo != null && regAccountTypeCombo.getValue() != null ? regAccountTypeCombo.getValue() : "Savings Account";
        String phone = regPhoneField != null ? regPhoneField.getText().trim() : "";

        String accNum = generateUniqueAccountNumber();
        String password = generateStrictPassword();
        String creationDate = LocalDate.now().toString();

        List<String> history = new ArrayList<>();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String initialLog = String.format("[%s] Account Opened (%s) | Initial Balance: P 0.00", timestamp, accountType);
        history.add(initialLog);

        UserAccount newUser = new UserAccount(accNum, password, id, name, dob, address, email, postal, income, employment, accountType, phone, creationDate, 0.00, history);
        
        AccountDAO.saveAccount(newUser);
        AccountDAO.addTransaction(accNum, initialLog);

        registeredUsers.put(accNum, newUser);

        showAlert("Registration Successful", "Save Your Login Credentials", "Account created successfully!\n\nAccount Number: " + accNum + "\nPassword: " + password);
        closeStage(event);
        openStage("login_form.fxml", "Apple Banking System - Login");
    }

    @FXML
    public void processDeposit(ActionEvent event) {
        UserAccount user = registeredUsers.get(currentLoggedInUser);
        if (user == null || depositAmountField == null) return;

        try {
            double amount = Double.parseDouble(depositAmountField.getText().trim());
            if (amount <= 0) {
                showAlert("Invalid Amount", null, "Please enter an amount greater than P 0.00.");
                return;
            }
            user.deposit(amount);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logEntry = String.format("[%s] DEPOSIT: +P %,.2f | New Balance: P %,.2f", timestamp, amount, user.getBalance());
            
            user.getTransactionHistory().add(logEntry);

            AccountDAO.updateBalance(currentLoggedInUser, user.getBalance());
            AccountDAO.addTransaction(currentLoggedInUser, logEntry);

            showAlert("Deposit Successful", null, String.format("Deposited P %,.2f.\nNew Balance: P %,.2f", amount, user.getBalance()));
            closeStage(event);
        } catch (NumberFormatException e) {
            showAlert("Input Error", null, "Please enter a valid numeric amount.");
        }
    }

    @FXML
    public void processWithdraw(ActionEvent event) {
        UserAccount user = registeredUsers.get(currentLoggedInUser);
        if (user == null || withdrawAmountField == null) return;

        try {
            double amount = Double.parseDouble(withdrawAmountField.getText().trim());
            if (amount <= 0) {
                showAlert("Invalid Amount", null, "Please enter an amount greater than P 0.00.");
                return;
            }

            if (!user.canWithdraw(amount)) {
                showAlert("Withdrawal Error", null, "WITHDRAWAL NOT ALLOWED: Check account lock-in period or balance limits.");
                closeStage(event);
                return;
            }

            user.withdraw(amount);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logEntry = String.format("[%s] WITHDRAWAL: -P %,.2f | New Balance: P %,.2f", timestamp, amount, user.getBalance());
            
            user.getTransactionHistory().add(logEntry);

            AccountDAO.updateBalance(currentLoggedInUser, user.getBalance());
            AccountDAO.addTransaction(currentLoggedInUser, logEntry);

            showAlert("Withdrawal Successful", null, String.format("Withdrew P %,.2f.\nNew Balance: P %,.2f", amount, user.getBalance()));
            closeStage(event);
        } catch (NumberFormatException e) {
            showAlert("Input Error", null, "Please enter a valid numeric amount.");
        }
    }
    @FXML public void openDeposit(ActionEvent e) { openStage("deposit.fxml", "Deposit Funds"); }
    @FXML public void openWithdraw(ActionEvent e) { openStage("withdraw.fxml", "Withdraw Funds"); }
    @FXML public void openViewBalance(ActionEvent e) { openStage("view_balance.fxml", "Balance Inquiry"); }
    @FXML public void openViewAccountDetails(ActionEvent e) { openStage("view_account_details.fxml", "Account Details"); }
    @FXML public void openViewHistory(ActionEvent e) { openStage("view_history.fxml", "Transaction History"); }
    
    @FXML public void logoutAction(ActionEvent e) { currentLoggedInUser = ""; closeStage(e); openStage("login_view.fxml", "Home"); }
    @FXML public void cancelAction(ActionEvent e) { closeStage(e); openStage("login_view.fxml", "Home"); }
    @FXML public void cancelTransaction(ActionEvent e) { closeStage(e); }
    @FXML public void closeAction(ActionEvent e) { closeStage(e); }
    @FXML public void resetAction(ActionEvent e) { if (accountNumberField != null) accountNumberField.clear(); if (passwordField != null) passwordField.clear(); }

    private void openStage(String fxmlFile, String title) {
        try {
            URL fileUrl = BankController.class.getResource(fxmlFile);
            if (fileUrl == null) return;
            
            FXMLLoader loader = new FXMLLoader(fileUrl);
            loader.setController(this);
            Parent root = loader.load();

            UserAccount user = registeredUsers.get(currentLoggedInUser);
            
            if (fxmlFile.equals("view_account_details.fxml") && user != null) {
                if (detailIdLabel != null) detailIdLabel.setText("ID Number: " + user.getIdNumber());
                if (detailNameLabel != null) detailNameLabel.setText("Full Name: " + user.getFullName());
                if (detailDobLabel != null) detailDobLabel.setText("Date of Birth: " + user.getDob());
                if (detailAddressLabel != null) detailAddressLabel.setText("Physical Address: " + user.getAddress());
                
                if (detailEmailLabel != null) detailEmailLabel.setText("Email Address: " + user.getEmail());
                if (detailPostalLabel != null) detailPostalLabel.setText("Postal Address: " + user.getPostal());
                if (detailIncomeLabel != null) detailIncomeLabel.setText("Source of Income: " + user.getIncome());
                if (detailEmploymentLabel != null) detailEmploymentLabel.setText("Employment Details: " + user.getEmployment());
                if (detailAccountTypeLabel != null) detailAccountTypeLabel.setText("Account Type: " + user.getAccountType());
                if (detailPhoneLabel != null) detailPhoneLabel.setText("Cellphone Number: " + user.getPhone());
            }
            if (fxmlFile.equals("view_balance.fxml") && user != null && balanceDisplayLabel != null) {
                balanceDisplayLabel.setText(String.format("Current Balance: P %,.2f", user.getBalance()));
            }
            if (fxmlFile.equals("view_history.fxml") && user != null && historyTextArea != null) {
                StringBuilder sb = new StringBuilder();
                for (String record : user.getTransactionHistory()) sb.append(record).append("\n\n");
                historyTextArea.setText(sb.toString());
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    private String generateUniqueAccountNumber() {
        String accNum;
        do {
            accNum = String.valueOf(10000000 + random.nextInt(90000000));
        } while (registeredUsers.containsKey(accNum));
        return accNum;
    }

    private String generateStrictPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) sb.append((char) ('A' + random.nextInt(26)));
        for (int i = 0; i < 6; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }
}
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDAO {

    public static void saveAccount(UserAccount user) {
    String sql = "INSERT INTO accounts(account_number, password, id_number, full_name, dob, address, email, postal, income, employment, account_type, phone, creation_date, balance) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, user.getAccountNumber());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getIdNumber());
        pstmt.setString(4, user.getFullName());
        pstmt.setString(5, user.getDateOfBirth());
        pstmt.setString(6, user.getAddress());
        pstmt.setString(7, user.getEmail());
        pstmt.setString(8, user.getPostal());
        pstmt.setString(9, user.getIncome());
        pstmt.setString(10, user.getEmployment());
        pstmt.setString(11, user.getAccountType());
        pstmt.setString(12, user.getPhone());
        pstmt.setString(13, user.getCreationDate());
        pstmt.setDouble(14, user.getBalance());

        pstmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static Map<String, UserAccount> loadAllAccounts() {
    Map<String, UserAccount> users = new HashMap<>();
    String sql = "SELECT * FROM accounts";

    try (Connection conn = DatabaseConnection.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            String accNum = rs.getString("account_number");
            String pass = rs.getString("password");
            String id = rs.getString("id_number");
            String name = rs.getString("full_name");
            String dob = rs.getString("dob");
            String address = rs.getString("address");
            String email = rs.getString("email");
            String postal = rs.getString("postal");
            String income = rs.getString("income");
            String employment = rs.getString("employment");
            String accType = rs.getString("account_type");
            String phone = rs.getString("phone");
            String creationDate = rs.getString("creation_date");
            double balance = rs.getDouble("balance");

            List<String> history = loadTransactions(accNum);

            UserAccount user = new UserAccount(accNum, pass, id, name, dob, address, email, postal, income, employment, accType, phone, creationDate, balance, history);

            users.put(accNum, user);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return users;
}
    public static void updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTransaction(String accountNumber, String details) {
        String sql = "INSERT INTO transactions(account_number, transaction_details) VALUES(?,?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, details);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<String> loadTransactions(String accountNumber) {
        List<String> history = new ArrayList<>();
        String sql = "SELECT transaction_details FROM transactions WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    history.add(rs.getString("transaction_details"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}
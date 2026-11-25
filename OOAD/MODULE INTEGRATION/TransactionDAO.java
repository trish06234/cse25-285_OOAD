import java.sql.*;

public class TransactionDAO {

    public static void addTransaction(String accountNumber, String details) {

        String sql =
            "INSERT INTO transactions(account_number, transaction_details) VALUES(?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            pstmt.setString(2, details);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
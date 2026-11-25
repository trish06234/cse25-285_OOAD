import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public static void saveCustomer(Customer customer) {

        String sql = "INSERT INTO customers(id_number, full_name, dob, address, email, postal, income, employment, phone) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getIdNumber());
            pstmt.setString(2, customer.getFullName());
            pstmt.setString(3, customer.getDateOfBirth());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getEmail());
            pstmt.setString(6, customer.getPostal());
            pstmt.setString(7, customer.getIncome());
            pstmt.setString(8, customer.getEmployment());
            pstmt.setString(9, customer.getPhone());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Customer loadCustomer(String idNumber) {

        String sql = "SELECT * FROM customers WHERE id_number = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idNumber);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    return new Customer(
                            rs.getString("id_number"),
                            rs.getString("full_name"),
                            rs.getString("dob"),
                            rs.getString("address"),
                            rs.getString("email"),
                            rs.getString("postal"),
                            rs.getString("income"),
                            rs.getString("employment"),
                            rs.getString("phone")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Customer> loadAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM customers";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                customers.add(
                        new Customer(
                                rs.getString("id_number"),
                                rs.getString("full_name"),
                                rs.getString("dob"),
                                rs.getString("address"),
                                rs.getString("email"),
                                rs.getString("postal"),
                                rs.getString("income"),
                                rs.getString("employment"),
                                rs.getString("phone")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public static void updateCustomer(Customer customer) {

        String sql = "UPDATE customers SET full_name=?, dob=?, address=?, email=?, postal=?, income=?, employment=?, phone=? WHERE id_number=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getFullName());
            pstmt.setString(2, customer.getDateOfBirth());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getPostal());
            pstmt.setString(6, customer.getIncome());
            pstmt.setString(7, customer.getEmployment());
            pstmt.setString(8, customer.getPhone());
            pstmt.setString(9, customer.getIdNumber());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(String idNumber) {

        String sql = "DELETE FROM customers WHERE id_number=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idNumber);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public class Customer {

    private String idNumber;
    private String fullName;
    private String dateOfBirth;
    private String address;
    private String email;
    private String postal;
    private String income;
    private String employment;
    private String phone;

    public Customer(String idNumber, String fullName, String dateOfBirth, String address, String email, String postal, String income, String employment, String phone) {
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.postal = postal;
        this.income = income;
        this.employment = employment;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }
}
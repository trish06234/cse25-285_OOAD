public class Personal extends Customer {

    private String employerName;
    private String employerEmail;
    private String proofOfEmploymentLink;
    private String employeeID;
    private String sourceOfIncome;
    private String dateOfBirth;

    public Personal(String name,String pin,String postalAddress,String physicalAddress,String cellphoneNumber,String employerName,String employerEmail,String proofOfEmploymentLink,String employeeID,
    String sourceOfIncome,String dateOfBirth) {

        super(name,pin,postalAddress,physicalAddress,cellphoneNumber);
        this.employerName = employerName;
        this.employerEmail = employerEmail;
        this.proofOfEmploymentLink = proofOfEmploymentLink;
        this.employeeID = employeeID;
        this.sourceOfIncome = sourceOfIncome;
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerEmail() {
        return employerEmail;
    }

    public String getProofOfEmploymentLink() {
        return proofOfEmploymentLink;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getSourceOfIncome() {
        return sourceOfIncome;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
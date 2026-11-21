public class Business extends Customer{
    private String companyRegistrationNo;
    private String companyName;
    private String companyEmail;
    private String proofOfBusinessLink;

    public Business(String postalAddress,String physicalAddress,String cellphoneNumber,String companyRegistrationNo,String companyName,String companyEmail,String proofOfBusinessLink){
        super(postalAddress,physicalAddress,cellphoneNumber);
        this.companyRegistrationNo=companyRegistrationNo;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.proofOfBusinessLink = proofOfBusinessLink;
    }

    public String getCompanyRegistrationNo() {
        return companyRegistrationNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getProofOfBusinessLink() {
        return proofOfBusinessLink;
    }
}
       
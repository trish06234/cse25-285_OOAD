public class Customer {

    private String name;
    private int pin;
    private String postalAddress;
    private String physicalAddress;
    private String cellphoneNumber;

    public Customer(String name,int pin,String postalAddress,String physicalAddress,String cellphoneNumber) {
        this.name = name;
        this.pin = pin;
        this.postalAddress = postalAddress;
        this.physicalAddress = physicalAddress;
        this.cellphoneNumber = cellphoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getPin() {
        return pin;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public boolean login(String name, int pin) {
        return this.name.equals(name) && this.pin == pin;
    }

    public void registerAccount() {
        System.out.println("Account Registered Successfully.");
    }

    public void verifyPin() {
        System.out.println("PIN Verified.");
    }

    public void openSavingsAccount() {
        System.out.println("Savings Account Opened.");
    }
}
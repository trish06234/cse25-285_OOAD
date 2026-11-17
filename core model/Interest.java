public class Interest {

    private double monthlyInterestRate;
    private String dayOfInterest;

    public Interest(double monthlyInterestRate,String dayOfInterest) {

        this.monthlyInterestRate = monthlyInterestRate;
        this.dayOfInterest = dayOfInterest;
    }

    public double getMonthlyInterestRate() {
        return monthlyInterestRate;
    }

    public String getDayOfInterest() {
        return dayOfInterest;
    }

    public void addInterest(Account account) {

        double interestAmount =account.getBalance() * monthlyInterestRate;

        account.deposit(interestAmount);

        System.out.println("Interest Added: P" + interestAmount);
    }
}
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AccountController {

    @FXML
    private TextField depositAmountField;

    @FXML
    private TextField withdrawAmountField;

    @FXML
    public void processDeposit(ActionEvent event) {

        double amount =
                Double.parseDouble(depositAmountField.getText());

        System.out.println("Deposit: " + amount);
    }

    @FXML
    public void processWithdraw(ActionEvent event) {

        double amount =
                Double.parseDouble(withdrawAmountField.getText());

        System.out.println("Withdraw: " + amount);

    }
}
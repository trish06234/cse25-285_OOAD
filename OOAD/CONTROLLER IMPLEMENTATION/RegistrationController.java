import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML private TextField regIdField;
    @FXML private TextField regNameField;
    @FXML private TextField regDobField;
    @FXML private TextField regAddressField;
    @FXML private TextField regEmailField;
    @FXML private TextField regPostalField;
    @FXML private TextField regIncomeField;
    @FXML private TextField regEmploymentField;
    @FXML private TextField regPhoneField;
    @FXML private ComboBox<String> regAccountTypeCombo;

    @FXML
    public void continueAction(ActionEvent event) {

        String id = regIdField.getText();
        String name = regNameField.getText();
        String dob = regDobField.getText();
        String address = regAddressField.getText();
        String email = regEmailField.getText();

        System.out.println("Customer Registered: " + name);

    }
}
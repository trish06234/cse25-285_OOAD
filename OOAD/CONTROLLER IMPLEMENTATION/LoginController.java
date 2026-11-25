import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    public void loginAction(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("login_form.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerAction(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("register_individual.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeAction(ActionEvent event) {
        System.exit(0);
    }
}
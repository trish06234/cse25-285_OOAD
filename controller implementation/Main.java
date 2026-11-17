import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BankController.loadUsersFromFile();
        
        BankController controller = new BankController();

        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(Main.class.getResource("login_view.fxml"));
            loader.setController(controller);
            javafx.scene.Parent root = loader.load();
            primaryStage.setTitle("Apple Banking System - Home");
            primaryStage.setScene(new javafx.scene.Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
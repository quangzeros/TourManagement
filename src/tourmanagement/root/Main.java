package tourmanagement.root;



import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
@Override
public void start(Stage primaryStage) {
try {
// Đọc file fxml và vẽ giao diện.
Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
primaryStage.setTitle("Tour Management");
primaryStage.setScene(new Scene(root));
primaryStage.show();
} catch(IOException e) {
e.printStackTrace();
}
}
public static void main(String[] args) {
launch(args);
}
}


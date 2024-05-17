package lk.ijse.gdse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/view/login_form.fxml"))));
            stage.setScene(scene);
            stage.setTitle("Login Form");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.Locale;
import java.net.URL;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

/**
 * Main starts the application
 */

public class Main extends Application {

    /**
     * starts the app
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Scheduler");
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("sample.fxml");
        loader.setLocation(xmlUrl);
        MainController controller = loader.getController();
        loader.setController(controller);
       Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
       primaryStage.show();
    }

    /**
     * launches args
     * @param args
     */

    public static void main(String[] args) {
        launch(args);
    }
}

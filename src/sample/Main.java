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

    //Correctly made time zone changes in Add Appointment, make those changes else where in the app
    // The database is stored in UTC, after those results are read, they must be translated into local time before display, convert in AppointmentsInventory
    //Main Controller throws incorrect popup

public class Main extends Application {

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


    public static void main(String[] args) {
        launch(args);
    }
}

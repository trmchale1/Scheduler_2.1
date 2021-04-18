package sample;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import java.time.ZoneId;
import java.time.format.*;
import java.time.ZonedDateTime;


/**
 * MainController class starts sign in functionality
 *
 * This class accepts a user ID and password and provides an appropriate error message in either French or English
 *
 */
public class MainController implements Initializable {

    private final String currentLocale = Locale.getDefault().toString();

    @FXML
    private Label user, pw, current_lang;
    @FXML
    private Button mainButton, cancel;
    @FXML
    private TextField user_field;
    @FXML
    private TextField pw_field;
    @FXML
    private AnchorPane ap;

    AppointmentsInventory ai = new AppointmentsInventory();

    @FXML
    private ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Appointments> appointmentList15 = FXCollections.observableArrayList();

    ResourceBundle rb = ResourceBundle.getBundle("sample/login", Locale.getDefault());
    private final ZoneId zone = ZoneId.systemDefault();

    /**
     * Initialize method starts the app
     *
     * @param url - unused
     * @param rb - resourcebundle holds the values for French/English translations
     *
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            rb = ResourceBundle.getBundle("sample/login", Locale.getDefault());
            user.setText(rb.getString("user"));
            pw.setText(rb.getString("pw"));
            mainButton.setText(rb.getString("mainButton"));
            current_lang.setText(zone.toString());
            cancel.setText(rb.getString("cancel"));
        } catch (MissingResourceException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * buttonClicked method runs when the sign in button is clicked
     * activates the signIn method
     *
     */

    @FXML
    public void buttonClicked() {
        boolean temp = signIn();
        if (temp == false) {
            return;
        }
        try {
            reminder();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/CustomerTable.fxml"));
            CustomerTable controller = new CustomerTable();
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) user_field.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks the username and password strings for length
     * writes to the login_activity.txt file the timestamp and success/failure
     * @return boolean to buttonClicked
     * if sign in fails, error message generated
     *
     * calls validate login method
     */

    @FXML
    public boolean signIn() {
        Logger l = new Logger();
        String u = user_field.getText().trim();
        String p = pw_field.getText().trim();
        if (u.length() == 0 || p.length() == 0) {
            l.logger(u,false);
            errorWindow(1);
        }
        boolean x = validateLogin(u, p);
        return x;
    }

    /**
     * checks the username and password strings for validity
     * success or failure printed to login_activity.txt
     * @param u username
     * @param p password
     * @return true false to signIn()
     */
    public boolean validateLogin(String u, String p) {
        Logger l = new Logger();
        String test = "test";
        String admin = "admin";
        if ((u.equals(test) && p.equals(test)) || (u.equals(admin) && p.equals(admin))) {
            populateReminderList();
            reminder();
            l.logger(u,true);
            return true;
        } else {
            l.logger(u,false);
            errorWindow(1);
            return false;
        }
    }


    /**
     * handleCancelAction()
     * this method is triggered when the cancel button is clicked
     * two lambda expressions in this method
     * the first is for the OK button
     * the second runs a system exit and closes the program
     */
    @FXML
    public void handleCancelAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you you want to cancel?");
        alert.setContentText("Confirm Cancel");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent((ButtonType response) -> {
                            System.exit(0);
                        }
                );
    }

    /**
     * errorWindow() runs when the username and pw are incorrect
     * @param code error code
     * @return
     */
    private boolean errorWindow(int code) {
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("again"));
            alert.setContentText(rb.getString("incorrect"));
            alert.showAndWait();
            return true;
        }

        return true;
    }


    /**
     * populateReminderList() makes a call to the database to populate appointments in a data structure
     */
    private void populateReminderList() {
        try {
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointments;");
            while (rs.next()) {
                int appointment_id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");
/*
                ZonedDateTime newzdtStart = start.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(zone);
                ZonedDateTime newzdtEnd = end.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(zone);
                LocalDateTime startDT = newLocalStart.toLocalDateTime();
                LocalDateTime endDT = newLocalEnd.toLocalDateTime();
                Timestamp fnStart = Timestamp.valueOf(startDT);
                Timestamp fnEnd = Timestamp.valueOf(endDT);
*/
                System.out.println(start);
                Appointments apptObject = new Appointments(appointment_id, title, description, location, type, start, end, customer_id, user_id, contact_id);
                ai.addAppointment(apptObject);
            }
            appointmentList = ai.getAllAppointments();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * iterates through the data structure created in populateReminderList() and creates a popup if there is an appointment in the next 15 minutes
     */
    private void reminder() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus15Min = now.plusMinutes(15);
        Appointments b = new Appointments();
        int x = 0;
        int temp = 0;
        System.out.println(user_field.getText().trim().equalsIgnoreCase("test"));
        if(user_field.getText().trim().equalsIgnoreCase("test")){
            temp = 1;
        } else if (user_field.getText().trim().equalsIgnoreCase("admin")){
            temp = 2;
        }
        System.out.println("List Size: " + appointmentList.size());
        for(Appointments a : appointmentList){
            System.out.println(a.getStart());
            System.out.println("temp value: " + temp);
            if(a.getStart().toLocalDateTime().isAfter(now.minusMinutes(1)) && a.getStart().toLocalDateTime().isBefore(nowPlus15Min) && a.getUser_id() == temp){
                x = x + 1;
                b = a;
            }
        }
        if (x == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment Reminder");
            alert.setHeaderText("Reminder: ");
            alert.setContentText("You have no appointments coming up");
            alert.showAndWait();
        } else {
            Integer appt_id = b.getAppointment_id();
            Timestamp start = b.getStart();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointment Reminder");
            alert.setHeaderText("Reminder: You have the following appointment set for the next 15 minutes.");
            alert.setContentText(appt_id + " is currently set for " + start + ".");
            alert.showAndWait();
        }
    }
}

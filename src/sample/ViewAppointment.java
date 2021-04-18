package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.*;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

/**
 * View Appointment UI for appointments
 */

public class ViewAppointment implements Initializable {

    @FXML
    public TableView<Appointments> apptTable = new TableView<Appointments>();

    @FXML
    private ObservableList<Appointments> appointmentSet = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Appointments> appointmentSetWeek = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Appointments> appointmentSetMonth = FXCollections.observableArrayList();

    int week = 0;
    int month = 0;
    public TableColumn appointment_id;
    public TableColumn title;
    public TableColumn description;
    public TableColumn location;
    public TableColumn contact;
    public TableColumn type;
    public TableColumn start;
    public TableColumn end;
    public TableColumn customer_id;
    public RadioButton rad_week;
    public RadioButton rad_month;

    AppointmentsInventory ai = new AppointmentsInventory();

    ContactsInventory ci = new ContactsInventory();

    CustomerInventory custInv = new CustomerInventory();

    public ViewAppointment (){

    }

    /**
     * Initialize starts the object
     * @param url
     * @param rb
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        appointment_id.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        try {
            popAppointment();
            popContacts();
            ToggleGroup toggleGroup = new ToggleGroup();
            rad_week.setToggleGroup(toggleGroup);
            rad_month.setToggleGroup(toggleGroup);
            fillWeekMonth();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Takes input from cancel, two lambda expressions
     */
    @FXML
    public void handleCancelAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you you want to exit?");
        alert.setContentText("Confirm Exit");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent((ButtonType response) -> {
                            System.exit(0);
                        }
                );
    }

    /**
     * Iterates through appointments to set by month and by week
     */
    public void fillWeekMonth(){
        appointmentSet = ai.getAllAppointments();
        for (Appointments a : appointmentSet) {
//            System.out.println(a.getStart().toLocalDateTime().toLocalDate().getMonth());
//            System.out.println(LocalDate.now().getMonth());
            if (a.getStart().toLocalDateTime().toLocalDate().getMonth() == LocalDate.now().getMonth()) {
                appointmentSetMonth.add(a);
            }
        }
        for (Appointments a : appointmentSet) {
            Period between = Period.between(a.getStart().toLocalDateTime().toLocalDate(), LocalDate.now());
            if (between.getDays() < 7 && between.getMonths() == 0 && between.getDays() > -7) {
                appointmentSetWeek.add(a);
            }
        }
    }

    /**
     * Sets the table by month
     */
    @FXML
    public void radMonthAct(){
        apptTable.setItems(appointmentSetMonth);
    }

    /**
     * Sets the table by week
     */
    @FXML
    public void radWeekAct() {
        apptTable.setItems(appointmentSetWeek);
    }

    /**
     * Activated by Add Appointments
     * @param e
     */
    @FXML
    private void addAppointments(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
            AddAppointment controller = new AddAppointment(ai,ci,custInv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Activated by button View Customers
     * @param e
     */
    @FXML
    private void viewCustomers(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerTable.fxml"));
            CustomerTable controller = new CustomerTable();
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch(IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Activated by Edit Appointment
     * @param event
     */
    @FXML
    private void editAppointment(ActionEvent event) {
        try {
            Appointments selected = apptTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                errorWindow(1,selected);
                return;
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAppointment.fxml"));
                EditAppointment controller = new EditAppointment(selected,ai,ci,custInv);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * activated by button Customer Reports
     * @param event
     */
    @FXML
    private void CustomerReports(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerReports.fxml"));
                CustomerReports controller = new CustomerReports(ai,ci,custInv);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Activated by the button contact reports
     * @param event
     */
    @FXML
    private void ContactReports(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactSchedule.fxml"));
            ContactSchedule controller = new ContactSchedule(ai,ci);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Activated by the user reports button
     * @param event
     */
    @FXML
    private void userReports(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserReports.fxml"));
            UserReports controller = new UserReports(ai);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * delete customer appointments
     * @param event
     */
    @FXML
    private void deleteCustApp(ActionEvent event) {
        try {
            Appointments selected = apptTable.getSelectionModel().getSelectedItem();
            boolean boo =  errorWindow(2,selected);
            if(boo == false){
                return;
            }

            if(rad_week.isSelected()){
                appointmentSetWeek.remove(selected);
                apptTable.refresh();
            } else if (rad_month.isSelected()) {
                appointmentSetMonth.remove(selected);
                apptTable.refresh();
            }
            ai.deleteAppointmentt(selected.getAppointment_id());
            apptTable.refresh();
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?");
            stmt.setInt(1, selected.getAppointment_id());
            stmt.executeUpdate();
            conn.close();
            if (selected == null) {
                errorWindow(1,selected);
                return;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * pop up error
     * @param code
     * @param selected
     * @return
     */
    private boolean errorWindow(int code,Appointments selected) {
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Inventory!");
            alert.setContentText("There's nothing to select!");
            alert.showAndWait();
            return true;
        }
        if (code == 2) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Are you sure?");
            alert.setContentText("Are you sure you want to delete Appointment ID:" + selected.getAppointment_id() + " and Type: " + selected.getType());
            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {
                return false;
            } else if (result.get() == ButtonType.OK) {
                return true;
            } else if (result.get() == ButtonType.CANCEL) {
                return false;
            }
        }
        if (code == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Pick!");
            alert.setContentText("Choose by week or by month!");
            alert.showAndWait();
            return true;
        }
        return true;
    }

    /**
     * Reads from the database and populates the model with appointments
     */

    public void popAppointment() {
        try {
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointments join users on users.User_ID = appointments.User_ID;");
            while(rs.next()){
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
                Appointments apptObject = new Appointments(appointment_id,title,description,location,type,start,end,customer_id,user_id,contact_id);
                ai.addAppointment(apptObject);
            }
        } catch (ConcurrentModificationException x){
            x.getMessage();
            x.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Reads from the database and populates the model with contacts
     */

    public void popContacts() {
        try {
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contacts;");
            while(rs.next()){
                int contact_id = rs.getInt("Contact_ID");
                String contact_name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contacts con = new Contacts(contact_id,contact_name,email);
                ci.addContact(con);
            }
        } catch (ConcurrentModificationException x){
            x.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
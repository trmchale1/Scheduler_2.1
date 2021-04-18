package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Creates a UI to view schedule by contact
 */

public class ContactSchedule implements Initializable  {

    @FXML
    public TableView<ContactReports> table_con1 = new TableView<>();

    @FXML
    private ObservableList<ContactReports> con1 = FXCollections.observableArrayList();

    @FXML
    private ObservableList<ContactReports> con2 = FXCollections.observableArrayList();

    @FXML
    private ObservableList<ContactReports> con3 = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Appointments> apptList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Contacts> contactList = FXCollections.observableArrayList();

    ContactsInventory ci = new ContactsInventory();

    AppointmentsInventory ai = new AppointmentsInventory();

    public RadioButton rad_1;
    public RadioButton rad_2;
    public RadioButton rad_3;

    @FXML
    public TableColumn appointment_id;
    public TableColumn title;
    public TableColumn type;
    public TableColumn description;
    public TableColumn start;
    public TableColumn end;
    public TableColumn customer_id;

    /**
     * Constructor for Contact Schedule
     * @param ai
     * @param ci
     */
    ContactSchedule(AppointmentsInventory ai, ContactsInventory ci) {
        this.ai = ai;
        apptList = ai.getAllAppointments();
        this.ci = ci;
        contactList = ci.getAllContacts();
    }

    /**
     * initialize method sets the CellValueFactory for the UI
     * initializes radio buttons
     * @param url
     * @param rb
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        appointment_id.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));;
        title.setCellValueFactory(new PropertyValueFactory<>("title"));;
        type.setCellValueFactory(new PropertyValueFactory<>("type"));;
        description.setCellValueFactory(new PropertyValueFactory<>("description"));;
        start.setCellValueFactory(new PropertyValueFactory<>("start"));;
        end.setCellValueFactory(new PropertyValueFactory<>("end"));;
        customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));;
        try {
            printAppointmentsByContact();
            ToggleGroup toggleGroup = new ToggleGroup();
            rad_1.setToggleGroup(toggleGroup);
            rad_2.setToggleGroup(toggleGroup);
            rad_3.setToggleGroup(toggleGroup);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * sets the appointmentList data structure according to Contact
     */
    public void printAppointmentsByContact() {
        ArrayList<Integer> all = new ArrayList<Integer>();
        ArrayList<Integer> unique = new ArrayList<Integer>();
        for (Appointments a : apptList) {
            all.add(a.getContact_id());
            if (unique.contains(a.getContact_id()) == false) {
                unique.add(a.getContact_id());
            }
        }
        for(Appointments a : apptList){
            if(a.getContact_id() == 1) {
                ContactReports insert1 = new ContactReports(a.getAppointment_id(),a.getTitle(),a.getType(),a.getDescription(),a.getStart(),a.getEnd(),a.getCustomer_id());
                con1.add(insert1);
            } else if(a.getContact_id() == 2) {
                ContactReports insert2 = new ContactReports(a.getAppointment_id(),a.getTitle(),a.getType(),a.getDescription(),a.getStart(),a.getEnd(),a.getCustomer_id());
                con2.add(insert2);
            } else if(a.getContact_id() == 3) {
                ContactReports insert3 = new ContactReports(a.getAppointment_id(),a.getTitle(),a.getType(),a.getDescription(),a.getStart(),a.getEnd(),a.getCustomer_id());
                con3.add(insert3);
            }
        }
        }

    /**
     * sets 1st contact data to table
     */
    @FXML
        private void radcon1(){
            table_con1.setItems(con1);
        }

    /**
     * sets 2nd contact data to table
     */
    @FXML
    private void radcon2(){
            table_con1.setItems(con2);
        }

    /**
     * sets 3rd contact data to table
     */
    @FXML
        private void radcon3(){
            table_con1.setItems(con3);
        }

    /**
     * sends user back to the View Appointment UI
     * @param e
     */
    @FXML
        private void onBack(ActionEvent e){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAppointment.fxml"));
                    ViewAppointment controller = new ViewAppointment();
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
        }





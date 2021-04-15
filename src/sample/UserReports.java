package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.ResourceBundle;

/**
 * User Reports UI
 */

public class UserReports implements Initializable {
    @FXML
    public TableView<UserReportInventory> table_con1 = new TableView<>();
    @FXML
    private ObservableList<UserReportInventory> usrrep1 = FXCollections.observableArrayList();
    @FXML
    private ObservableList<UserReportInventory> usrrep2 = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Appointments> apptList = FXCollections.observableArrayList();

    AppointmentsInventory ai = new AppointmentsInventory();

    public RadioButton rad_1;
    public RadioButton rad_2;

    @FXML
    public TableColumn appointment_id;
    public TableColumn title;
    public TableColumn type;
    public TableColumn description;
    public TableColumn start;
    public TableColumn end;
    public TableColumn customer_id;

    /**
     * User Reports constructor
     * @param ai Appointments Model
     */

    UserReports(AppointmentsInventory ai) {
        this.ai = ai;
        apptList = ai.getAllAppointments();
    }

    /**
     * Initialize the object
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
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Set Reports by user
     */

    public void printAppointmentsByContact() {
        ArrayList<Integer> all = new ArrayList<Integer>();
        ArrayList<Integer> unique = new ArrayList<Integer>();
        for (Appointments a : apptList) {
            all.add(a.getContact_id());
            if (unique.contains(a.getUser_id()) == false) {
                unique.add(a.getUser_id());
            }
        }
        for(Appointments a : apptList){
            if(a.getUser_id() == 1) {
                UserReportInventory insert1 = new UserReportInventory(a.getAppointment_id(),a.getTitle(),a.getType(),a.getDescription(),a.getStart(),a.getEnd(),a.getCustomer_id());
                usrrep1.add(insert1);
            } else if(a.getUser_id() == 2) {
                UserReportInventory insert2 = new UserReportInventory(a.getAppointment_id(),a.getTitle(),a.getType(),a.getDescription(),a.getStart(),a.getEnd(),a.getCustomer_id());
                usrrep2.add(insert2);
            }
        }
    }

    /**
     * set the table with user 1
     */
    @FXML
    private void radcon1(){
        table_con1.setItems(usrrep1);
    }

    /**
     * set the table with user 2
     */
    @FXML
    private void radcon2(){
        table_con1.setItems(usrrep2);
    }

    /**
     * Back to appointments
     * @param event
     * @throws IOException
     */
    @FXML
    private void onBack(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAppointment.fxml"));
        ViewAppointment controller = new ViewAppointment();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



}

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Month;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;

/**
 * Creates a UI for Reports for Customer
 */
public class CustomerReports implements Initializable {

    @FXML
    public TableView<CustomerTypeReport> table_type = new TableView<>();

    @FXML
    public TableView<CustomerMonthReport> table_month = new TableView<>();

    @FXML
    private ObservableList<Appointments> appts = FXCollections.observableArrayList();

    @FXML
    private ObservableList<CustomerTypeReport> appt_type_list = FXCollections.observableArrayList();

    @FXML
    private ObservableList<CustomerMonthReport> appt_month_list = FXCollections.observableArrayList();

    CustomerInventory custInv = new CustomerInventory();

    AppointmentsInventory ai = new AppointmentsInventory();

    ContactsInventory ci = new ContactsInventory();

    @FXML
    public TableColumn column_type;
    public TableColumn type_value;
    public TableColumn column_month;
    public TableColumn month_value;

    /**
     * Constructor for Customer Reports
     * @param ai - Model for appointments
     * @param ci - Model for Contacts
     * @param custInv - Mondel for Customers
     */
    CustomerReports(AppointmentsInventory ai, ContactsInventory ci, CustomerInventory custInv) {
        this.ai = ai;
        appts = ai.getAllAppointments();
        this.ci = ci;
        this.custInv = custInv;
    }

    /**
     * Runs the methods popTypeSet() and popMonthSet()
     * @param url
     * @param rb
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        column_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        type_value.setCellValueFactory(new PropertyValueFactory<>("total"));
        column_month.setCellValueFactory(new PropertyValueFactory<>("month"));
        month_value.setCellValueFactory(new PropertyValueFactory<>("month_total"));
        try {
            popTypeSet();
            popMonthSet();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Counts the appointments according to type and creates a data structure
     */
    public void popTypeSet() {
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> aptList = new ArrayList<String>();
        for (Appointments a : appts) {
            aptList.add(a.getType());
            if (list.contains(a.getType()) == false) {
                list.add(a.getType());
            }
        }
        for (String a : list) {
            System.out.println("Frequency of Type: " + a + " " + Collections.frequency(aptList, a));
            CustomerTypeReport insert = new CustomerTypeReport(a, Collections.frequency(aptList, a));
            appt_type_list.add(insert);
        }
        System.out.println(appt_type_list.size());
        try {
            table_type.setItems(appt_type_list);
        } catch (IllegalStateException ex) {
            ex.getStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Counts the appointments per month and populates a data structure
     */
    public void popMonthSet() {
        ArrayList<Month> all = new ArrayList<Month>();
        ArrayList<Month> unique = new ArrayList<Month>();
        for (Appointments a : appts) {
            LocalDate d = a.getStart().toLocalDateTime().toLocalDate();
            all.add(d.getMonth());
            if(unique.contains(d.getMonth()) == false){
                unique.add(d.getMonth());
            }
        }
        for(Month a : unique){
            System.out.println("Frequency of Type: " + a + " " + Collections.frequency(all,a));
            CustomerMonthReport insert = new CustomerMonthReport(a,Collections.frequency(all,a));
            appt_month_list.add(insert);
        } try {
            table_month.setItems(appt_month_list);
        } catch (IllegalStateException ex) {
            ex.getStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * sends the user back to the Customer Table UI
     * @param e
     */
    @FXML
    public void custButton(ActionEvent e){
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
     * sends the user back to the View Appointment UI
     * @param e
     */
    @FXML
    public void appButton(ActionEvent e){
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


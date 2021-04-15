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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;
import java.util.Optional;

/**
 * ViewAppointment is a User Interface class that holds the Customers, it has buttons for Add, Edit and Delete functionality
 */

public class CustomerTable implements Initializable {
    @FXML
    private ObservableList<Contacts> contactCollection = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Divisions> divisionsCollection = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Customers> customerCollection = FXCollections.observableArrayList();
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    public TableView<Customers> customerTable = new TableView<Customers>();

    public TableColumn customer_id;
    public TableColumn customer_name;
    public TableColumn address;
    public TableColumn postal_code;
    public TableColumn phone;
    public TableColumn division;
    public TableColumn country;

    private Customers CustomerClassObj;

    AppointmentsInventory ai = new AppointmentsInventory();

    CustomerInventory customerInventory = new CustomerInventory();

    ContactsInventory ci = new ContactsInventory();

    /**
     * Constructor for CustomerTable
     *
     * @param customer - CustomerInventory, holds Customers objects
     * @param divisionsCollection - Collection of Divisions, when Add or Edit call back to the Table
     */

    public CustomerTable (CustomerInventory customer, ObservableList<Divisions> divisionsCollection){
        this.customerInventory = customer;
        this.divisionsCollection = divisionsCollection;
    }

    /**
     * CustomerTable contructor, when the app starts
     */

    public CustomerTable() {

    }

    /**
     * initialize si the first method called, handles setting cellValueFactory for the table
     * calls database to load data into the app
     * sets Customer data to the table
     * @param url - unused
     * @param rb - unused
     */

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        customer_name.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postal_code.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        division.setCellValueFactory(new PropertyValueFactory<>("division"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        try {
            if (customerInventory.size() == 0) {
                popCustomers();
                updateModel();
                customerCollection.setAll(customerInventory.getAllCustomers());
                customerTable.setItems(customerCollection);
            } else
                refreshTable();
        }
          catch (Exception exception) {
                exception.printStackTrace();
        }
    }

    /**
     * refreshTable() updates the table according to changes made in the model
     */
    private void refreshTable(){
        System.out.println("refresh table");
        ObservableList<Customers> c = customerInventory.getAllCustomers();
        customerCollection.setAll(customerInventory.getAllCustomers());
        customerTable.setItems(customerCollection);
        customerTable.refresh();
    }

    @FXML
    private void handleShowView(ActionEvent e) {
        String view = (String) ((Node)e.getSource()).getUserData();
        loadFXML(getClass().getResource(view));
    }

    /**
     * acHandleShowView runs when the Add Customer button is pressed
     *
     *
     * @param e
     */

    @FXML
    private void acHandleShowView(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
            AddCustomer controller = new AddCustomer(this.divisionsCollection,this.customerInventory);
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
     * runs when the View Appointment button is pressed
     * @param e
     */

    @FXML
    private void vaHandleShowView(ActionEvent e) {
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

    /**
     * runs when the edit Customer button is pressed
     * @param event
     */

    @FXML
    private void editCustomer(ActionEvent event) {
        try {
            Customers selected = customerTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                errorWindow(1,selected);
                return;
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCustomer.fxml"));
                EditCustomer controller = new EditCustomer(selected,customerInventory,divisionsCollection);
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
     *
     * @param code error code triggers response
     * @param selected Customer object for customer reference
     * @return
     */

    private boolean errorWindow(int code,Customers selected) {
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
            alert.setContentText("Are you sure you want to delete this Customer: " + selected.getCustomer_name() + "- and their appointments?");
            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {
                return false;
            } else if (result.get() == ButtonType.OK) {
                return true;
            } else if (result.get() == ButtonType.CANCEL) {
                return false;
            }
        }
        return true;
    }

    /**
     * sets the view
     * @param url
     */
    private void loadFXML(URL url) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            mainBorderPane.setCenter(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads data from the database and populates countries and divisions
     */
    public void updateModel() {
        try {
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From countries c join first_level_divisions d on c.Country_ID = d.Country_ID");
            while (rs.next()) {
                String division = rs.getString("Division");
                int division_id = rs.getInt("Division_ID");
                int country_id = rs.getInt("Country_ID");
                Divisions d = new Divisions(division_id, division, country_id);
                divisionsCollection.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    /**
     * Reads data from the database and populates customers
     */
    public void popCustomers() {
        try {
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From customers c join first_level_divisions d On c.Division_ID = d.Division_ID join countries co On co.Country_ID = d.Country_ID");
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal_code = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String division = rs.getString("Division");
                String country = rs.getString("Country");
                Customers custObject = new Customers(id, name, address, postal_code, phone, division, country);
                customerInventory.addCustomer(custObject);
            }
        }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    /**
     * REWRITE!!!!
     *
     * Triggered when the Delete Customer button is pressed
     * Deletes the Customer, and also their appointments
     * @param event
     */

    @FXML
    private void deleteCustApp(ActionEvent event) {
        try {
            Customers selected = customerTable.getSelectionModel().getSelectedItem();
            boolean boo =  errorWindow(2,selected);
            if(boo == false){
                return;
            }
            customerInventory.deleteCustomer(selected);
            customerCollection.remove(selected);
            ai.deleteAppointment(selected.getCustomer_id());
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM appointments WHERE Customer_ID = ?");
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM customers WHERE Customer_ID = ?");
            stmt1.setInt(1, selected.getCustomer_id());
            stmt.setInt(1, selected.getCustomer_id());
            stmt.executeUpdate();
            stmt1.executeUpdate();
            conn.close();
            if (selected == null) {
                boolean x = errorWindow(1,selected);
                return;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void dbScript() throws Exception {
        try {
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO countries VALUES(1,\t'U.S',\tNOW(), 'script', NOW(), 'script');");
            stmt.execute();
            stmt.close();
            System.out.println("Inserts Successful");
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}



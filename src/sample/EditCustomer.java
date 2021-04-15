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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Period;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Activates the Edit Customer object
 */

public class EditCustomer implements Initializable {
    @FXML
    private ObservableList<Divisions> divisionCollection = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<String> uk = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<String> can = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<String> us = FXCollections.observableArrayList();
    @FXML
    private final ObservableList<Customers> customerCollection = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox choice_div;
    @FXML
    private ChoiceBox choice_count;
    public TextField customer_id;
    public TextField customer_name;
    public TextField address;
    public TextField postal_code;
    public TextField phone;
    public Customers customer;
    public CustomerInventory customerInventory;

    /**
     * Constructor passes model - customer, custInv, divisions
     * @param customer
     * @param customerInventory
     * @param divisionCollection
     */
    public EditCustomer(Customers customer, CustomerInventory customerInventory, ObservableList<Divisions> divisionCollection){
        this.customer = customer;
        this.customerInventory = customerInventory;
        this.divisionCollection = divisionCollection;
    }

    public EditCustomer(){

    }

    /**
     * Initializes the object
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRegions();
        resetFields();
    }

    /**
     * sets regions according to id
     */
    public void setRegions(){
        for(Divisions d : divisionCollection){
            int div_id =  d.getDivision_id();
            String div = d.getDivision();
            int c_id = d.getCountry_id();
            if(c_id == 1){
                us.add(div);
            } else if (c_id == 2){
                uk.add(div);
            } else if (c_id == 3){
                can.add(div);
            }
        }
        for(String u: can)
            choice_count.setItems(FXCollections.observableArrayList("Canada","United Kingdom","United States"));
        choice_count.setOnAction((Event) -> {
            int index = choice_count.getSelectionModel().getSelectedIndex();
            System.out.println(index);
            if(index == 0){
                choice_div.setItems(can);
            } else if (index == 1) {
                choice_div.setItems(uk);
            } else if(index == 2){
                choice_div.setItems(us);
            }
        });

    }

    /**
     * exit the UI
     * @param event
     */
    @FXML
    private void exitEditCustomer(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        CustomerTable(event);
    }

    /**
     * sets the field to starting text
     */
    private void resetFields() {
        try {
            customer_id.setText(String.valueOf(customer.getCustomer_id()));
            customer_name.setText(customer.getCustomer_name());
            address.setText(customer.getAddress());
            postal_code.setText(customer.getPostal_code());
            phone.setText(customer.getPhone());
            choice_div.setValue(customer.getDivision());
            choice_count.setValue(customer.getCountry());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the Customer Table
     *
     * @param event
     */
    private void CustomerTable(Event event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerTable.fxml"));
            CustomerTable controller;
            controller = new CustomerTable(customerInventory,divisionCollection);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        } }

    /**
     * error window
     * @param code
     * @return
     */
    private boolean errorWindow(int code){
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try again");
            alert.setContentText("Please add a Division and Country");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * saves user input
     * @param event
     */
    @FXML
    private void saveAppointment(Event event){
        Customers customerClassObj;
        try {
            int id = Integer.parseInt(customer_id.getText().trim());
            String name = customer_name.getText().trim();
            String add_temp = address.getText().trim();
            String postal_code_temp = postal_code.getText().trim();
            String phone_temp = phone.getText().trim();
            String div_temp = (String) choice_div.getValue();
            String country_temp = (String) choice_count.getValue();
            int div_id = 0;
            for(Divisions d : divisionCollection){
                if(d.getDivision() == div_temp)
                    div_id = d.getDivision_id();
            }
            if(country_temp == null || div_id == 0){
                boolean temp = errorWindow(1);
                if(temp == false){
                    return;
                }
            }
            customerClassObj = new Customers(id,name,add_temp,postal_code_temp,phone_temp,div_temp,country_temp);
            customerInventory.deleteCustomer(customer);
            customerInventory.addCustomer(customerClassObj);
            String created_by = "script";
            String last_updated_by = "script";
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?");
            stmt.setString(1, name);
            stmt.setString(2, add_temp);
            stmt.setString(3, postal_code_temp);
            stmt.setString(4, phone_temp);
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            stmt.setString(6, created_by);
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            stmt.setString(8, last_updated_by);
            stmt.setInt(9, div_id);
            stmt.setInt(10, id);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        CustomerTable(event);
    }
}

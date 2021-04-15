package sample;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * This user interface adds customers, is triggered by the View Customer class
 */
public class AddCustomer implements Initializable {
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

    public TextField customer_id = new TextField();
    public TextField customer_name = new TextField();
    public TextField address = new TextField();
    public TextField postal_code = new TextField();
    public TextField phone = new TextField();

    CustomerInventory customerInventory;

    /**
     * Constructor for add customer
     * @param divisions passes divisions to the object
     * @param customer passes customers to the model
     */
    public AddCustomer(ObservableList<Divisions> divisions,CustomerInventory customer){
        this.divisionCollection = divisions;
        this.customerInventory = customer;
    }

    /**
     * runs the methods setRegions() resetFields()
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRegions();
        resetFields();
    }

    /**
     * sets text to divisions and countries
     */
    public void setRegions(){
        for(Divisions d : divisionCollection){
            int div_id =  d.getDivision_id();
            String div = d.getDivision();
            System.out.println(div_id + " " + div);
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
     * triggers the exit
     * @param event
     */
    @FXML
    private void exitAddCustomer(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        CustomerTable(event);
    }

    /**
     * sets the fields with canned text
     */
    private void resetFields() {
        try {
            customer_name.setText("Customer Name");
            address.setText("Address");
            postal_code.setText("Postal Code");
            phone.setText("Phone");
            Random rand = new Random();
            int value = rand.nextInt(1000);
            customer_id.setText(String.valueOf(value));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * sends the user back to the CustomerTable
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
     * Error ran per error code
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
     * saves the customer to model and db per the input
     * @param event
     */
    @FXML
    private void saveCustomer(Event event){
        Customers customerClassObj;
        try {
            int id = Integer.parseInt(customer_id.getText().trim());
            String name = customer_name.getText().trim();
            String add_temp = address.getText().trim();
            String postal_code_temp = postal_code.getText().trim();
            String phone_temp = phone.getText().trim();
            String div_temp = (String) choice_div.getValue();
            int div_id = 0;
            for(Divisions d : divisionCollection){
                if(d.getDivision() == div_temp)
                    div_id = d.getDivision_id();
            }
            String country_temp = (String) choice_count.getValue();
            if(country_temp == null || div_id == 0){
                boolean temp = errorWindow(1);
                if(temp == false){
                    return;
                }
            }
            String created_by = "script";
            String last_updated_by = "script";
            customerClassObj = new Customers(id,name,add_temp,postal_code_temp,phone_temp,div_temp,country_temp);
            customerInventory.addCustomer(customerClassObj);
            // add to db
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO customers (Customer_ID,Customer_Name,Address,Postal_Code,Phone,Create_Date,Created_By,Last_Update,Last_Updated_By,Division_ID) VALUES(?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, add_temp);
            stmt.setString(4, postal_code_temp);
            stmt.setString(5, phone_temp);
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setString(7, created_by);
            stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            stmt.setString(9, last_updated_by);
            stmt.setInt(10, div_id);
            stmt.executeUpdate();
            conn.close();
            System.out.println("Inserts Successful");
        } catch ( ClassNotFoundException ex ) {
            ex.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        CustomerTable(event);
    }
}
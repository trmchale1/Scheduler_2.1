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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.control.DatePicker;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This user interface adds appointments, is triggered by the View Appointment class
 */

public class AddAppointment implements Initializable {
    @FXML
    private ObservableList<Appointments> appointments = FXCollections.observableArrayList();
    @FXML
    public TextField appointment_id = new TextField();
    @FXML
    public TextField title = new TextField();
    @FXML
    public TextField description = new TextField();
    @FXML
    public TextField location = new TextField();
    @FXML
    public TextField type = new TextField();
    @FXML
    public TextField start = new TextField();
    @FXML
    public TextField end = new TextField();
    @FXML
    public TextField customer_id = new TextField();
    @FXML
    public TextField user_id = new TextField();
    @FXML
    public ObservableList<Integer> times = FXCollections.observableArrayList();
    @FXML
    public ObservableList<Integer> minutes = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox choice_con;
    @FXML
    public DatePicker startDate = new DatePicker();
    @FXML
    public DatePicker endDate = new DatePicker();
    @FXML
    private TextField startHour;
    @FXML
    private TextField endHour;

    @FXML
    private TextField startMinutes;
    @FXML
    private TextField endMinutes;

    AppointmentsInventory ai;

    @FXML
    public ObservableList<Contacts> contactCollection = FXCollections.observableArrayList();
    @FXML
    public ObservableList<String> contactName = FXCollections.observableArrayList();

    ContactsInventory ci = new ContactsInventory();

    CustomerInventory custInv = new CustomerInventory();

    /**
     * This contructor adds and appointment
     * @param ai - appointmentInventory part of the model
     * @param ci - contactsInventory part of the model
     * @param custInv - customerInventory, part of the model
     */

    public AddAppointment(AppointmentsInventory ai, ContactsInventory ci,CustomerInventory custInv){
        this.ci = ci;
        this.ai = ai;
        this.custInv = custInv;
        popCustomers();
    }

    /**
     * Initialize starts the object
     * runs setContacts(), resetFields(), setTimes() methods
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setContacts();
        resetFields();
    }

    /**
     * exits add appointment
     * @param event
     */
    @FXML
    private void exitAddAppointment(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        ViewAppointment(event);
    }

    /**
     * populates choice box with contacts
     */
    public void setContacts(){
        appointments = ai.getAllAppointments();
        contactCollection = ci.getAllContacts();
        for(Contacts c : contactCollection){
            contactName.add(c.getContact_name());
        }
        choice_con.setItems(contactName);
    }


    /**
     * sends the user back to the View Appointment UI
     * @param event
     */
    private void ViewAppointment(Event event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAppointment.fxml"));
            ViewAppointment controller;
            controller = new ViewAppointment();
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * sets the fields with temp variables
     */
    private void resetFields() {
        try {
            title.setText("Title");
            description.setText("Description");
            location.setText("Location");
            type.setText("Type");
            customer_id.setText("1");
            user_id.setText("1");
            Random rand = new Random();
            int value = rand.nextInt(1000);
            appointment_id.setText(String.valueOf(value));
            user_id.setText("1");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * runs error popups
     * @param code - code to run
     * @return returns boolean
     */
    private boolean errorWindow(int code){
        if (code == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try again");
            alert.setContentText("Cannot schedule an appointment on a Weekend");
            alert.showAndWait();
            return false;
        }
        if (code == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try Again");
            alert.setContentText("Appointments must be 8am and 10pm");
            alert.showAndWait();
            return false;
        }
        if (code == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try Again");
            alert.setContentText("This customer already has an appointment at this time");
            alert.showAndWait();
            return false;
        }
        if (code == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try Again");
            alert.setContentText("The end time of this appointment is before the start");
            alert.showAndWait();
            return false;
        }
        if (code == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try Again");
            alert.setContentText("Customer ID does not exist");
            alert.showAndWait();
            return false;
        }
        if (code == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try Again");
            alert.setContentText("Hours must be from 0 to 23");
            alert.showAndWait();
            return false;
        }
        if (code == 7) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try Again");
            alert.setContentText("Minutes must be from 0 to 60");
            alert.showAndWait();
            return false;
        }
        if (code == 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Try Again");
            alert.setContentText("No Contact!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Tests if the appointment is between 8am and 10pm
     * @param current_time
     * @return
     */
    private boolean testOfficeHours(LocalTime current_time){
        boolean local = true;
        System.out.println(current_time);
        LocalTime time1 = LocalTime.of(7, 00, 00);
        LocalTime time2 = LocalTime.of(21, 00, 00);
        boolean morn = current_time.isBefore(time1);
        boolean aft = current_time.isAfter(time2);
        if( morn == true || aft == true ){
            local = errorWindow(2);
        }
        return local;
    }

    /**
     * tests if contact is null
     * @param con
     * @return
     */
    private boolean testContactNull(int con){
        if(con == 0){
            errorWindow(8);
            return false;
        }
        return true;
    }

    /**
     * Tests if an appointment overlaps with another
     * @param cust_id input customer id
     * @param start new appointment to check
     * @param end new appointment to check
     * @return true / false if appointments conflict
     */
    private boolean searchCustApt(int app_id,int cust_id, Timestamp start, Timestamp end){
        for(Appointments a : appointments){
            if(a.getCustomer_id() == cust_id && a.getAppointment_id() != app_id){
                if (start.before(a.getStart()) && end.after(a.getEnd())) {
 //                   System.out.println(a.getCustomer_id() + "Start: " + start + " before a.getStart: " + a.getStart() + " AND end: " + end + " after a.getEnd: " + a.getEnd());
 //                   System.out.println("Return false - appointment outside another appointment");
                    errorWindow(3);
                    return false;
                } else if (start.after(a.getStart()) && end.before(a.getEnd())) {
 //                   System.out.println(a.getCustomer_id() + "Start: " + start + " after a.getStart: " + a.getStart() + " AND end: " + end + " before a.getEnd: " + a.getEnd());
  //                  System.out.println("Return false - appointment inside another appointment");
                    errorWindow(3);
                    return false;
                } else if (start.before(a.getStart()) && end.after(a.getStart()) && end.before(a.getEnd())) {
  //                  System.out.println(a.getCustomer_id() + "Start: " + start + " before a.getStart: " + a.getStart() + " AND end: " + end + " after a.getStart: " + a.getStart() + " AND end: " + end + " before a.getEnd: " + a.getEnd());
  //                  System.out.println("Return false - to the left");
                    errorWindow(3);
                    return false;
                } else if (start.after(a.getStart()) && start.before(a.getEnd()) && end.after(a.getEnd())) {
  //                  System.out.println(a.getCustomer_id() + "Start: " + start + " after a.getStart: " + a.getStart() + " AND start: " + start + " before a.getEnd: " + a.getEnd() + " AND " + end + " after a.getEnd() " + a.getEnd());
  //                  System.out.println("Return false - to the right");
                    errorWindow(3);
                    return false;
                } else if (a.getStart().after(a.getEnd())) {
   //                 System.out.println("Starts after the end - return false");
                    errorWindow(4);
                    return false;
                } else if (start.equals(a.getStart()) || end.equals(a.getEnd())) {
  //                  System.out.println("Starts or ends equal - return false");
                    errorWindow(3);
                    return false;
                } else if(start.before(a.getStart()) && end.before(a.getStart())){
//                    System.out.println("Customer ID: " + a.getCustomer_id() + " Start: " + start + " before a.getStart: " + a.getStart() + " AND end: " + end + " before a.getStart(): " + a.getStart());
 //                   System.out.println("Good - before current appt");
                } else if ((start.after(a.getEnd()) && end.after(a.getEnd())) ) {
//                    System.out.println("Customer ID: " + a.getCustomer_id() + " Start: " + start + " after a.getStart: " + a.getEnd() + " AND end: " + end + " after a.getStart(): " + a.getEnd());
//                    System.out.println("Good - after current appt");
                } else if (start.equals(a.getStart()) || end.equals(a.getEnd())) {

                } else {
//                    System.out.println("Some other case");
                }
            }
        }
                return true;
    }

    /**
     * tests the customer ID to make sure it is a known customer
     * @param test integer for id
     * @return
     */
    private boolean testCust_ID(int test){
         ObservableList<Customers> c = custInv.getAllCustomers();
         for (Customers a : c){
             if(a.getCustomer_id() == test){
                 System.out.println("Returning true");
                 return true;
             }
         }
        errorWindow(5);
        return false;
    }

    /**
     *  tests a time to make sure the integers are hours and minutes
     *
     * @param time LocalTime object
     * @return
     */

    private boolean testTimes(LocalTime time){
        if(time.getHour() >= 0 && time.getHour() < 24){

        } else {
            errorWindow(6);
            return false;
        }
        if(time.getMinute() >= 0 && time.getMinute() < 60){

        } else {
            errorWindow(7);
            return false;
        }
        return true;
    }

    /**
     * checks if user inputs a single digit
     * @param temp String
     * @return
     */

    private String checkTwoDigits(String temp){
        if(temp.length() == 1){
            return ("0" + temp);
        } else {
            return temp;
        }
    }

        /**
         * saves an appointment that has been input
         * @param event
         */

    @FXML
    private void saveAppointment(Event event){
        Appointments appt;
        try {
            int app_id = Integer.parseInt(appointment_id.getText().trim());
            String tit = title.getText().trim();
            String descrip = description.getText().trim();
            String loc = location.getText().trim();
            String typ = type.getText().trim();
            String created_by = "script";
            String last_updated_by = "script";
            int cust_id = Integer.parseInt(customer_id.getText().trim());
            boolean testCust = testCust_ID(cust_id);
            if(testCust == false){return;}
            int usr_id = Integer.parseInt(user_id.getText().trim());
            String con_temp = (String) choice_con.getValue();
            LocalDate sDate = this.startDate.getValue();
            LocalDate eDate = this.endDate.getValue();
            String tempsHour = (String) startHour.getText();
            String tempeHour = (String) endHour.getText();
            String tempsMin = (String) startMinutes.getText();
            String tempeMin = (String) endMinutes.getText();
            tempsHour = checkTwoDigits(tempsHour);
            tempeHour = checkTwoDigits(tempeHour);
            tempsMin = checkTwoDigits(tempsMin);
            tempeMin = checkTwoDigits(tempeMin);
            String sTime = (String) tempsHour + ":" + tempsMin;
            String eTime = (String) tempeHour + ":" + tempeMin;
            LocalTime sTime_formatted = LocalTime.parse(sTime);
            LocalTime eTime_formatted = LocalTime.parse(eTime);
            LocalDateTime s = LocalDateTime.of(sDate,sTime_formatted);
            LocalDateTime e = LocalDateTime.of(eDate,eTime_formatted);
            Timestamp stimestamp = Timestamp.valueOf(s);
            Timestamp etimestamp = Timestamp.valueOf(e);
            ZoneId zone = ZoneId.systemDefault();
            ZonedDateTime CSTstart = stimestamp.toLocalDateTime().atZone(zone);
// print
            ZonedDateTime UTCstart = CSTstart.withZoneSameInstant(ZoneId.of("UTC"));
// print
            ZonedDateTime CSTend = etimestamp.toLocalDateTime().atZone(zone);
            ZonedDateTime UTCend = CSTend.withZoneSameInstant(ZoneId.of("UTC"));

            LocalDateTime CSTstartl = CSTstart.toLocalDateTime();
            LocalDateTime CSTendl = CSTend.toLocalDateTime();

            LocalDateTime UTCLocalStart = UTCstart.toLocalDateTime();
            LocalDateTime UTCLocalEnd = UTCend.toLocalDateTime();

            Timestamp fnStart = Timestamp.valueOf(CSTstartl);
            Timestamp fnEnd = Timestamp.valueOf(CSTendl);

            Timestamp CSTstampstart = Timestamp.valueOf(CSTstartl);
            Timestamp CSTstampend = Timestamp.valueOf(CSTendl);
// Tests
            // LocalDateTime to LocalDate
            LocalDate CSTdatestart = CSTstartl.toLocalDate();
            LocalDate CSTdateend = CSTendl.toLocalDate();
            // LocalDateTime to LocalTime
            LocalTime CSTstarttime = CSTstartl.toLocalTime();
            LocalTime CSTendtime = CSTendl.toLocalTime();

            System.out.println(CSTstartl);
            System.out.println(CSTendl);
            System.out.println(CSTstarttime);
            System.out.println(CSTendtime);

            boolean tst1, tst2, tst3, tst4;
            tst1 = testOfficeHours(CSTstarttime);
            tst2 = testOfficeHours(CSTendtime);
            tst3 = testTimes(CSTstarttime);
            tst4 = testTimes(CSTendtime);
            if(tst1 == false || tst2 == false || tst3 == false || tst4 == false){
                return;
            }
            boolean tm1 = searchCustApt(app_id,cust_id,CSTstampstart,CSTstampend);
            if(tm1 == false){return;}
            int con_id = 0;
            for(Contacts c : contactCollection){
                if(c.getContact_name() == con_temp)
                    con_id = c.getContact_id();
            }
            boolean test_con = testContactNull(con_id);
            if(test_con == false){return;}
            DBConnection db = new DBConnection();
            Connection conn = DBConnection.makeConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO appointments (Appointment_ID,Title,Description,Location,Type,Start,End,Create_Date,Created_By,Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, app_id);
            stmt.setString(2, tit);
            stmt.setString(3, descrip);
            stmt.setString(4, loc);
            stmt.setString(5, typ);
            stmt.setTimestamp(6, fnStart);
            stmt.setTimestamp(7, fnEnd);
            stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            stmt.setString(9, created_by);
            stmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
            stmt.setString(11, last_updated_by);
            stmt.setInt(12, cust_id);
            stmt.setInt(13, usr_id);
            stmt.setInt(14,con_id);
            stmt.executeUpdate();
            conn.close();
        } catch(DateTimeParseException ex){
            ex.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ViewAppointment(event);
    }


    /**
     * populates the customer data structure
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
                custInv.addCustomer(custObject);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

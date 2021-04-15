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
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Date;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * UI for Editing Appointments
 */

public class EditAppointment implements Initializable {

    @FXML
    public TextField appointment_id;
    @FXML
    public TextField title;
    @FXML
    public TextField description;
    @FXML
    public TextField location;
    @FXML
    public TextField type;
    @FXML
    public TextField customer_id = new TextField();
    @FXML
    public TextField user_id = new TextField();
    @FXML
    public ObservableList<Integer> hours = FXCollections.observableArrayList();
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

    public Appointments apt;

    AppointmentsInventory ai = new AppointmentsInventory();

    ContactsInventory ci = new ContactsInventory();

    @FXML
    public ObservableList<Integer> minutes = FXCollections.observableArrayList();

    @FXML
    public ObservableList<Contacts> contactCollection = FXCollections.observableArrayList();

    @FXML
    public ObservableList<String> contactName = FXCollections.observableArrayList();

    @FXML
    public ObservableList<Appointments> appointments = FXCollections.observableArrayList();

    public CustomerInventory custInv = new CustomerInventory();

    /**
     * Constructor for editing appointments
     * @param apt appointment to edit
     * @param ai passes in the appointment model
     * @param ci passes in the contacts model
     */
    public EditAppointment(Appointments apt,AppointmentsInventory ai, ContactsInventory ci,CustomerInventory custInv){
        this.apt = apt;
        this.ai = ai;
        this.ci = ci;
        this.custInv = custInv;
    }

    /**
     * runs the following methods setContacts(), resetFields(), setTimes()
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setContacts();
        resetFields();
        convertTimeZone();
    }

    public void convertTimeZone(){
        ZoneId zone = ZoneId.systemDefault();
        Timestamp start = apt.getStart();
        Timestamp end = apt.getEnd();
/*        ZonedDateTime newzdtStart = start.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(zone);
        ZonedDateTime newzdtEnd = end.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(zone);
        LocalDateTime startDT = newLocalStart.toLocalDateTime();
        LocalDateTime endDT = newLocalEnd.toLocalDateTime();
        Timestamp fnStart = Timestamp.valueOf(startDT);
        Timestamp fnEnd = Timestamp.valueOf(endDT);
*/
        try {
            Date dateStart = new Date(start.getTime());
            Date dateEnd = new Date(end.getTime());
            LocalDate localDateStart = Instant.ofEpochMilli(dateStart.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate localDateEnd = Instant.ofEpochMilli(dateEnd.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            startDate.setValue(localDateStart);
            endDate.setValue(localDateEnd);
            startHour.setText(start.toString().substring(11,13));
            endHour.setText(end.toString().substring(11,13));
            startMinutes.setText(start.toString().substring(14,16));
            endMinutes.setText(end.toString().substring(14,16));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * runs when the exit button is clicked
     * @param event
     */
    @FXML
    private void exitEditAppointment(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        ViewAppointment(event);
    }

    /**
     * Sets the choicebox to contacts
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
     * sets the fields to canned text
     */
    private void resetFields() {
        try {
            appointment_id.setText(String.valueOf(apt.getAppointment_id()));
            title.setText(apt.getTitle());
            description.setText(apt.description);
            location.setText(apt.getLocation());
            type.setText(apt.getType());
            customer_id.setText(String.valueOf(apt.getCustomer_id()));
            user_id.setText("1");
            System.out.println(apt.getContact_id());
            choice_con.getSelectionModel().select(apt.getContact_id() - 1);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * sends the user back to the View Appointment UI
     * @param event
     */
    private void ViewAppointment(Event event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAppointment.fxml"));
            ViewAppointment controller = new ViewAppointment();
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
     * Error popup per the error code
     * @param code
     * @return
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
     * tests if the day is a weekend
     * @param currentDate
     * @return
     */
    private boolean testDateWeekend(LocalDate currentDate){
        boolean local = true;
        if(currentDate.getDayOfWeek() == DayOfWeek.SUNDAY || currentDate.getDayOfWeek() == DayOfWeek.SATURDAY){
            local = errorWindow(1);
        }
        return local;
    }

    /**
     * tests if the appointment is between 8am and 10pm
     * @param current_time
     * @return
     */
    private boolean testOfficeHours(LocalTime current_time){
        boolean local = true;
        LocalTime time1 = LocalTime.of(8, 00, 00);
        LocalTime time2 = LocalTime.of(22, 00, 00);
        boolean morn = current_time.isBefore(time1);
        boolean aft = current_time.isAfter(time2);
        if( morn == true || aft == true ){
            local = errorWindow(2);
        }
        return local;
    }

    /**
     * checks the appointment times to see if the edited appointment overlaps with others
     * @param cust_id checks all the appointments with this customer
     * @param start new appointment to be checked
     * @param end new appointment to be checked
     * @return TRUE/FALSE
     */
    private boolean searchCustApt(int app_id,int cust_id, Timestamp start, Timestamp end){
        for(Appointments a : appointments){
            if(a.getCustomer_id() == cust_id  && a.getAppointment_id() != app_id){
                if (start.before(a.getStart()) && end.after(a.getEnd())) {
                    System.out.println(a.getCustomer_id() + "Start: " + start + " before a.getStart: " + a.getStart() + " AND end: " + end + " after a.getEnd: " + a.getEnd());
                    System.out.println("Return false - appointment outside another appointment");
                    errorWindow(3);
                    return false;
                } else if (start.after(a.getStart()) && end.before(a.getEnd())) {
                    System.out.println(a.getCustomer_id() + "Start: " + start + " after a.getStart: " + a.getStart() + " AND end: " + end + " before a.getEnd: " + a.getEnd());
                    System.out.println("Return false - appointment inside another appointment");
                    errorWindow(3);
                    return false;
                } else if (start.before(a.getStart()) && end.after(a.getStart()) && end.before(a.getEnd())) {
                    System.out.println(a.getCustomer_id() + "Start: " + start + " before a.getStart: " + a.getStart() + " AND end: " + end + " after a.getStart: " + a.getStart() + " AND end: " + end + " before a.getEnd: " + a.getEnd());
                    System.out.println("Return false - to the left");
                    errorWindow(3);
                    return false;
                } else if (start.after(a.getStart()) && start.before(a.getEnd()) && end.after(a.getEnd())) {
                    System.out.println(a.getCustomer_id() + "Start: " + start + " after a.getStart: " + a.getStart() + " AND start: " + start + " before a.getEnd: " + a.getEnd() + " AND " + end + " after a.getEnd() " + a.getEnd());
                    System.out.println("Return false - to the right");
                    errorWindow(3);
                    return false;
                } else if (a.getStart().after(a.getEnd())) {
                    System.out.println("Starts after the end - return false");
                    errorWindow(4);
                    return false;
                } else if (start.equals(a.getStart()) || end.equals(a.getEnd())) {
                    System.out.println("Starts or ends equal - return false");
                    errorWindow(3);
                    return false;
                } else if(start.before(a.getStart()) && end.before(a.getStart())){
                    System.out.println("Customer ID: " + a.getCustomer_id() + " Start: " + start + " before a.getStart: " + a.getStart() + " AND end: " + end + " before a.getStart(): " + a.getStart());
                    System.out.println("Good - before current appt");
                } else if ((start.after(a.getEnd()) && end.after(a.getEnd())) ) {
                    System.out.println("Customer ID: " + a.getCustomer_id() + " Start: " + start + " after a.getStart: " + a.getEnd() + " AND end: " + end + " after a.getStart(): " + a.getEnd());
                    System.out.println("Good - after current appt");
                } else if (start.equals(a.getStart()) || end.equals(a.getEnd())) {

                } else {
                    System.out.println("Some other case");
                }
            }
        }
        return true;
    }

    private boolean testCust_ID(int test){
        ObservableList<Customers> c = custInv.getAllCustomers();
        for (Customers a : c){
            if(a.getCustomer_id() == test){
                return true;
            }
        }
        errorWindow(5);
        return false;
    }

    /**
     * checks if the contact is null
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
     * saves the appointment that has been edited
     * @param event
     */
    @FXML
    private void saveAppointment(Event event){
        Appointments temp;
        try {
            int app_id = Integer.parseInt(appointment_id.getText().trim());
            String tit = title.getText().trim();
            String descrip = description.getText().trim();
            String loc = location.getText().trim();
            String typ = type.getText().trim();
            int custid = Integer.parseInt(customer_id.getText().trim());
            String created_by = "script";
            String last_updated_by = "script";
            int cust_id = Integer.parseInt(customer_id.getText().trim());
            int usr_id = Integer.parseInt(user_id.getText().trim());
            String con_temp = (String) choice_con.getValue();
            LocalDate sDate = startDate.getValue();
            LocalDate eDate = endDate.getValue();
            boolean wk1,wk2;
            wk1 = testDateWeekend(sDate);
            wk2 = testDateWeekend(eDate);
            if(wk1 == false || wk2 == false ){
                return;
            }
            String sTime = (String) startHour.getText() + ":" + startMinutes.getText();
            String eTime = (String) endHour.getText() + ":" + endMinutes.getText();
            System.out.println(sTime);
            System.out.println(eTime);
            LocalTime sTime_formatted = LocalTime.parse(sTime);
            LocalTime eTime_formatted = LocalTime.parse(eTime);
            System.out.println(sTime_formatted);
            System.out.println(eTime_formatted);
            boolean tst1, tst2, tst3, tst4;
            tst3 = testTimes(sTime_formatted);
            tst4 = testTimes(eTime_formatted);
            tst1 = testOfficeHours(sTime_formatted);
            tst2 = testOfficeHours(eTime_formatted);
            if(tst1 == false || tst2 == false || tst3 == false || tst4 == false){
                return;
            }
            LocalDateTime s = LocalDateTime.of(sDate,sTime_formatted);
            LocalDateTime e = LocalDateTime.of(eDate,eTime_formatted);
            Timestamp stimestamp = Timestamp.valueOf(s);
            Timestamp etimestamp = Timestamp.valueOf(e);
            boolean tm1 = searchCustApt(app_id,cust_id,stimestamp,etimestamp);
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
            PreparedStatement stmt = conn.prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?,Last_Update = ?,Last_Updated_By = ?,Customer_ID = ?,User_ID = ?,Contact_ID = ? WHERE Appointment_ID = ?");
            stmt.setString(1, tit);
            stmt.setString(2, descrip);
            stmt.setString(3, loc);
            stmt.setString(4, typ);
            stmt.setTimestamp(5, stimestamp);
            stmt.setTimestamp(6,etimestamp);
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            stmt.setString(8, created_by);
            stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            stmt.setString(10, last_updated_by);
            stmt.setInt(11, cust_id);
            stmt.setInt(12, usr_id);
            stmt.setInt(13,con_id);
            stmt.setInt(14, app_id);
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
}


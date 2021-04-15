package sample;
import java.sql.Timestamp;

/**
 * Model for appointments
 */
public class Appointments {

    public int appointment_id;
    public String title;
    public String description;
    public String location;
    public String type;
    public Timestamp start;
    public Timestamp end;
    public int customer_id;
    public int user_id;
    public int contact_id;

    /**
     * Custructor for appointments
     */
    public Appointments(int appointment_id, String title, String description, String location, String type, Timestamp start, Timestamp end, int customer_id, int user_id, int contact_id) {
        this.appointment_id = appointment_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
    }

    /**
     * Empty constructor
     */
    public Appointments(){

    }

    /**
     * get the appointment id
     * @return
     */
    public int getAppointment_id() {
        return appointment_id;
    }

    /**
     * set the appointment id
     * @param appointment_id
     */
    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    /**
     * get the title
     * @return
     */
    public String getTitle(){
        return title;
    }

    /**
     * set the title
     * @param title
     */
    public void  setTitle(String title){
        this.title = title;
    }

    /**
     * get description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get location
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * set location
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * get type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * set type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get start
     * @return
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * set start
     * @param start
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * get end
     * @return
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * set end
     * @param end
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * get customer id
     * @return
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * set customer id
     * @param customer_id
     */
    public void setCustomer_id(int customer_id){
        this.customer_id = customer_id;
    }

    /**
     * get user id
     * @return
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * set user id
     * @param customer_id
     */
    public void setUser_id(int customer_id){
        this.user_id = user_id;
    }

    /**
     * get contact id
     * @return
     */
    public int getContact_id() {return contact_id;}

    /**
     * set contact id
     * @param contact_id
     */
    public void setContact_id(int contact_id){
        this.contact_id = contact_id;
    }

}


package sample;

import java.sql.Timestamp;

/**
 * User report Inventory model
 */

public class UserReportInventory {
    private int appointment_id;
    private String title;
    private String type;
    private String description;
    private Timestamp start;
    private Timestamp end;
    private int customer_id;

    /**
     * constructor
     * @param appointment_id integer
     * @param title string
     * @param type string
     * @param description string
     * @param start timestamp
     * @param end timestamp
     * @param customer_id integer
     */

    public UserReportInventory(int appointment_id,String title, String type,String description,Timestamp start,Timestamp end,int customer_id){
        this.appointment_id = appointment_id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
    }

    /**
     * gets the appointment
     * @return integer
     */
    public int getAppointment_id(){
        return appointment_id;
    }

    /**
     * sets the appointment
     * @param appointment_id integer
     */
    public void setAppointment_id(int appointment_id){
        this.appointment_id = appointment_id;
    }

    /**
     * gets the title
     * @return string
     */
    public String getTitle(){
        return title;
    }

    /**
     * sets the title
     * @param title string
     */

    public void setTitle(String title){
        this.title = title;
    }

    /**
     * gets the type
     * @return string
     */
    public String getType(){
        return type;
    }

    /**
     * setsthe type
     * @param type string
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * gets the description
     * @return string
     */
    public String getDescription(){
        return description;
    }

    /**
     * sets the description
     * @param description string
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * gets the start
     * @return timestamp
     */
    public Timestamp getStart(){
        return start;
    }

    /**
     * sets the start
     * @param start timestamp
     */
    public void setStart(Timestamp start){
        this.start = start;
    }

    /**
     * gets the end
     * @return timestamp
     */
    public Timestamp getEnd(){
        return end;
    }

    /**
     * sets the end
     * @param end timestamp
     */
    public void setEnd(Timestamp end){
        this.end = end;
    }

    /**
     * gets customer id
     * @return integer
     */
    public int getCustomer_id(){
        return customer_id;
    }

    /**
     * sets the customer id
     * @param customer_id
     */
    public void setCustomer_id(int customer_id){
        this.customer_id = customer_id;
    }
}

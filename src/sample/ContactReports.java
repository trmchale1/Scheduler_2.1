package sample;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Model for Contact Reports, populates a customer report
 */
public class ContactReports {

    private int appointment_id;
    private String title;
    private String type;
    private String description;
    private Timestamp start;
    private Timestamp end;
    private int customer_id;

    /**
     * Constructor for Contact Reports
     */
    public ContactReports(int appointment_id,String title, String type,String description,Timestamp start,Timestamp end,int customer_id){
        this.appointment_id = appointment_id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
    }

    /**
     * gets the appointment id
     * @return integer
     */
    public int getAppointment_id(){
        return appointment_id;
    }

    /**
     * sets the appointment id
     * @param appointment_id integer
     */
    public void setAppointment_id(int appointment_id){
        this.appointment_id = appointment_id;
    }

    /**
     * returns the title
     * @return string
     */
    public String getTitle(){
        return title;
    }

    /**
     * sets the title
     * @param title String
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * gets the type
     * @return String
     */
    public String getType(){
        return type;
    }

    /**
     * sets the type
     * @param type String
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * gets Description
     * @return String
     */
    public String getDescription(){
        return description;
    }

    /**
     * sets Description
     * @param description String
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * gets the Start
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
     * gets the customer id
     * @return returns integer
     */
    public int getCustomer_id(){
        return customer_id;
    }

    /**
     * sets customer id
     * @param customer_id integer
     */
    public void setCustomer_id(int customer_id){
        this.customer_id = customer_id;
    }
}

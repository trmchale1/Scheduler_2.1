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

    public int getAppointment_id(){
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id){
        this.appointment_id = appointment_id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Timestamp getStart(){
        return start;
    }

    public void setStart(Timestamp start){
        this.start = start;
    }

    public Timestamp getEnd(){
        return end;
    }

    public void setEnd(Timestamp end){
        this.end = end;
    }

    public int getCustomer_id(){
        return customer_id;
    }

    public void setCustomer_id(int customer_id){
        this.customer_id = customer_id;
    }
}

package sample;

/**
 * Model for Contacts
 */
public class Contacts {

    private int contact_id;
    String contact_name;
    String email;

    public Contacts(int contact_id, String contact_name, String email){
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.email = email;
    }

    public int getContact_id(){
        return contact_id;
    }

    public void setContact_id(int contact_id){
        this.contact_id = contact_id;
    }

    public String getContact_name(){
        return contact_name;
    }

    public String setContact_name(){
        return contact_name;
    }

    public void setEmail(String contact_name){
        this.contact_name = contact_name;
    }

    public String getEmail(){
        return email;
    }
}
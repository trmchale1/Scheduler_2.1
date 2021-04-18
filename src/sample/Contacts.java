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

    /**
     * gets the contact id
     * @return returns integer
     */
    public int getContact_id(){
        return contact_id;
    }

    /**
     * sets the contact id
     * @param contact_id integer
     */
    public void setContact_id(int contact_id){
        this.contact_id = contact_id;
    }

    /**
     * gets the contact name
     * @return String
     */
    public String getContact_name(){
        return contact_name;
    }

    /**
     * sets the contact name
     * @return string
     */
    public String setContact_name(){
        return contact_name;
    }

    /**
     * sets the email
     * @param contact_name String
     */
    public void setEmail(String contact_name){
        this.contact_name = contact_name;
    }

    /**
     * gets the email
     * @return String
     */
    public String getEmail(){
        return email;
    }
}
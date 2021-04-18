package sample;

/**
 * Model for Customers
 */
public class Customers {

    private int customer_id;
    String customer_name;
    String address;
    String postal_code;
    String phone;
    String division;
    String country;

    /**
     * Customers constructor
     * @param customer_id integer
     * @param customer_name String
     * @param address String
     * @param postal_code String
     * @param phone String
     * @param division String
     * @param country String
     */
    public Customers(int customer_id, String customer_name, String address, String postal_code, String phone, String division, String country){
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    /**
     * gets the customer id
     * @return integer
     */
    public int getCustomer_id(){
        return customer_id;
    }

    /**
     * sets the customer id
     * @param customer_id integer
     */
    public void setCustomer_id(int customer_id){
        this.customer_id = customer_id;
    }

    /**
     * gets the customer name
     * @return string
     */
    public String getCustomer_name(){
        return customer_name;
    }

    /**
     * sets the customer name
     * @param fcustomer_name String
     */
    public void setCustomer_name(String fcustomer_name){
        this.customer_name = fcustomer_name;
    }

    /**
     * gets the address
     * @return string
     */
    public String getAddress(){
        return address;
    }

    /**
     * sets the address
     * @param faddress string
     */
    public void setAddress(String faddress){ this.address = faddress;
    }

    /**
     * gets the postal code
     * @return string
     */
    public String getPostal_code(){
        return postal_code;
    }

    /**
     * sets the postal code
     * @param fpostal_code string
     */
    public void setPostal_code(String fpostal_code){
        this.postal_code = fpostal_code;
    }

    /**
     * gets the phone
     * @return string
     */
    public String getPhone(){
        return phone;
    }

    /**
     * sets the phone
     * @param fphone string
     */
    public void setPhone(String fphone){  this.phone = fphone;
    }

    /**
     * gets the division
     * @return returns the division
     */
    public String getDivision(){return division;}

    /**
     * sets the division
     * @param fdivision string
     */
    public void setDivision(String fdivision){ this.country = fdivision;}

    /**
     * sets the country
     * @return returns the country
     */
    public String getCountry() {return country;}

    /**
     * sets the country
     * @param fcountry string
     */
    public void setCountry(String fcountry) { this.country = fcountry;
    }
}


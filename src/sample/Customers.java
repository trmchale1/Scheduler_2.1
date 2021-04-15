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

    public Customers(int customer_id, String customer_name, String address, String postal_code, String phone, String division, String country){
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    public int getCustomer_id(){
        return customer_id;
    }

    public void setCustomer_id(int customer_id){
        this.customer_id = customer_id;
    }

    public String getCustomer_name(){
        return customer_name;
    }

    public void setCustomer_name(String fcustomer_name){
        this.customer_name = fcustomer_name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String faddress){ this.address = faddress;
    }

    public String getPostal_code(){
        return postal_code;
    }

    public void setPostal_code(String fpostal_code){
        this.postal_code = fpostal_code;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String fphone){  this.phone = fphone;
    }

    public String getDivision(){return division;}

    public void setDivision(String fdivision){ this.country = fdivision;}

    public String getCountry() {return country;}

    public void setCountry(String fcountry) { this.country = fcountry;
    }
}


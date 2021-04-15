package sample;

/**
 * Model for Customer Type Reports, used in the UI for Customer Reports
 */
public class CustomerTypeReport {

    private String type;
    private int total;

    public CustomerTypeReport(String type, int total){
        this.type = type;
        this.total = total;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public int getTotal(){
        return total;
    }

    public void setTotal(int total){
        this.total = total;
    }
}

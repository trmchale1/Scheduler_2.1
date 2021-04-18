package sample;

/**
 * Model for Customer Type Reports, used in the UI for Customer Reports
 */
public class CustomerTypeReport {

    private String type;
    private int total;

    /**
     * constructor
     * @param type string
     * @param total integer
     */
    public CustomerTypeReport(String type, int total){
        this.type = type;
        this.total = total;
    }

    /**
     * gets type
     * @return string
     */
    public String getType(){
        return type;
    }

    /**
     * sets the type
     * @param type string
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * gets the total
     * @return integer
     */
    public int getTotal(){
        return total;
    }

    /**
     * gets the total
     * @param total integer
     */
    public void setTotal(int total){
        this.total = total;
    }
}

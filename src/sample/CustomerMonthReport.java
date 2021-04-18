package sample;
import java.util.*;
import java.time.*;

/**
 * Model for Customer Month Reports
 */
public class CustomerMonthReport {
    private Month month;
    private int total;

    /**
     * constructor for CustomerMonthReport
     * @param month
     * @param total
     */
    public CustomerMonthReport(Month month, int total){
        this.month = month;
        this.total = total;
    }

    /**
     * gets month
     * @return Month
     */
    public Month getMonth(){ return month; }

    /**
     * sets month
     * @param month month
     */
    public void setMonth(Month month){
        this.month = month;
    }

    /**
     * gets month
     * @return returns integer
     */
    public int getMonth_total(){
        return total;
    }

    /**
     * sets month
     * @param total integer
     */
    public void setMonth_total(int total){
        this.total = total;
    }
}

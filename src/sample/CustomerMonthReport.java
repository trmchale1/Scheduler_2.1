package sample;
import java.util.*;
import java.time.*;

/**
 * Model for Customer Month Reports
 */
public class CustomerMonthReport {
    private Month month;
    private int total;

    public CustomerMonthReport(Month month, int total){
        this.month = month;
        this.total = total;
    }

    public Month getMonth(){ return month; }

    public void setMonth(Month month){
        this.month = month;
    }

    public int getMonth_total(){
        return total;
    }

    public void setMonth_total(int total){
        this.total = total;
    }
}

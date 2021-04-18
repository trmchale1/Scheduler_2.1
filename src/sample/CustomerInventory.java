package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for Customers
 */
public class CustomerInventory {

    private ObservableList<Customers> customerInventory = FXCollections.observableArrayList();

    /**
     * adds a customer
     * @param newCustomer Customer object
     */
    public void addCustomer(Customers newCustomer){
        if(newCustomer != null) {
            customerInventory.add(newCustomer);
        }
    }

    /**
     * returns size
     * @return integer
     */
    public int size(){
        return customerInventory.size();
    }

    /**
     * looks up customers
     * @param customerID integer
     * @return Customer object
     */
    public Customers lookUpCustomers(int customerID) {
        if (!customerInventory.isEmpty()) {
            for (int i = 0; i < customerInventory.size(); i++) {
                if (customerInventory.get(i).getCustomer_id() == customerID) {
                    return customerInventory.get(i);
                }
            }

        }
        return null;
    }

    /**
     * updates customer
     * @param index integer
     * @param selectedCustomer Customer object
     */
    public void updateCustomer(int index,Customers selectedCustomer) {
        for (int i = 0; i < customerInventory.size(); i++) {
            if (customerInventory.get(i).getCustomer_id() == selectedCustomer.getCustomer_id()) {
                customerInventory.set(i, selectedCustomer);
                break;
            }
        }
        return;
    }

    /**
     * deletes customer
     * @param c Customer object
     * @return boolean
     */
    public boolean deleteCustomer(Customers c) {
        for (int i = 0; i < customerInventory.size(); i++) {
            if (customerInventory.get(i).getCustomer_id() == c.getCustomer_id()) {
                customerInventory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * gets all customers
     * @return observable list
     */
    public ObservableList<Customers> getAllCustomers() {
        return customerInventory;
    }

}
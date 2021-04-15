package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for Customers
 */
public class CustomerInventory {

    private ObservableList<Customers> customerInventory = FXCollections.observableArrayList();

    public void addCustomer(Customers newCustomer){
        if(newCustomer != null) {
            customerInventory.add(newCustomer);
        }
    }

    public int size(){
        return customerInventory.size();
    }

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

    public void updateCustomer(int index,Customers selectedCustomer) {
        for (int i = 0; i < customerInventory.size(); i++) {
            if (customerInventory.get(i).getCustomer_id() == selectedCustomer.getCustomer_id()) {
                customerInventory.set(i, selectedCustomer);
                break;
            }
        }
        return;
    }

    public boolean deleteCustomer(Customers c) {
        for (int i = 0; i < customerInventory.size(); i++) {
            if (customerInventory.get(i).getCustomer_id() == c.getCustomer_id()) {
                customerInventory.remove(i);
                return true;
            }
        }
        return false;
    }

    public ObservableList<Customers> getAllCustomers() {
        return customerInventory;
    }

}
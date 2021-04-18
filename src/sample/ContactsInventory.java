package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for Contacts
 */
public class ContactsInventory {

    private ObservableList<Contacts> contactsInventory = FXCollections.observableArrayList();

    /**
     * adds contacts
     * @param newContact Contact Object
     */
    public void addContact(Contacts newContact){
        if(newContact!= null) {
            contactsInventory.add(newContact);
        }
    }

    /**
     * size of the contacts Inventory
     * @return integer
     */
    public int size(){
        return contactsInventory.size();
    }

    /**
     * looks up contacts by integer
     * @param contactID integer
     * @return
     */
    public Contacts lookUpContacts(int contactID) {
        if (!contactsInventory.isEmpty()) {
            for (int i = 0; i < contactsInventory.size(); i++) {
                if (contactsInventory.get(i).getContact_id() == contactID) {
                    return contactsInventory.get(i);
                }
            }
        }
        return null;
    }

    /**
     * updates contact based on object
     * @param index integer
     * @param selectedContact Contact object
     */
    public void updateContact(int index,Contacts selectedContact) {
        for (int i = 0; i < contactsInventory.size(); i++) {
            if (contactsInventory.get(i).getContact_id() == selectedContact.getContact_id()) {
                contactsInventory.set(i, selectedContact);
                break;
            }
        }
        return;
    }

    /**
     * deletes contacts
     * @param c Contact objects
     * @return
     */
    public boolean deleteContacts(Contacts c) {
        for (int i = 0; i < contactsInventory.size(); i++) {
            if (contactsInventory.get(i).getContact_id() == c.getContact_id()) {
                contactsInventory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * gets all contacts in the inventory
     * @return returns an ObservableList of Contacts
     */
    public ObservableList<Contacts> getAllContacts() {
        return contactsInventory;
    }

}

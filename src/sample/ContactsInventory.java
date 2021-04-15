package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for Contacts
 */
public class ContactsInventory {

    private ObservableList<Contacts> contactsInventory = FXCollections.observableArrayList();

    public void addContact(Contacts newContact){
        if(newContact!= null) {
            contactsInventory.add(newContact);
        }
    }

    public int size(){
        return contactsInventory.size();
    }

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

    public void updateContact(int index,Contacts selectedContact) {
        for (int i = 0; i < contactsInventory.size(); i++) {
            if (contactsInventory.get(i).getContact_id() == selectedContact.getContact_id()) {
                contactsInventory.set(i, selectedContact);
                break;
            }
        }
        return;
    }

    public boolean deleteContacts(Contacts c) {
        for (int i = 0; i < contactsInventory.size(); i++) {
            if (contactsInventory.get(i).getContact_id() == c.getContact_id()) {
                contactsInventory.remove(i);
                return true;
            }
        }
        return false;
    }

    public ObservableList<Contacts> getAllContacts() {
        return contactsInventory;
    }

}

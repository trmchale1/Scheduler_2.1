package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for appointments
 */
public class AppointmentsInventory {

    private ObservableList<Appointments> appointmentsInventory = FXCollections.observableArrayList();

    /**
     * adds an appointment
     * @param appointments
     */
    public void addAppointment(Appointments appointments){
        if(appointments != null) {
            appointmentsInventory.add(appointments);
        }
    }

    /**
     * returns the size of the Inventory
     * @return
     */
    public int size(){
        return appointmentsInventory.size();
    }

    /**
     * looks up the appointment by the id
     * @param appointmentID input
     * @return
     */
    public Appointments lookUpAppointment(int appointmentID) {
        if (!appointmentsInventory.isEmpty()) {
            for (int i = 0; i < appointmentsInventory.size(); i++) {
                if (appointmentsInventory.get(i).getCustomer_id() == appointmentID) {
                    return appointmentsInventory.get(i);
                }
            }

        }
        return null;
    }

    /**
     * updates the appointment
     * @param index
     * @param selectedAppointment
     */
    public void updateAppointment(int index,Appointments selectedAppointment) {
        for (int i = 0; i < appointmentsInventory.size(); i++) {
            if (appointmentsInventory.get(i).getAppointment_id() == selectedAppointment.getAppointment_id()) {
                appointmentsInventory.set(i, selectedAppointment);
                break;
            }
        }
        return;
    }

    /**
     * delete appointment
     * @param c uses the object as a param
     * @return
     */
    public boolean deleteAppointment(Appointments c) {
        for (int i = 0; i < appointmentsInventory.size(); i++) {
            if (appointmentsInventory.get(i).getAppointment_id() == c.getAppointment_id()) {
                appointmentsInventory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * deletes appointment by customer index
     * @param cust_index
     * @return
     */
    public boolean deleteAppointment(int cust_index) {
        for (int i = 0; i < appointmentsInventory.size(); i++) {
            if (appointmentsInventory.get(i).getCustomer_id() == cust_index) {
                appointmentsInventory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * deletes the appointment by customer index
     * @param apt_index
     * @return
     */
    public boolean deleteAppointmentt(int apt_index) {
        for (int i = 0; i < appointmentsInventory.size(); i++) {
            if (appointmentsInventory.get(i).getCustomer_id() == apt_index) {
                appointmentsInventory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * returns appointments by observableList
     * @return
     */
    public ObservableList<Appointments> getAllAppointments() {
        return appointmentsInventory;
    }
}

package sample;

import java.sql.Timestamp;

/**
 * User object
 */

public class Users {

    private String user_name;
    private String password;

    /**
     * constructor
     * @param user_name string
     * @param password string
     */
    public Users(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

    /**
     * gets the username
     * @return string
     */
    public String getUser_name(){
        return user_name;
    }

    /**
     * sets the user name
     * @param user_name string
     */
    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    /**
     * gets the pw
     * @return string
     */
    public String getPassword(){
        return password;
    }

    /**
     * sets the p w
     * @param password string
     */
    public void setPassword(String password){
        this.password = password;
    }
}

package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Instant;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Utility to connect to database
 */
public class DBConnection {
    private static final String databaseName = "WJ07qPv";
    private static final String DB_URL = "jdbc:mysql://wgudb.ucertify.com:3306/" + databaseName;
    private static final String username = "U07qPv";
    private static final String password = "53689104949";
    public static Connection conn;

    /**
     * Connects to the db
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */

    public static Connection makeConnection() throws ClassNotFoundException, SQLException, Exception{
        try {
            conn = DriverManager.getConnection(DB_URL, username, password);
        } catch(SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
        System.out.println("Connection successful.");
        return conn;
    }

    /**
     * Closes connection to the database
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException{
        conn.close();
        System.out.println("Connection closed.");
    }
}

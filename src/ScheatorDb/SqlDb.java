/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorDb;

import java.sql.*;

/**
 *
 * @author mep
 */
public class SqlDb extends AbstractDb {

    SqlDb() {
        // Use default values for testing
        initDb("scheator", "root", "root");
    }
    
    /**
     * Creates connection to the database.
     *
     * @param db Database name.
     * @param user User name for the db connection.
     * @param pw Password for the db connection.
     */
    SqlDb(String db, String user, String pw) {
        initDb(db, user, pw);
    }

    private void initDb(String db, String user, String pw) {
        if (db.length() == 0) {
            db = "scheator";
        }
        String url = "jdbc:mysql://localhost/" + db;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, pw);
            if (!con.isClosed()) {

            }
            else {
                System.err.println("Failed to connecto to " + db);
            }
        }
        catch (Exception e) {
           System.err.println("Exception: "+ e.getMessage());
        }
        qe = new SqlQueryEngine();
    }
}

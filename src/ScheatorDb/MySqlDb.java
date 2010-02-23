package ScheatorDb;

import java.sql.*;

/** Database connector for a MySql database.
 *
 * @author mep
 */
public final class MySqlDb extends AbstractDb {

    MySqlDb() {
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
    MySqlDb(String db, String user, String pw) {
        initDb(db, user, pw);
    }

    /** Initialiases the MySql database.
     *
     * @param db Database name (address).
     * @param user Database user name.
     * @param pw Database password for the user.
     */
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

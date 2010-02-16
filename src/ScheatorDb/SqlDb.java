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

    private static final String GET_SERIES_LIST =
                "SELECT * from Series";

    SqlDb() {
        initDb("scheator", "root", "marko1");
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

    public String[] readSeriesList() {
        String[] retVal = {""};
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(GET_SERIES_LIST);
            int row = 0;
            while (rs.next()) {
//                retVal[row] = rs.toString();
                row++;
            }
        } catch (SQLException ex) {

        }
        return retVal;
    }

}

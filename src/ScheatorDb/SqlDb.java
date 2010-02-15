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

    }
    
    /**
     * Creates connection to the database.
     *
     * @param db Database name.
     * @param user User name for the db connection.
     * @param pw Password for the db connection.
     */
    SqlDb(String db, String user, String pw) {

        String url = "jdbc:mysql:///" + db;

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
    }

    @Override
    public String[] readSeriesList() {
        String[] retVal;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(GET_SERIES_LIST);
            int row = 0;
            while (rs.next()) {
                retVal[row] = rs.toString();
                row++;
            }
        } catch (SQLException ex) {

        }
        return retVal;
    }

}

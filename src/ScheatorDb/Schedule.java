/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorDb;
import java.util.LinkedHashMap;
import java.sql.*;

/**
 *
 * @author mep
 */
public class Schedule extends DbObject {

    /** Table name in the database */
    private static final String[] TABLE_NAME = {"Schedule"};
    /** Id field name for this table in the database */
    private static final String ID_FIELD = "ID";
    /** Field names for this table in the database */
    private static final String[] FIELDS = {"ID", "Name", "Season"};

    Schedule() {

    }

    Schedule(int key) {
        fetch(key);
    }

    public void fetch(int key) {
        this.currentId = key;
        list.clear();
        String[] idFields = null;
        String[] ids = null;
        if (key != 0) {
            idFields[0] = "season";
            ids[0] = String.valueOf(currentId);
        }

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, null, idFields , ids));
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                //Data schedule = new Data(Integer.parseInt(id.trim()), name);
                //list.put(id, schedule);
            }
        } catch (Exception e) {
            System.err.println("Failed to read series data: " + e.toString());
        }
    }

    public class Data {
        Data() {

        }
    }
}

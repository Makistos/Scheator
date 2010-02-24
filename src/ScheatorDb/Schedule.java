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
    private static final String[] TABLES = {"Season", "SeasonTeam", "Team", "Match"};
    /** Id field name for this table in the database */
    private static final String ID_FIELD = "ID";
    /** Field names for this table in the database */
    private static final String[] FIELDS = {"ID", "Name", "Season"};

    public Schedule() {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
    }

    public Schedule(int key) {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(key);
    }

    public void fetch(Integer key) {
        this.currentId = key;
        list.clear();
        String[] idFields = null;
        String[] ids = null;
        if (key != 0) {
            idFields = new String[1];
            ids = new String[1];
            idFields[0] = "season";
            ids[0] = String.valueOf(currentId);
        }

        try {
/*
            Statement st = db.con.createStatement();
            String q = db.qe.getItems(TABLES, null, idFields , ids, null);
            System.err.println("Query = " + q);
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                //Data schedule = new Data(Integer.parseInt(id.trim()), name);
                //list.put(id, schedule);
            }
 */
        } catch (Exception e) {
            System.err.println("Failed to read series data: " + e.toString());
        }
    }

    public class Data extends DbObject.Data {

        Data() {

        }
    }
}

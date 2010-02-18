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
public class Teams extends DbObject {

    private static final String[] TABLE_NAME = {"Team"};
    private static final String[] ID_FIELDS = {"ID"};
    private static final String[] FIELDS = {"ID", "Name"};
    
    Teams() {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
    }

    Teams(int seasonId) {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(seasonId);
    }

    public void fetch(int seasonId) {
        String[] ids = null;

        if (seasonId != 0) {
            ids = new String[1];
            ids[0] = String.valueOf(seasonId);
        }
        
        list.clear();

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, FIELDS, ID_FIELDS , ids, null));
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                Data team = new Data(Integer.parseInt(id.trim()), name);
                list.put(id, team);
            }
        } catch (Exception e) {
            System.err.println("Failed to read season data: " + e.toString());
        }

    }

    public class Data extends DbObject.Data {
        
        Data() {
            field_id = 0;
            field_name = "";
        }

        Data (String name) {
            field_id = 0;
            field_name = name;
        }
        
        Data(int id, String name) {
            field_id = id;
            field_name = name;
        }
    }

}

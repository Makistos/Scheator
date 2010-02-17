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
public class Season extends DbObject {

    /** Table name in the database */
    private static final String[] TABLE_NAME = {"Season"};
    /** Id field name for this table in the database */
    private static final String ID_FIELD = "ID";
    /** Field names for this table in the database */
    private static final String[] FIELDS = {"ID", "Name", "Season"};

    public Season() {
        list = new LinkedHashMap<Integer, Data>();
        db = new SqlDb();
        // No data loading by default
        //fetch();
    }

    public Season(int seriesId) {
        list = new LinkedHashMap<Integer, Data>();
        db = new SqlDb();
        fetch(seriesId);
    }
    
    public final void fetch(int key) {
        this.currentId = key;
        String[] idFields = null;
        String[] ids = null;
        if (currentId != 0) {
            idFields[0] =  "season";
            ids[0] = String.valueOf(currentId);
        } 
        
        list.clear();

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, null, idFields , ids));
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                String seasonId = rs.getString(FIELDS[2]);
                Data season = new Data(Integer.parseInt(id.trim()), name, Integer.parseInt(seasonId));
                list.put(id, season);
            }
        } catch (Exception e) {
            System.err.println("Failed to read season data: " + e.toString());
        }

    }

    public class Data extends DbObject.Data {

         public int field_id;
         public String field_name;
         public int field_season;

         Data(String name) {
             field_id = 0;
             field_name = name;
             field_season = 0;
         }
         
         Data(String name, int season) {
             field_id = 0;
             field_name = name;
             field_season = season;
         }

         Data(int id, String name, int season) {
             field_id = id;
             field_name = name;
             field_season = season;
         }

         @Override
         public String toString() {
             return field_name;
         }

 }
 
}

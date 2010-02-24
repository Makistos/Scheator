package ScheatorDb;
import java.util.LinkedHashMap;
import java.sql.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 *
 * @author mep
 */
public class Teams extends DbObject {

    private static final String[] TABLE_NAME = {"Team"};
    private static final String[] FIELDS = {"ID", "Name"};
    
    public Teams() {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
    }

    public Teams(int seasonId) {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(seasonId);
    }

    public void addNew(String name) {
        Data team = new Data(name);
        list.put(null, team);
    }

    /** Gets teams connected to given season (identified by seasonId).
     *
     * @param seasonId
     */
    public void fetch(Integer seasonId) {
        this.currentId = seasonId;
        HashMap<String, Object> idFields = null;

        if (seasonId != null) {
            idFields = new HashMap<String, Object>();
            idFields.put("id", seasonId);
        }
        
        list.clear();

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, FIELDS, idFields, null));
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                Data team = new Data(Integer.parseInt(id.trim()), name);
                list.put(id, team);
            }
        } catch (Exception e) {
            System.err.println("Failed to read team data: " + e.toString());
        }

    }

    /** Object representation of a team.
     * 
     */
    public class Data extends DbObject.Data {

        /** Id in the database. */
        Integer field_id;
        /** Season "name", e.g. "2007-08". */
        String field_name;
        
        Data() {
            field_id = null;
            field_name = "";
        }

        Data (String name) {
            field_id = null;
            field_name = name;
        }
        
        Data(Integer id, String name) {
            field_id = id;
            field_name = name;
        }
        
        @Override
        public void set(String field, Object value) {
            String name = "field_".concat(field);
            try {
                Class c = Class.forName(this.getClass().getName());
                Field f = c.getDeclaredField(name);
                f.set(this, value);
            } catch (Exception e) {
                System.err.println("Field does not exist: " + name + "(" + e.getMessage() + ")");
            }
            if (state == FieldState.SAVED) {
                state = FieldState.CHANGED;
            }
        }

        @Override
        public Object get(String field) {
            String name = "field_".concat(field);
            Object retval = null;
            try {
                Class c = Class.forName(this.getClass().getName());
                Field f = c.getDeclaredField(name);
                retval = (Object)f.get(this);
            } catch (Exception e) {
                 System.err.println("Field does not exist: " + name + " (" + e.getMessage() + ")");
            }
            return retval;
        }

        @Override
        public String toString() {
            return field_name;
        }
    }

}

package ScheatorDb;

import java.util.LinkedHashMap;
import java.sql.*;
import java.lang.reflect.Field;
import java.util.*;

/** The database reflection of the Series database table.
 *
 * @author mep
 */
public class Series extends DbObject {

    /** Table name in the database */
    private static final String[] TABLE_NAME = {"Series"};
    /** Field names for this table in the database */
    private static final String[] FIELDS = {"ID", "Name"};

    /** Constructor for this class.
     *
     * @brief Creates the database connection and fetches the data from
     * the database.
     *
     * @todo Only Sql databases supported at the moment.
     * 
     */
    public Series() {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(0);
    }

    
    /** Gets all the series or one specific series from the database.
     *
     * @param key Series database id.
     */
    public final void fetch(Integer key) {
        this.currentId = key;
        list.clear();
        HashMap<String, Object> idFields = null;
        if (key != 0) {
            idFields = new HashMap<String, Object>();
            idFields.put("season", key);
        }
        
        try {

            Statement st = db.con.createStatement();
            String q = db.qe.getItems(TABLE_NAME, null, idFields, null);
            System.err.println("Query: " + q);
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                Data series = new Data(Integer.parseInt(id.trim()), name);
                list.put(id, series);
            }
        } catch (Exception e) {
            System.err.println("Failed to read series data: " + e.toString());
        }
    }

    /** A container for a series entity.
     * 
     */
    public class Data extends DbObject.Data {

        /** Id in the database. */
        Integer field_id;
        /** Season "name", e.g. "2007-08". */
        String field_name;

        /** Creates a new series entity without a database id.
         *
         * @param name Name of the series.
         */
        Data(String name) {
            this.field_id = null;
            this.field_name = name;
        }

        /** Creates a series entity with database id and name.
         *
         * @param id   Database id for this entity.
         * @param name Name of the series.
         */
        Data(Integer id, String name) {
            this.field_id = id;
            this.field_name = name;
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

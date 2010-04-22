package ScheatorDb;

import java.sql.*;
import java.lang.reflect.Field;
import java.util.*;

/** 
 *
 * @author mep
 */
public class Season extends DbObject {

    /** Table name in the database */
    private static final String[] TABLE_NAME = {"Season"};
    /** Field names for this table in the database */
    private static final String[] FIELDS = {"ID", "Name", "Series"};

    public Season() {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        // No data loading by default
        //fetch();
    }

    public Season(int seriesId) {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(seriesId);
    }

    /** Fetches the data from the database for a season.
     *
     * @brief A season is a mapping between a series and the matches. One series
     * can have several seasons and a season consists of several matches. 
     *
     * @param series Series database id.
     */
    public final void fetch(Integer series) {
        this.currentId = series;
        HashMap<String, Object> idFields = null;
        if (currentId != 0) {
            idFields = new HashMap<String, Object>();
            idFields.put("series", series);
        } 
        
        list.clear();

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, null, idFields, null));
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                String seriesId = rs.getString(FIELDS[2]);
                Data season = new Data(Integer.parseInt(id.trim()),
                        name, Integer.parseInt(seriesId.trim()));
                list.put(id, season);
            }
        } catch (Exception e) {
            System.err.println("Failed to read season data: " + e.toString());
        }
    }

    /** Data for the season object.
     *
     */
    public class Data extends DbObject.Data {

        /** Id in the database. */
        Integer field_id;
        /** Season "name", e.g. "2007-08". */
        String field_name;
        /** Database id of series this season belongs to. */
        Integer field_series;

        Data(String name) {
            field_id = null;
            field_name = name;
            field_series = 0;
        }
         
        Data(String name, int series) {
            field_id = null;
            field_name = name;
            field_series = series;
        }

        Data(Integer id, String name, int series) {
            field_id = id;
            field_name = name;
            field_series = series;
        }

        /** Returns season name by default. */
        @Override
        public String toString() {
            return field_name;
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
    }
 
}

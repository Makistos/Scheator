package ScheatorDb;

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
        fetch(null);
    }

    public void addNew(String name) {
        Data series = new Data(name);
        series.state = FieldState.NEW;
        list.put(null, series);
    }


    /** Gets all the series or one specific series from the database.
     *
     * @param key Series database id.
     */
    public final void fetch(Integer key) {
        this.currentId = key;
        list.clear();
        HashMap<String, Object> idFields = null;
        if (key != null) {
            idFields = new HashMap<String, Object>();
            idFields.put("season", key);
        }
        
        try {

            Statement st = db.con.createStatement();
            String q = db.qe.getItems(TABLE_NAME, null, idFields, null);
            ResultSet rs = st.executeQuery(q);
            while (rs.next()) {
                Integer id = rs.getInt(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                Data series = new Data(id, name);
                list.put(id, series);
            }
        } catch (Exception e) {
            System.err.println("Failed to read series data: " + e.toString());
        }
    }

    @Override
    public void save() {
        System.err.println("Saving teams");
        try {
            Statement st = db.con.createStatement();

            /* Update and insert rows */
            System.err.println("No of items: " + list.size());
            for(Iterator itr = list.values().iterator(); itr.hasNext();) {
                Data row = (Data) itr.next();
                String q;
                HashMap<String, Object> fields = new HashMap<String, Object>();
                System.err.println("Series name: " + row.field_name);
                System.err.println("State: " + row.state);
                fields.put("name", row.field_name);

                switch (row.state) {
                    case SAVED:
                        /* Nothing to do */
                        break;
                    case CHANGED:
                        /* Existing row changed */
                        HashMap<String, Object> idFields = new HashMap<String, Object>();
                        idFields.put("id", row.field_id);
                        q = db.qe.updateItem("Series", fields, idFields);
                        st.executeUpdate(q);
                        break;
                    case NEW:
                        /* New team */
                        q = db.qe.addItem("Series", fields);
                        st.executeUpdate(q);
                        break;
                }
            }

            System.err.println("No of deleted items: " + deletedList.size());
            /* Delete rows */
            for(Iterator itr = deletedList.values().iterator(); itr.hasNext();) {
                Data row = (Data) itr.next();
                String q;
                HashMap<String, Object> idFields = new HashMap<String, Object>();

                System.err.println("save() id: " + row.field_id);
                idFields.put("id", row.field_id);
                q = db.qe.deleteItems("Team", idFields);
                st.executeUpdate(q);
            }
        } catch (Exception e) {

        }

        // Reload information */
        fetch(currentId);
    }

    public void delete(Integer key) {
        Data obj = (Data)list.remove(key);
        if (obj == null) {
            System.err.println("Null object found for key " + key);
        }
        deletedList.put(key, obj);
        System.err.println("Number of deleted items: " + deletedList.size());
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

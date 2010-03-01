package ScheatorDb;
import java.sql.*;
import java.lang.reflect.Field;
import java.util.*;

/** Represents the Teams table data from the database.
 *
 * @author mep
 */
public class Teams extends DbObject {

    private static final String[] TABLE_NAME = {"Team"};
    private static final String[] FIELDS = {"ID", "Name"};
    
    public Teams() {
        list = new LinkedHashMap<Integer, Data>();
        deletedList = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
    }

    public Teams(int seasonId) {
        list = new LinkedHashMap<Integer, Data>();
        deletedList = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(seasonId);
    }

    public void addNew(String name) {
        Data team = new Data(name);
        list.put(null, team);
    }

    @Override
    public void save() {
        try {
            Statement st = db.con.createStatement();

            /* Update and insert rows */
            System.err.println("No of items: " + list.size());
            for(Iterator itr = list.values().iterator(); itr.hasNext();) {
                Data row = (Data) itr.next();
                String q;
                HashMap<String, Object> fields = new HashMap<String, Object>();

                fields.put("name", row.field_name);

                switch (row.state) {
                    case SAVED:
                        /* Nothing to do */
                        break;
                    case CHANGED:
                        /* Existing row changed */
                        HashMap<String, Object> idFields = new HashMap<String, Object>();
                        idFields.put("id", row.field_id);
                        q = db.qe.updateItem("Team", fields, idFields);
                        st.executeUpdate(q);
                        break;
                    case NEW:
                        /* New team */
                        q = db.qe.addItem("Team", fields);
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

        String[] orderBy = {"name"};

        list.clear();

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, FIELDS, idFields, orderBy));
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

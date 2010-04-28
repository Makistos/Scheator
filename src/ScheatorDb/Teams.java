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
    private static final String[] FIELDS = {"Team.ID", "Team.Name"};
    
    public Teams() {
        list = new LinkedHashMap<Integer, Data>();
        deletedList = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
    }

    public Teams(int seriesId) {
        list = new LinkedHashMap<Integer, Data>();
        deletedList = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(seriesId);
    }

    /** Adds an existing team to the list
     *
     * @param team Team to add.
     */
    public void add(Data team) {
        team.state = FieldState.SAVED;
        list.put((Integer)team.field_id, team);
    }

    /** Adds a new team to the list
     *
     * @param name Name of team.
     */
    public void addNew(String name) {
        Data team = new Data(name);
        team.state = FieldState.NEW;
        list.put(null, team);
    }

    /** Adds the "bye" team to the list.
     * 
     * Bye team is needed if number of teams in a series is uneven. In that
     * case, each round has one team not playing, this is simulated with the
     * bye round.
     */
    public void addBye() {
        HashMap<String, Object> idFields = new HashMap<String, Object>();
        idFields.put("name", "Bye");
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, FIELDS, idFields, null));
            while (rs.next()) {
                Integer id = rs.getInt(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                Data team = new Data(id, name);
                list.put(id, team);
            }
        } catch (Exception e) {
            System.err.println("Bye team missign from database!");
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
                System.err.println("Team name: " + row.field_name);
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

    /** Gets teams connected to given season (identified by seriesId).
     *
     * @param seriesId
     */
    public void fetch(Integer seriesId) {
        String tables[] = {"","", ""};
        this.currentId = seriesId;
        HashMap<String, Object> idFields = null;

/*        if (seasonId != null) {
            tables[0] = TABLE_NAME[0];
            tables[1] = "`Season` as s";
            idFields = new HashMap<String, Object>();
            idFields.put("s.id", seasonId);
        }
*/
        String[] orderBy = {"Team.name"};

        list.clear();

        try {
            Statement st = db.con.createStatement();
            ResultSet rs;
            if (seriesId == null) {
                rs = st.executeQuery(db.qe.getItems(TABLE_NAME, FIELDS, idFields, orderBy));
            } else {
                // This is too compllicated for the query builder so we do the query by hand
                String q = "SELECT DISTINCT Team.ID, Team.Name FROM `Team`, `Season`, `Series`, `Match` m "
                        + "WHERE Season.Series = Series.id AND m.season = Season.id AND "
                        + "(m.hometeam = Team.id OR m.awayteam = Team.id) "
                        + "AND Series.id = " + seriesId
                        + " ORDER BY Team.name";
                System.err.println(q);
                rs = st.executeQuery(q);
            }
            while (rs.next()) {
                Integer id = rs.getInt(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                Data team = new Data(id, name);
                list.put(id, team);
            }
        } catch (Exception e) {
            System.err.println("Failed to read team data: " + e.toString());
        }

    }

    public void delete(Integer key) {
        Data obj = (Data)list.remove(key);
        if (obj == null) {
            System.err.println("Null object found for key " + key);
        }
        deletedList.put(key, obj);
        System.err.println("Number of deleted items: " + deletedList.size());
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
            System.err.println("Changing " + field_name + " to " + (String) value);
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

package ScheatorDb;
import java.util.LinkedHashMap;
import java.sql.*;

/** The database reflection of the Series database table.
 *
 * @author mep
 */
public class Series extends DbObject {

    /** Table name in the database */
    private static final String[] TABLE_NAME = {"Series"};
    /** Id field name for this table in the database */
    private static final String ID_FIELD = "ID";
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
    public final void fetch(int key) {
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
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, null, idFields , ids, null));
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
    }
}

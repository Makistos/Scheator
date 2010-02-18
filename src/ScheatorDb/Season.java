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
     * @param key
     */
    public final void fetch(int key) {
        this.currentId = key;
        String[] idFields = null;
        String[] ids = null;
        if (currentId != 0) {
            idFields =  new String[1];
            idFields[0] = "series";
            ids = new String[1];
            ids[0] = String.valueOf(currentId);
        } 
        
        list.clear();

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, null, idFields , ids, null));
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                String seriesId = rs.getString(FIELDS[2]);
                Data season = new Data(Integer.parseInt(id.trim()), name, Integer.parseInt(seriesId));
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

        /** Database id of series this season belongs to. */
        public int field_series;

        Data(String name) {
            field_id = 0;
            field_name = name;
            field_series = 0;
        }
         
        Data(String name, int series) {
            field_id = 0;
            field_name = name;
            field_series = series;
        }

        Data(int id, String name, int series) {
            field_id = id;
            field_name = name;
            field_series = series;
        }

        /** Returns season name by default. */
        @Override
        public String toString() {
            return field_name;
        }
    }
 
}

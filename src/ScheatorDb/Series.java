/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
        list = new LinkedHashMap<Integer, SeriesData>();
        db = new SqlDb();
        fetchSeries();
    }

    /** Gets all the series from the database
     * 
     */
    private void fetchSeries() {
        String[] idFields = {""};
        String[] fields = {""};
        list.clear();
        
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLE_NAME, null , null));
            while (rs.next()) {
                String id = rs.getString(FIELDS[0]);
                String name = rs.getString(FIELDS[1]);
                SeriesData series = new SeriesData(Integer.parseInt(id.trim()), name);
                list.put(id, series);
            }
        } catch (Exception e) {
            System.err.println("Failed to read series data: " + e.toString());
        }
    }

    /** A container for a series entity
     * 
     */
    public class SeriesData {

        int field_id;
        String field_name;

        /** Creates a new series entity without a database id.
         *
         * @param name Name of the series.
         */
        SeriesData(String name) {
            this.field_id = 0;
            this.field_name = name;
        }

        /** Creates a series entity with database id and name.
         *
         * @param id   Database id for this entity.
         * @param name Name of the series.
         */
        SeriesData(int id, String name) {
            this.field_id = id;
            this.field_name = name;
        }
        @Override
        public String toString() {
            return field_name;
        }
    }
}

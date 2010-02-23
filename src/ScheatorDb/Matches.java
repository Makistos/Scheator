package ScheatorDb;
import java.util.LinkedHashMap;
import java.sql.*;
import java.lang.reflect.Field;

/** A class representing a match or a number of matches (=schedule).
 *
 * @author mep
 */
public class Matches extends DbObject {

    /** Tables needed to get information for a match. */
    private static final String[] TABLES = {"`Match`", "`Season`", "`Team` AS home", "`Team` AS away"};
    /** Fields that need to be retrieved from the database for a match. */
    private static final String[] FIELDS =
    {"Match.Id AS matchId",
     "Season.id AS seasonId",
     "home.name AS hometeam",
     "away.name AS awayteam",
     "match_no"};

    /** ID fields needed to identify matches required. These values return an
     entire season's matches.*/
    private static final String[] ID_FIELDS =
    { "Match.season",
      "Season.id",
      "home.id",
      "away.id"
    };

    /** Field used to order the matches. */
    private static final String[] ORDER_BY = {"match_no"};
    
    public Matches() {
        // Create a list with an empty Data object. The table controller
        // requires that there is at least one row in the table.
        list = new LinkedHashMap<Integer, Data>();
        Data m = new Data();
        list.put(0, m);
        db = new MySqlDb();
    }

    public Matches(int seasonId) {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(seasonId);
    }

    /** Adds a new match to the list (but not yet to the database).
     *
     * @param match_no Match number.
     * @param homeTeam Home team id.
     * @param awayTeam Away team id.
     */

    public void addNew(int match_no, String homeTeam, String awayTeam) {
        Data match = new Data(null, match_no, homeTeam, awayTeam);
        list.put(match_no, match);
    }

    /** Fetches the match(es) from the database and inserts them into a
     * LinkedHashMap structure.
     *
     * @param seasonId Database id for the season to search matches for.
     */
    public void fetch(int seasonId) {
        list.clear();
        String[] ids = {"Season.id", Integer.toString(seasonId), "Match.hometeam", "Match.awayteam"};
        String query;

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLES, FIELDS, ID_FIELDS, ids, ORDER_BY));
            while (rs.next()) {
                String matchId = rs.getString("matchId");
                String sId = rs.getString("seasonId");
                String home_team = rs.getString("hometeam");
                String away_team = rs.getString("awayteam");
                String match_no = rs.getString("match_no");
                currentId = Integer.parseInt(sId);
                Data team = new Data(Integer.parseInt(matchId.trim()),
                        Integer.parseInt(match_no.trim()),
                        home_team, away_team);
                list.put(matchId, team);
            }
        } catch (Exception e) {
            System.err.println("Failed to read season data: " + e.toString());
        }
    }

    /** One match in the database.
     *
     */
    public class Data extends DbObject.Data {
        /** Id in the database. */
        Integer field_id;
        /** Season "name", e.g. "2007-08". */
        String field_name;
        /** Match number (order) in the schedule. */
        Integer field_matchNumber;
        /** Home team id (from the Team table). */
        String field_homeTeam;
        /** Away team id  (from the Team table). */
        String field_awayTeam;

        public Data() {
            field_id = null;
            field_name = "";
            field_matchNumber = null;
            field_homeTeam = "";
            field_awayTeam = "";
            state = FieldState.NEW;
        }
        
        Data(Integer id, int match, String home, String away) {
            field_id = id;
            field_name = "";
            field_matchNumber = match;
            field_homeTeam = home;
            field_awayTeam = away;
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

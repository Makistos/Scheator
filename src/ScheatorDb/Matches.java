package ScheatorDb;
import java.util.LinkedHashMap;
import java.sql.*;
import java.lang.reflect.Field;
import java.util.*;

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
     "home.id as homeid",
     "away.name AS awayteam",
     "away.id as awayid",
     "match_no"};

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

    public void addNew(Integer match_no, Integer seasonId, String homeTeam, Integer homeTeamId,
            String awayTeam, Integer awayTeamId) {
        Data match = new Data(null, seasonId, match_no, homeTeam, homeTeamId,
                awayTeam, awayTeamId);
        match.state = FieldState.NEW;
        list.put(match_no, match);
        // Remove the empty item now that we have something
        list.remove(0);
        currentId = seasonId;
    }

    /** Fetches the match(es) from the database and inserts them into a
     * LinkedHashMap structure.
     *
     * @param seasonId Database id for the season to search matches for.
     */
    public void fetch(Integer seasonId) {
        list.clear();
        //String[] ids = {"Season.id", Integer.toString(seasonId), "Match.hometeam", "Match.awayteam"};
        
        HashMap<String, Object> idFields = new HashMap<String, Object>();
        FieldReference sid = new FieldReference("Season.id");
        idFields.put("Match.season", sid);
        idFields.put("Season.id", seasonId);
        FieldReference home = new FieldReference("Match.hometeam");
        idFields.put("home.id", home);
        FieldReference away = new FieldReference("Match.awayteam");
        idFields.put("away.id", away);
        
        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLES, FIELDS, idFields, ORDER_BY));
            while (rs.next()) {
                Integer matchId = rs.getInt("matchId");
                Integer sId = rs.getInt("seasonId");
                String home_team = rs.getString("hometeam");
                Integer home_id = rs.getInt("homeid");
                String away_team = rs.getString("awayteam");
                Integer away_id = rs.getInt("awayid");
                Integer match_no = rs.getInt("match_no");
                currentId = sId;
                Data team = new Data(matchId, sId,
                        match_no,home_team, home_id, away_team, away_id);
                list.put(matchId, team);
            }
        } catch (Exception e) {
            System.err.println("Failed to read match data: " + e.toString());
        }
    }

    @Override
    public void save() {
        try {
            Statement st = db.con.createStatement();
            for(Iterator itr = list.values().iterator(); itr.hasNext();) {
                Data row = (Data) itr.next();
                String q;
                HashMap<String, Object> fields = new HashMap<String, Object>();

                fields.put("match_no", row.field_matchNumber);
                fields.put("hometeam", row.field_homeTeamId);
                fields.put("awayteam", row.field_awayTeamId);
                fields.put("season", currentId);
                System.err.println("State = " + row.state);
                switch (row.state) {
                    case SAVED:
                        break;
                    case CHANGED:
                        HashMap<String, Object> idFields = new HashMap<String, Object>();
                        idFields.put("id", row.field_id);
                        q = db.qe.updateItem("Match", fields, idFields);
                        st.executeUpdate(q);
                        break;
                    case NEW:
                        q = db.qe.addItem("Match", fields);
                        st.executeUpdate(q);
                        break;
                }
            }
        } catch (Exception e) {

        }
        // Reload information */
        fetch(currentId);
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
        Integer field_homeTeamId;
        /** Home team name. */
        String field_homeTeam;
        /** Away team id  (from the Team table). */
        Integer field_awayTeamId;
        /** Away team name. */
        String field_awayTeam;
        /** Id of season this match is connected to. */
        Integer field_seasonId;

        public Data() {
            field_id = null;
            field_name = null;
            field_matchNumber = null;
            field_homeTeam = null;
            field_homeTeamId = null;
            field_awayTeam = null;
            field_awayTeamId = null;
            field_seasonId = null;
            state = FieldState.NEW;
        }
        
        Data(Integer id, Integer seasonId, int match, String home, Integer homeId,
                String away, Integer awayId) {
            field_id = id;
            field_name = "";
            field_matchNumber = match;
            field_homeTeam = home;
            field_homeTeamId = homeId;
            field_awayTeam = away;
            field_awayTeamId = awayId;
            field_seasonId = seasonId;
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
            String retval = "(";
            if (field_matchNumber != null) {
                retval = retval + field_matchNumber.toString();
            }
            retval.concat(")");
            if (field_homeTeam != null) {
                retval = retval +"\t" + field_homeTeam;
            }
            if (field_awayTeam != null) {
                retval = retval + "\t" + field_awayTeam;
            }
            return retval;
        }
    }

}

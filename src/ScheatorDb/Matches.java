/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorDb;
import java.util.LinkedHashMap;
import java.sql.*;

/**
 *
 * @author mep
 */
public class Matches extends DbObject {

    private static final String[] TABLES = {"Match", "Season", "Team home", "Team away"};
    private static final String[] FIELDS =
    {"match.Id AS matchId",
     "season.id AS seasonId",
     "home.name as home_team",
     "away.name as away_team",
     "match_no"};
    private static final String[] ID_FIELDS =
    { "seasonId",
      "home.id",
      "away.id"
    };

    private static final String[] ORDER_BY = {"match_no"};
    
    Matches() {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
    }

    Matches(int seasonId) {
        list = new LinkedHashMap<Integer, Data>();
        db = new MySqlDb();
        fetch(seasonId);
    }

    public void fetch(int seasonId) {
        list.clear();
        String[] ids = {"seasonId", "match.hometeam", "match.awayteam"};
        String query;

        try {
            Statement st = db.con.createStatement();
            ResultSet rs = st.executeQuery(db.qe.getItems(TABLES, FIELDS, ID_FIELDS, ids, ORDER_BY));
            while (rs.next()) {
                String matchId = rs.getString("matchId");
                String sId = rs.getString("seasonId");
                String home_team = rs.getString("home_team");
                String away_team = rs.getString("away_team");
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

    public class Data extends DbObject.Data {
        public int field_matchNumber;
        public String field_homeTeam;
        public String field_awayTeam;

        Data(int id, int match, String home, String away) {
            field_id = id;
            field_name = "";
            field_matchNumber = match;
            field_homeTeam = home;
            field_awayTeam = away;
        }
    }

}

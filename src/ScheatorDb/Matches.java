/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorDb;
import java.util.LinkedHashMap;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author mep
 */
public class Matches extends DbObject {

    private static final String[] TABLES = {"`Match`", "`Season`", "`Team` AS home", "`Team` AS away"};
    private static final String[] FIELDS =
    {"Match.Id AS matchId",
     "Season.id AS seasonId",
     "home.name AS hometeam",
     "away.name AS awayteam",
     "match_no"};
    private static final String[] ID_FIELDS =
    { "Match.season",
      "Season.id",
      "home.id",
      "away.id"
    };

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

    public class Data extends DbObject.Data {
        public int field_matchNumber;
        public String field_homeTeam;
        public String field_awayTeam;

        public Data() {
            field_id = 0;
            field_name = "";
            field_matchNumber = 0;
            field_homeTeam = "";
            field_awayTeam = "";
        }
        Data(int id, int match, String home, String away) {
            field_id = id;
            field_name = "";
            field_matchNumber = match;
            field_homeTeam = home;
            field_awayTeam = away;
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import ScheatorDb.Teams;
import ScheatorDb.Matches;
import java.util.*;

/**
 *
 * @author mep
 */
public class Scheduler {
    private Matches matches = new Matches();
    private Integer seasonId;

    public Scheduler(Integer season, Teams participants) {
        seasonId = season;
        createSchedule(participants);
    }

    private void createSchedule(Teams participants) {
        if (participants.getSize() % 2 == 1) {
            // Number of teams uneven ->  add the bye team.
            participants.addBye();
        }

        LinkedHashMap<Integer, Teams.Data> pList= participants.getList();
        ArrayList list = new ArrayList();
        // First, lets turn the LinkedHashMap into a list for easier manipulation
        for (Iterator itr=pList.values().iterator();itr.hasNext();) {
            list.add(itr.next());
        }

        for(int i = 0;i<list.size();i++) {
            createOneRound(i, list);
            // Move last item to first
            list.add(0, list.get(list.size()-1));
            list.remove(list.size()-1);
        }

    }

    /** Creates one round, i.e. a set of matches where each team plays once.
     *
     * @param round Round number.
     * @param list List of teams
     */
    private void createOneRound(int round, ArrayList teams) {
        int mid = teams.size() / 2;
        // Split list into two

        ArrayList l1 = new ArrayList();
        // Can't use sublist (can't cast it to ArrayList - how stupid is that)??
        for(int j=0;j<mid-1;j++) {
            l1.add(teams.get(j));
        }

        ArrayList l2 = new ArrayList();
        // We need to reverse the other list
        for(int j=teams.size()-1;j>mid-1;j--) {
            l2.add(teams.get(j));
        }

        for(int tId = 0;tId<l1.size()-1;tId++) {
            Teams.Data t1;
            Teams.Data t2;
            // Switch sides after each round
            if (round %2 == 1) {
                t1 = (Teams.Data)l1.get(tId);
                t2 = (Teams.Data)l2.get(tId);
            } else {
                t1 = (Teams.Data)l2.get(tId);
                t2 = (Teams.Data)l1.get(tId);
            }
            matches.addNew((round+1)*(tId+1), seasonId, (String)t1.get("name"), (Integer)t1.get("id"),
                    (String)t2.get("name"), (Integer)t2.get("id"));
        }
    }

    public Matches get() {
        return matches;
    }
}

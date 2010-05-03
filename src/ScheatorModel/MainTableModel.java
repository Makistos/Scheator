/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import javax.swing.table.*;
import ScheatorController.*;
import ScheatorDb.*;
import java.util.LinkedHashMap;
import java.util.Iterator;

/** The model for the schedule table on the main window.
 *
 * @author mep
 */
public class MainTableModel extends AbstractTableModel {

    private AbstractController controller;
    Matches provider;
    LinkedHashMap<Integer, Matches.Data> list;
    private String columns[] = {"", "", "", ""};
    private Integer seasonId;

    private static final int COL_ROUND = 0;
    private static final int COL_MATCH = 1;
    private static final int COL_HOMETEAM = 2;
    private static final int COL_AWAYTEAM = 3;

    public MainTableModel(AbstractController controller) {
        provider = new ScheatorDb.Matches();
        this.controller = controller;
        this.controller.addModel(this);
        list = provider.getList();
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(MainTableModel.class);
        //javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(MainTableModel.class, this);

        columns[COL_ROUND] = resourceMap.getString("ColRound.text");
        columns[COL_MATCH] = resourceMap.getString("ColMatch.text");
        columns[COL_HOMETEAM] = resourceMap.getString("ColHome.text");
        columns[COL_AWAYTEAM] = resourceMap.getString("ColAway.text");
        seasonId = 0;
    }

    /** Returns number of rows in the table.
     *
     * @return Row count.
     */
    public int getRowCount() {
        if (list != null && list.size() > 0)
            return list.size();
        else {
            return 1;
        }
    }

    /** Returns number of columns inthe table.
     *
     * @return Column count.
     */
    public int getColumnCount() {
        return columns.length;
    }

    /** Returns name for the given column. This is used as the column name.
     *
     * @param columnIndex Column index.
     * @return Column name.
     */
    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }
    
    public Object getValueAt(int row, int column) {
        Object retval = null;
        int i = 0;
        for(Iterator it=list.values().iterator(); it.hasNext();) {
            Matches.Data dbRow = (Matches.Data) it.next();
            if (i == row) {
                switch(column) {
                    case COL_ROUND:
                        return dbRow.get("round");
                    case COL_MATCH:
                        return dbRow.get("matchNumber");
                    case COL_HOMETEAM:
                        return dbRow.get("homeTeam");
                    case COL_AWAYTEAM:
                        return dbRow.get("awayTeam");
                }
                break;
            }
            i++;
        }
        return retval;
    }

    /** Moves a match to a different position.
     *
     * @param start Start position.
     * @param end Position to move the match to.
     */
    public void moveMatch(int start, int end) {
/*        String tmp[];

        tmp = rows[end];

        if (start > end) {
            for(int i=rows.length;i>0;i--) {
                if (i == start) {
                    tmp = rows[i];
                } else if (i == end) {
                    rows[i+1] = rows[i];
                    rows[i] = tmp;
                    break;
                } else if (i < start) {
                    rows[i+1] = rows[i];
                }
            }
        } else {
            for(int i=0;i<rows.length-1;i++) {
                if (i == start) {
                    tmp = rows[i];
                    //rows[i] = rows[i+1];
                } else if (i == end) {
                    rows[i-1] = rows[i];
                    rows[i] = tmp;
                    break;
                } else if (i > start)
                    rows[i-1] = rows[i];
            }
        }
        System.err.println("Row moved");
        fireTableDataChanged();
 */
    }

    /** Updates the table data from the database and notifies the listeners.
     *
     * @param seasonId Season for which to retrieve the schedule.
     */
    public void update(int seasonId) {
        System.err.println("Updating table for season #" + seasonId);
        provider.fetch(seasonId);
        this.seasonId = seasonId;
        fireTableDataChanged();
    }

    /** Saves the table to the database.
     * 
     */
    public void save() {
        System.err.println("Table saved");
    }

    /** Returns current season id (primary key in database).
     * 
     * @return
     */
    public Integer getSeasonId() {
        return seasonId;
    }

    /** Called by the main controller when the series list has been changed.
     * 
     */
    public void seriesSaved() {
        update(seasonId);
    }
}

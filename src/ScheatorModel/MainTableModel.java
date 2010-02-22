/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import javax.swing.table.*;
import ScheatorController.*;
import ScheatorDb.*;
import org.jdesktop.application.Action;
import java.util.ArrayList;

/** The model for the schedule table on the main window.
 *
 * @author mep
 */
public class MainTableModel extends AbstractTableModel {

    private AbstractController controller;
    ArrayList<Schedule.Data> list;
    DbObject provider;
    private String columns[] = {"", "", ""};

    String rows[][] = {
        {"1", "Arsenal", "Everton"},
        {"1", "Chelsea", "ManU"},
        {"1", "Fulham", "Blackburn"},
        {"1", "Tottenham", "Bolton"}
    };

    public MainTableModel(AbstractController controller) {
        provider = new ScheatorDb.Schedule();
        this.controller = controller;
        controller.addModel(this);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(MainTableModel.class);
        //javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(MainTableModel.class, this);

        columns[0] = resourceMap.getString("ColRound.text");
        columns[1] = resourceMap.getString("ColHome.text");
        columns[2] = resourceMap.getString("ColAway.text");

    }
    public int getRowCount() {
        return rows.length;
    }

    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }
    
    public Object getValueAt(int row, int column) {
        return rows[row][column];
    }

    /** Moves a match to a different position.
     *
     * @param start Start position.
     * @param end Position to move the match to.
     */
    public void moveMatch(int start, int end) {
        String tmp[];

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
    }

    /** Updates the table data from the database and notifies the listeners.
     *
     * @param seasonId Season for which to retrieve the schedule.
     */
    public void update(int seasonId) {
        System.err.println("Updating table for season #" + seasonId);
        provider.fetch(seasonId);
        fireTableDataChanged();
    }

    public void save() {
        System.err.println("Table saved");
    }
}

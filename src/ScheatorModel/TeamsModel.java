/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;
import javax.swing.table.*;
import java.util.LinkedHashMap;
import ScheatorDb.*;
import ScheatorController.*;
import java.util.Iterator;
/**
 *
 * @author mep
 */
public class TeamsModel extends AbstractTableModel {

    private AbstractController controller;
    Teams provider;
    LinkedHashMap<Integer, Matches.Data> list;
    private String columns[] = {""};

    public TeamsModel(AbstractController controller, Integer seasonId) {
        this.controller = controller;
        provider = new ScheatorDb.Teams();
        controller.addModel(this);
        list = provider.getList();
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(TeamsModel.class);

        columns[0] = resourceMap.getString("ColTeam.text");

        /* Get the teams */
        provider.fetch(seasonId);
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

    /** Returns number of columns in the table.
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

    public Object getValueAt(int row, int col) {
        Object retval = null;
        int i = 0;
        for(Iterator it=list.values().iterator(); it.hasNext();) {
            Teams.Data dbRow = (Teams.Data) it.next();
            if (i == row) {
                switch(col) {
                    case 0:
                        return dbRow.get("name");
                }
                break;
            }
            i++;
        }
        return retval;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        // The entire table is editable
        return true;
    }

    @Override
    public Class getColumnClass(int col) {
        return String.class;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        int i = 0;
        for(Iterator it=list.values().iterator(); it.hasNext();) {
            Teams.Data dbRow = (Teams.Data) it.next();
            if (i == row) {
                switch(col) {
                    case 0:
                        dbRow.set("name", value);
                }
                break;
            }
            i++;
        }
        fireTableCellUpdated(row, col);
    }

}
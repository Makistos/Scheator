package ScheatorModel;

import javax.swing.table.*;
import java.util.LinkedHashMap;
import ScheatorDb.*;
import ScheatorController.*;
import java.util.Iterator;

/** Implements the team data model.
 *
 * Data is retrieved through the provider (at this point a MySql connector).
 * 
 * @author mep
 */
public class TeamsModel extends AbstractTableModel {

    /** Controller */
    private AbstractController controller;
    /** Data provider. */
    Teams provider;
    /** List containing the teams. */
    LinkedHashMap<Integer, Teams.Data> list;
    /**  Fake variable to get the column count. */
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

    public void update(Integer seasonId) {
        list.clear();
        provider.fetch(seasonId);
        fireTableDataChanged();
    }

    /** Returns number of rows in the table.
     *
     * @return Row count.
     */
    public int getRowCount() {
        if (list != null && list.size() > 0)
            return list.size();
        else {
            return 0;
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

    @Override
    public Object getValueAt(int row, int col) {
        Object retval = null;
        int i = 0;
        for(Iterator it=list.values().iterator(); it.hasNext();) {
            Teams.Data dbRow = (Teams.Data) it.next();
            if (i == row) {
                //return dbRow.get("name");
                return dbRow;
            }
            i++;
        }
        return retval;
    }

    /** Tells if a certain cell is editable.
     *
     * This method tells the delegate whether a given cell is editable.
     * Since this table only has one column and it is editable, this method
     * always returns true.
     *
     * @param row Item's row.
     * @param col Item's column.
     * @return Always true.
     */
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

    public void addTeam(String name) {
        provider.addNew(name);
        fireTableDataChanged();
    }


    public void removeTeam(int row) {
        int i = 0;
        Integer key = null;

        for(Iterator it=list.values().iterator(); it.hasNext();) {
            Teams.Data dbRow = (Teams.Data) it.next();
            if (i == row) {
                key =  (Integer) dbRow.get("id");
                break;
            }
            i++;
        }

        System.err.println("removeTeam() key:" + key);
        if (key != null) {
            provider.delete(key);
            //list.clear();
            list = provider.getList();
            System.err.println("removeTeam() count:" + list.size());
            provider.save();
            fireTableDataChanged();
        }
    }

    public void saveTeams() {
        provider.save();
    }

}

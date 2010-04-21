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
import ScheatorController.*;

/** Table model for a series table.
 *
 * @author mep
 */
public class SeriesTableModel extends AbstractTableModel {
    /** Controller */
    private MainController controller;
    /** Data provider. */
    Series provider;
    /** List containing the teams. */
    LinkedHashMap<Integer, Series.Data> list;
    /**  Fake variable to get the column count. */
    private String columns[] = {""};

    public SeriesTableModel(MainController controller, Integer seasonId) {
        this.controller = controller;
        provider = new ScheatorDb.Series();
        controller.addModel(this);
        list = provider.getList();
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(SeriesTableModel.class);

        columns[0] = resourceMap.getString("ColSeries.text");

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
            Series.Data dbRow = (Series.Data) it.next();
            if (i == row) {
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
            Series.Data dbRow = (Series.Data) it.next();
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

    public void addRow(String name) {
        provider.addNew(name);
        fireTableDataChanged();
    }

    public void removeTeam(int row) {
        int i = 0;
        Integer key = null;

        for(Iterator it=list.values().iterator(); it.hasNext();) {
            Series.Data dbRow = (Series.Data) it.next();
            if (i == row) {
                key =  (Integer) dbRow.get("id");
                break;
            }
            i++;
        }

        if (key != null) {
            provider.delete(key);
            //list.clear();
            list = provider.getList();
            fireTableDataChanged();
        }
    }

    public void saveSeries() {
        provider.save();
        controller.seriesSaved();
    }

/*    public void update() {
        fireTableDataChanged();
    }
 */
}

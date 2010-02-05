/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import javax.swing.table.*;

/**
 *
 * @author mep
 */
public class MainTableModel extends AbstractTableModel implements TableModel {

    public void setContents() {
        //firePropertyChange();
    }

    public int getRowCount() {
        return 0;
    }

    public int getColumnCount() {
        return 0;
    }

    public Object getValueAt(int row, int column) {
        Object obj = new Object();

        return obj;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author mep
 */
public class SeriesComboBoxModel extends AbstractListModel implements ComboBoxModel {

    String selection = null;
    private String[][] series = {
        {"0","Valioliiga"},
        {"1", "SM-liiga"},
        {"2", "NHL"},
        {"3", "Veikkausliiga"}
    };
    
    public Object getElementAt(int index) {
        return series[index][1];
    }

    public int getSize() {
        return series.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (String) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }

    public int getSelectedId() {
        return Integer.parseInt(series[1][0]);
    }
}

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
public class SeasonComboBoxModel extends AbstractListModel implements ComboBoxModel {

    String selection = null;
    private String[] seasons = {"", "", ""};

    public Object getElementAt(int index) {
        return seasons[index];
    }

    public int getSize() {
        return seasons.length;
    }

    public void setSelectedItem(Object anItem) {
        selection = (String) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }

    public void fill(int series) {
        seasons[0] = "2007-08";
        seasons[1] = "2008-09";
        seasons[2] = "2009-10";
        fireContentsChanged(this, 0, seasons.length-1);
    }
}

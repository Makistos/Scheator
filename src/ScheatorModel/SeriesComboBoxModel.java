/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import ScheatorDb.*;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import java.util.LinkedHashMap;

/** Model for the series combo box on the main screen.
 *
 * @author mep
 */
public class SeriesComboBoxModel extends AbstractListModel implements ComboBoxModel {

    Series provider;
    LinkedHashMap<Integer, Series.SeriesData> series;
    Series.SeriesData selection = null;

    public SeriesComboBoxModel() {
        provider = new ScheatorDb.Series();
        series = provider.getList();
    }

/*    private String[][] series = {
        {"0","Valioliiga"},
        {"1", "SM-liiga"},
        {"2", "NHL"},
        {"3", "Veikkausliiga"}
    };
*/
    public Object getElementAt(int index) {
        return series.get(1);

/*        if (series.containsKey(index)) {
            
            return series.get(index);
        } else {
            return null;
        } */
    }

    public int getSize() {
        return series.size();
    }

    public void setSelectedItem(Object anItem) {
        selection =  (Series.SeriesData) anItem;
    }

    public Object getSelectedItem() {
        return selection;
    }

/*    public int getSelectedId() {
        return Integer.parseInt();
    }
 */
}

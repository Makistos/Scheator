/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import ScheatorDb.*;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Collection;

/** Model for the series combo box on the main screen.
 *
 * @author mep
 */
public class SeriesComboBoxModel extends ScheatorComboBoxModel {

    public SeriesComboBoxModel() {
        provider = new ScheatorDb.Series();
        list = provider.getList();
    }

}

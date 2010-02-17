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
public class SeasonComboBoxModel extends ScheatorComboBoxModel {

    public SeasonComboBoxModel() {
        provider = new ScheatorDb.Season();
        list = provider.getList();
    }
}

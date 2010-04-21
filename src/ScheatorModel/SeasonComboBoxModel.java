/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import ScheatorController.*;

/** Data model for a season combobox.
 *
 * @author mep
 */
public class SeasonComboBoxModel extends ScheatorComboBoxModel {

    AbstractController controller;
    
    public SeasonComboBoxModel(AbstractController controller) {
        this.controller = controller;
        controller.addModel(this);
        provider = new ScheatorDb.Season();
        list = provider.getList();
    }

    /** Updates the data from the database.
     *
     * @param seriesId Id of the series for which to find the seasons.
     */
    public void update(int seriesId) {
        provider.fetch(seriesId);
        System.err.println("Firing contentsChange()");
        fireContentsChanged(this, 0, list.size()-1);
    }
}

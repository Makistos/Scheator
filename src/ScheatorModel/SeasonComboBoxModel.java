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
    private int seriesId;

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
        this.seriesId = seriesId;
        provider.fetch(seriesId);
        //list.clear();
        list = provider.getList();
        System.err.println("Firing contentsChange() (list size = " + list.size() + ")");
        fireContentsChanged(this, 0, list.size()-1);
    }

    public void seriesSaved() {
        update(seriesId);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

/**
 *
 * @author mep
 */
public class SeasonComboBoxModel extends ScheatorComboBoxModel {

    public SeasonComboBoxModel() {
        provider = new ScheatorDb.Season();
        list = provider.getList();
    }

    /** Updates the data from the database.
     *
     * @param seriesId Id of the series for which to find the seasons.
     */
    public void update(int seriesId) {
        provider.fetch(seriesId);
        fireContentsChanged(this, 0, list.size()-1);
    }
}

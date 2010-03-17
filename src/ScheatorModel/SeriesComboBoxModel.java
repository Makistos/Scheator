/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import ScheatorController.*;

/** Model for the series combo box on the main screen.
 *
 * @author mep
 */
public class SeriesComboBoxModel extends ScheatorComboBoxModel {

    private AbstractController controller;

    public SeriesComboBoxModel(AbstractController controller) {
        provider = new ScheatorDb.Series();
        this.controller = controller;
        this.controller.addModel(this);

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

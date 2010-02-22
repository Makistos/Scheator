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
        controller.addModel(this);

        list = provider.getList();
    }
}

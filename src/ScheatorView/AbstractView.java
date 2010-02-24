/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import javax.swing.JPanel;
import javax.swing.event.*;
import java.awt.event.*;

/**
 *
 * @author mep
 */
public class AbstractView extends JPanel {

    public void comboBoxEvent(ListDataEvent e) {
        // By default, do nothing
    }

    public void mainTableChanged(TableModelEvent e) {
        // By default, do nothing
    }

    public void itemStateChanged(ItemEvent e) {
        // By default, do nothing
    }
}

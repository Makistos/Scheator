/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import ScheatorModel.*;
import ScheatorController.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author mep
 */
public class TeamPanel extends AbstractView {

    TeamsModel tableModel;

    AbstractController controller;

    TeamPanel(AbstractController controller) {
        Integer id = null;
        tableModel = new TeamsModel(controller, id);
        tableModel.addTableModelListener(new TableListener());
    
        JTable teamTable = new JTable(tableModel);
        teamTable.setFillsViewportHeight(true);
        add(teamTable);
    }

    class TableListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {

        }
    }
}

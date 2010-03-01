/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import ScheatorModel.*;
import ScheatorController.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.DefaultCellEditor;
import javax.swing.BoxLayout;
import org.jdesktop.application.Action;

/**
 *
 * @author mep
 */
public class TeamPanel extends AbstractView {

    TeamsModel tableModel;
    JTable teamTable;

    AbstractController controller;

    TeamPanel(AbstractController controller) {
        Integer id = null;
        tableModel = new TeamsModel(controller, id);
        tableModel.addTableModelListener(new TableListener());
        this.controller = controller;
        controller.addView(this);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(TeamPanel.class);
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(TeamPanel.class, this);

        //this.setLayout(new FlowLayout(FlowLayout.));

        teamTable = new JTable(tableModel);

        teamTable.setDefaultEditor(String.class, new DefaultCellEditor(new JTextField()));

        JScrollPane tableScroll = new JScrollPane(teamTable);
        add(tableScroll);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton addButton = new JButton();
        addButton.setAction(actionMap.get("add"));
        addButton.setText(resourceMap.getString("addButton.Action.text"));
        buttonPanel.add(addButton);

        JButton delButton = new JButton();
        delButton.setAction(actionMap.get("delete"));
        delButton.setText(resourceMap.getString("delButton.Action.text"));
        buttonPanel.add(delButton);

        add(buttonPanel);
    }

    public void saveTeams() {
        tableModel.saveTeams();
    }

    public void undoTeamChanges() {
        // Do nothing, the changes will be deleted when this window closes.
    }

    public void teamWindowClosing() {
        controller.removeView(this);
    }

    @Action
    public void add() {
        tableModel.addTeam("");
        validate();
    }

    @Action
    public void delete() {
        tableModel.removeTeam(teamTable.getSelectedRow());
    }
    
    class TableListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            //controller.teamEdited(e.)
        }
    }


}

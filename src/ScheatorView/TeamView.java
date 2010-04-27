/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import org.jdesktop.application.Action;
import java.awt.*;
import ScheatorModel.*;
import javax.swing.*;
import javax.swing.event.*;
import ScheatorController.*;

/**
 *
 * @author mep
 */
public class TeamView extends javax.swing.JFrame {

    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    private TeamPanel teamPanel;
    private AbstractController controller;
    private org.jdesktop.application.ResourceMap resourceMap;
    javax.swing.ActionMap actionMap;
    TeamsModel tableModel;
    
    public TeamView(java.awt.Frame parent, AbstractController controller) {
        //super(parent);
        this.controller = controller;
        controller.addFrame(this);

        resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(TeamView.class);
        actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(TeamView.class, this);

        initComponents();

        getRootPane().setDefaultButton(okButton);
    }

    public void initComponents() {
        Integer id = null;
        tableModel = new TeamsModel(controller, id);
        teamPanel = new TeamPanel(controller, tableModel);
        tableModel.addTableModelListener(new TableListener());

        javax.swing.BoxLayout layout = new javax.swing.BoxLayout(getContentPane(), BoxLayout.Y_AXIS);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        okButton = new JButton();
        cancelButton = new JButton();

        okButton.setAction(actionMap.get("okButton"));
        okButton.setName("okButton");

        cancelButton.setAction(actionMap.get("cancelButton"));
        cancelButton.setName("cancelButton");

        bottom.add(okButton);
        bottom.add(cancelButton);

        getContentPane().setLayout(layout);

        add(teamPanel);
        add(bottom);

        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Action
    public void cancelButton() {
        // Dispose changes
        dispose();
    }

    @Action
    public void okButton() {
        // Save contents
        tableModel.saveTeams();
        dispose();
    }

    class TableListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {

        }
    }
    
}

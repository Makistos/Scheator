/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import org.jdesktop.application.Action;
import ScheatorController.*;
import javax.swing.*;
import java.awt.Dimension;
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
    private JPanel teamPanel;
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

        pack();
        getRootPane().setDefaultButton(okButton);
    }

    public void initComponents() {
        Integer id = null;
        teamPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tableModel = new TeamsModel(controller, id);
        tableModel.addTableModelListener(new TableListener());
        JTable teamTable = new JTable(tableModel);
        JLabel info = new JLabel("Edit teams");
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        okButton = new JButton();
        cancelButton = new JButton();

        okButton.setAction(actionMap.get("okButton"));
        okButton.setName("okButton");

        cancelButton.setAction(actionMap.get("cancelButton"));
        cancelButton.setName("cancelButton");

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(info)
                .addGap(18,18,18)
                .addComponent(teamTable)
                .addGap(18,18,18)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(okButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cancelButton))
                );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(info)
                .addComponent(teamTable)
                .addGap(18,18,18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                );

        setSize(new Dimension(100,100));
/*
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        mainPanel.setPreferredSize(new Dimension(200,200));
        add(mainPanel);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        mainPanel.add(info, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        teamTable.setFillsViewportHeight(true);
        teamTable.setPreferredSize(new Dimension(200,200));
        teamPanel.setPreferredSize(new Dimension(200,200));
        
        teamPanel.add(teamTable);
        mainPanel.add(teamPanel,c);

        bottom.setAlignmentX(1f);
        
        bottom.add(okButton);

        bottom.add(cancelButton);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 10;
        c.ipadx = 30;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 0;
        c.gridwidth = 1;
        bottom.setBackground(Color.red);
        mainPanel.add(bottom,c);

        setSize(200,200);

 */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pack();
    }

    @Action
    public void cancelButton() {
        // Dispose changes
        dispose();
    }

    @Action
    public void okButton() {
        // Save contents
        dispose();
    }

    class TableListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {

        }
    }
    
}

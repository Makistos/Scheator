/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import org.jdesktop.application.Action;
import ScheatorController.*;
import javax.swing.*;
import java.awt.Dimension;

/**
 *
 * @author mep
 */
public class TeamView extends javax.swing.JDialog {

    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    private TeamPanel teamPanel;
    private AbstractController controller;
    private org.jdesktop.application.ResourceMap resourceMap;
    javax.swing.ActionMap actionMap;
    
    public TeamView(java.awt.Frame parent, AbstractController controller) {
        super(parent);
        this.controller = controller;
        controller.addFrame(this);

        resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(TeamView.class);
        actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(TeamView.class, this);

        initComponents();

        getRootPane().setDefaultButton(okButton);
    }

    public void initComponents() {
        teamPanel = new TeamPanel(controller);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        mainPanel.add(Box.createVerticalGlue());

        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.X_AXIS));

        mainPanel.add(teamPanel);
        
        JPanel bottom = new JPanel();
        bottom.setAlignmentX(1f);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
        
        okButton = new JButton();
        okButton.setAction(actionMap.get("okButton"));
        okButton.setName("okButton");
        bottom.add(okButton);
        bottom.add(Box.createRigidArea(new Dimension(5, 0)));

        cancelButton = new JButton();
        cancelButton.setAction(actionMap.get("cancelButton"));
        cancelButton.setName("cancelButton");
        bottom.add(cancelButton);
        bottom.add(Box.createRigidArea(new Dimension(15, 0)));

        mainPanel.add(bottom);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

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
    
}

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
public class ScheduleView extends javax.swing.JFrame {

    private JTextField seasonName = new JTextField(10);
    private JComboBox series = new JComboBox();
    private JTextField newSeries = new JTextField(10);
    private JButton addNewSeries = new JButton("Add new");
    private javax.swing.JButton genButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox onlySeriesTeamsCb = new JCheckBox();
    private javax.swing.JTable teamsTable = new JTable();
    private javax.swing.JButton addTeam = new JButton("Add team");
    private javax.swing.JButton delTeam = new JButton("Remove team");

    SeriesComboBoxModel seriesModel;
    
    //private JPanel teamsPanel;
    //private JPanel infoPanel;
    private AbstractController controller;
    private org.jdesktop.application.ResourceMap resourceMap;
    javax.swing.ActionMap actionMap;
    TeamsModel tableModel;

    public ScheduleView(java.awt.Frame parent, AbstractController controller) {

        this.controller = controller;
        controller.addFrame(this);

        resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(ScheduleView.class);
        actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(ScheduleView.class, this);

        initComponents();

        pack();
        getRootPane().setDefaultButton(genButton);

    }

    private void initComponents() {
        JLabel seasonNameLbl = new JLabel("Name");

        JLabel seriesLbl = new JLabel("Series");
        
        JPanel infoPanel = new JPanel();                
        JPanel teamsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel teamsButtons = new JPanel();
        JPanel teamsInputFields = new JPanel();
        JPanel seasonFields = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel seriesFields = new JPanel(new FlowLayout(FlowLayout.LEFT));
        javax.swing.BoxLayout layout = new javax.swing.BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        javax.swing.BoxLayout layout2 = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
        javax.swing.BoxLayout layout3 = new BoxLayout(teamsButtons, BoxLayout.Y_AXIS);
        javax.swing.BoxLayout layout4 = new BoxLayout(teamsInputFields, BoxLayout.Y_AXIS);

        infoPanel.setLayout(layout2);
        teamsButtons.setLayout(layout3);
        teamsInputFields.setLayout(layout4);
        
        seriesModel = new SeriesComboBoxModel(controller);

        getContentPane().setLayout(layout);

        // Info panel

        seasonFields.add(seasonNameLbl);
        seasonFields.add(seasonName);

        seriesFields.add(seriesLbl);
        seriesFields.add(series);
        seriesFields.add(newSeries);
        seriesFields.add(addNewSeries);

        infoPanel.add(seasonFields);
        infoPanel.add(seriesFields);

        // Teams panel

        teamsInputFields.add(onlySeriesTeamsCb);
        teamsInputFields.add(teamsTable);
        teamsButtons.add(addTeam);
        teamsButtons.add(delTeam);

        teamsPanel.add(teamsInputFields);
        teamsPanel.add(teamsButtons);

        // Bottom panel
        genButton = new JButton();
        cancelButton = new JButton();

        genButton.setAction(actionMap.get("genButton"));
        genButton.setName("genButton");

        cancelButton.setAction(actionMap.get("cancelButton"));
        cancelButton.setName("cancelButton");

        bottom.add(genButton);
        bottom.add(cancelButton);

        add(infoPanel);
        add(teamsPanel);
        add(bottom);
        pack();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    @Action
    public void cancelButton() {
        // Dispose changes
        dispose();
    }

}

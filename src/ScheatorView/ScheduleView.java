/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import org.jdesktop.application.Action;
import java.awt.*;
import ScheatorModel.*;
import javax.swing.*;
import ScheatorController.*;
import javax.swing.event.*;
import ScheatorDb.Teams;
import ScheatorDb.Series;
import java.awt.event.*;

/**
 *
 * @author mep
 */
public class ScheduleView extends javax.swing.JFrame {

    private JTextField seasonName = new JTextField(10);
    private JComboBox series;
    private JTextField newSeries = new JTextField(10);
    private JButton addNewSeries = new JButton("Add new");
    private javax.swing.JButton genButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox onlySeriesTeamsCb = new JCheckBox();
    private javax.swing.JTable teamsTable;
    private javax.swing.JButton addTeam = new JButton("Add team");
    private javax.swing.JButton delTeam = new JButton("Remove team");

    TeamsModel tableModel;
    SeriesComboBoxModel seriesModel;
    
    //private JPanel teamsPanel;
    //private JPanel infoPanel;
    private MainController controller;
    private org.jdesktop.application.ResourceMap resourceMap;
    javax.swing.ActionMap actionMap;

    public ScheduleView(java.awt.Frame parent, AbstractController controller) {

        this.controller = (MainController) controller;
        this.controller.addFrame(this);

        resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(ScheduleView.class);
        actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(ScheduleView.class, this);

        initComponents();

        onlySeriesTeamsCb.addItemListener(new CbListener());

        getRootPane().setDefaultButton(genButton);

    }

    private void initComponents() {
        Integer id = null;

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
        javax.swing.BoxLayout layout5 = new BoxLayout(teamsPanel, BoxLayout.X_AXIS);

        teamsPanel.setLayout(layout5);
        infoPanel.setLayout(layout2);
        teamsButtons.setLayout(layout3);
        teamsInputFields.setLayout(layout4);
        
        seriesModel = new SeriesComboBoxModel(controller);
        series = new JComboBox(seriesModel);
        series.setPrototypeDisplayValue("XXXXXXXXXXXX");
        series.setName("series");
        series.setAction(actionMap.get("seriesList"));
        series.setEditable(false);

        tableModel = new TeamsModel(controller, id);
        tableModel.addTableModelListener(new TableListener());

        teamsTable = new JTable(tableModel);
        teamsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        getContentPane().setLayout(layout);

        // Info panel

        seasonFields.add(seasonNameLbl);
        seasonFields.add(seasonName);

        seriesFields.add(seriesLbl);
        seriesFields.add(series);
        seriesFields.add(newSeries);
        seriesFields.add(addNewSeries);

        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Season/Tournament"));
        infoPanel.add(seasonFields);
        infoPanel.add(seriesFields);

        // Teams panel

        JScrollPane tableScroll = new JScrollPane(teamsTable);

        teamsInputFields.add(onlySeriesTeamsCb);
        teamsTable.setPreferredSize(new Dimension(300, 400));
        teamsInputFields.add(teamsTable);
        teamsButtons.add(addTeam);
        teamsButtons.add(delTeam);

        teamsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Participants"));
        teamsPanel.add(tableScroll);
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

    /** Action to generate the schedule.
     * 
     */
    @Action
    public void genButton() {
        System.err.println("Generating schedule...");
        //LinkedHashMap<Integer, Teams.Data> teams = new LinkedHashMap<Integer, Teams.Data>();
        Teams teams = new Teams();

        int selection[] = teamsTable.getSelectedRows();

        // Create list of selected teams
//        teams = tableModel.getSelected(selection);
        for(int i=0;i<selection.length;i++) {
            teams.add((Teams.Data) tableModel.getValueAt(selection[i], 0));
            //teams.put((Integer)team.get("id"), team);
        }

        // Forward request to controller
        controller.generateSchedule(seasonName.toString(), (Series.Data)series.getSelectedItem(), teams);

        dispose();
    }

    @Action
    public void cancelButton() {
        // Dispose changes
        dispose();
    }

    @Action
    public void add() {
        tableModel.addTeam("");
        validate();
    }

    @Action
    public void delete() {
        tableModel.removeTeam(teamsTable.getSelectedRow());
    }

    class TableListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            tableModel.saveTeams();
        }
    }

    class CbListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                tableModel.update(null);
            } else {
                Series.Data data = (Series.Data)seriesModel.getSelectedItem();
                Integer id = (Integer) data.get("id");
                System.err.println("Item selected: " + id);
                tableModel.update(id);
            }
        }
    }

}

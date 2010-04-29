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
        addNewSeries.setAction(actionMap.get("addNewSeries"));
        // resourceMap.getString() doesn't work here for some reason, and I
        // don't have time to figure this out.
        addNewSeries.setText("Add new");
        //addNewSeries.setName("addNewSeries");
        seriesFields.add(addNewSeries);

        infoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Season/Tournament"));
        infoPanel.add(seasonFields);
        infoPanel.add(seriesFields);

        // Teams panel

        JScrollPane tableScroll = new JScrollPane(teamsTable);

        onlySeriesTeamsCb.setToolTipText("Only show teams in selected series");
        onlySeriesTeamsCb.setText("Only series");
        onlySeriesTeamsCb.addItemListener(new CbListener());
//        onlySeriesTeamsCb.setAction(actionMap.get("onlySeriesTeamsCb"));
//        onlySeriesTeamsCb.setName("OnlySeriesTeamsCb");

        teamsInputFields.add(onlySeriesTeamsCb);
        teamsTable.setPreferredSize(new Dimension(300, 400));
        teamsInputFields.add(teamsTable);
        addTeam.setAction(actionMap.get("addTeam"));
        addTeam.setName("addTeam");
        teamsButtons.add(addTeam);

        delTeam.setAction(actionMap.get("delTeam"));
        delTeam.setName("delTeam");
        teamsButtons.add(delTeam);

        teamsTable.setAlignmentY(TOP_ALIGNMENT);
        //teamsTable.setFillsViewportHeight(true);
        //teamsTable.setPreferredScrollableViewportSize(new Dimension(300,600));
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
     *  This function first checks that the user has entered a name for the
     *  new schedule (season name) and selected a series. Both are required
     *  before the action can be taken.
     *
     *  If either is missing, the user is informed to fill in the missing info.
     */
    @Action
    public void genButton() {
        String name = seasonName.toString();
        Series.Data s = (Series.Data)series.getSelectedItem();
        //LinkedHashMap<Integer, Teams.Data> teams = new LinkedHashMap<Integer, Teams.Data>();

        if (!name.equals("") && s != null) {
            System.err.println("Generating schedule...");
            Teams teams = new Teams();

            int selection[] = teamsTable.getSelectedRows();

            // Create list of selected teams
            for(int i=0;i<selection.length;i++) {
                teams.add((Teams.Data) tableModel.getValueAt(selection[i], 0));
            }

            // Forward request to controller
            controller.generateSchedule(name, s, teams);

            dispose();
        } else {
            // User must supply a name for the season
            JOptionPane.showMessageDialog(this,"Season/Tournament must have a name and a series.");
        }
    }

    @Action
    public void cancelButton() {
        // Dispose changes
        dispose();
    }


    /** Called when the user clicks the Add new team button.
     * 
     */
    @Action
    public void addTeam() {
        tableModel.addTeam("");
        //validate();
    }

    /** Called when the Add new series button is clicked.
     *
     *  Checks if the connected text field has any text and if not,
     *  tells the user to fill it. Also sets the current index of the
     *  drop-box to the newly created series.
     *
     *  This operation can not be undone by clicking the Cancel button.
     */
    @Action
    public void addNewSeries() {
        String name = newSeries.getText();
        Integer newIdx;
        System.err.println("Adding a new series called: " + name + ".");
        if (!name.equals("")) {
            seriesModel.add(name);
            newIdx = seriesModel.getIndexByName(name);
            series.setSelectedIndex(newIdx);
        } else {
            JOptionPane.showMessageDialog(this,"Series must have a name.");
        }
        validate();
    }


    /** Called when the user clicks the Delete team button.
     *
     *  This operation can not be undone by clicking the Cancel button.
     * 
     */
    @Action
    public void delTeam() {
        System.err.println("Deleting team");
        tableModel.removeTeam(teamsTable.getSelectedRow());
        tableModel.saveTeams();
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scheator;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author mep
 */
public class ScheatorMainView extends FrameView {

    private JDialog aboutBox;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;

    public ScheatorMainView(SingleFrameApplication app) {
        super(app);

        initComponents();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ScheatorApp.getApplication().getMainFrame();
            aboutBox = new ScheatorAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ScheatorApp.getApplication().show(aboutBox);
    }

    @Action
    public void createSchedule() {
        
    }

    @Action
    public void print() {
        
    }
    
    @Action
    public void editSeries() {

    }

    @Action
    public void editTeams() {

    }

    @Action
    public void editSchedule() {

    }
    
    private void initComponents() {
        String[] columnNames = {"a", "b", "c"};
        Object[][] data = {{"aaa", "bbb", "ccc"}};
        String[] seriesNames = {"SM-liiga", "Veikkausliiga", "Valioliiga"};
        String[] seasonNames = {"2008-09", "2009-10"};
        mainPanel = new javax.swing.JPanel();

        // Menus
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem createScheduleMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem printMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu editMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem seriesMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem teamsMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem scheduleMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();

        // Status panel
        statusPanel = new javax.swing.JPanel();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        JLabel seriesLabel = new JLabel();
        JLabel seasonLabel = new JLabel();

        JComboBox seriesList = new JComboBox(seriesNames);
        JComboBox seasonList = new JComboBox(seasonNames);
        JButton searchButton = new JButton();

        JScrollPane searchPane = new JScrollPane();
        JScrollPane tablePane = new JScrollPane();
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchPane, tablePane);
        JPanel searchPanel = new javax.swing.JPanel();
        JPanel tablePanel = new javax.swing.JPanel();

        JTable mainTable = new JTable(data, columnNames);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(ScheatorMainView.class);
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(ScheatorMainView.class, this);

        seriesLabel.setText(resourceMap.getString("seriesLabel.text"));
        seasonLabel.setText(resourceMap.getString("seasonLabel.text"));
        searchButton.setText(resourceMap.getString("searchButton.text"));
        
        mainTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(mainTable);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setName("splitPane");
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(50);

        searchPanel.add(seriesLabel);
        searchPanel.add(seriesList);
        searchPanel.add(seasonLabel);
        searchPanel.add(seasonList);
        searchPanel.add(searchButton);
        searchPane.add(searchPanel);
        searchPane.setName("searchPane");
        searchPane.setViewportView(searchPanel);

        splitPane.setTopComponent(searchPane);

        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
        tablePanel.add(tableScrollPane);
        
        tablePane.setName("tablePane");
        tablePane.setViewportView(tablePanel);

        splitPane.setBottomComponent(tablePane);
        
        mainPanel.setName("mainPanel"); // NOI18N

        mainPanel.add(splitPane);
        
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        ////////////
        // Menu bars
        ////////////
        menuBar.setName("menuBar"); // NOI18N

        // ----
        // File
        //
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        createScheduleMenuItem.setAction(actionMap.get("createSchedule"));
        createScheduleMenuItem.setName("createSchedule");
        fileMenu.add(createScheduleMenuItem);

        fileMenu.addSeparator();
        
        printMenuItem.setAction(actionMap.get("print"));
        printMenuItem.setName("print");
        fileMenu.add(printMenuItem);

        fileMenu.addSeparator();

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        // ----
        // Edit
        //
        editMenu.setText(resourceMap.getString("editMenu.text"));
        editMenu.setName("editMenu");
        
        seriesMenuItem.setAction(actionMap.get("editSeries"));
        seriesMenuItem.setName("editSeries");
        editMenu.add(seriesMenuItem);

        teamsMenuItem.setAction(actionMap.get("editTeams"));
        teamsMenuItem.setName("editTeams");
        editMenu.add(teamsMenuItem);

        scheduleMenuItem.setAction(actionMap.get("editSchedule"));
        scheduleMenuItem.setName("editSchedule");
        editMenu.add(scheduleMenuItem);
        
        menuBar.add(editMenu);

        // ----
        // Help
        //
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);

    }

}

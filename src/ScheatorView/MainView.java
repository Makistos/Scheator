/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import scheator.*;
import ScheatorController.MainController;
import java.beans.PropertyChangeEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 *
 * @author mep
 */
public class MainView extends FrameView {

    private JDialog aboutBox;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private MainController controller;

    public MainView(SingleFrameApplication app, MainController controller) {

        super(app);
        this.controller = controller;
        controller.addMainFrame(this);
        
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

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(MainView.class);
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(MainView.class, this);

        // Panels (one split pane that includes two scroll panes)
        MainSearchPanel searchPanel = new MainSearchPanel(controller);
        MainTablePanel tablePanel = new MainTablePanel(controller);

        controller.addView(searchPanel);
        controller.addView(tablePanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchPanel, tablePanel);
        //JPanel searchPanel = new javax.swing.JPanel();

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setName("splitPane");
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(50);

        //searchPanel = createSearchPanel(resourceMap, actionMap);

        splitPane.setTopComponent(searchPanel);

//        tablePanel = createTablePanel(resourceMap, actionMap);
        
        splitPane.setBottomComponent(tablePanel);
        
        //mainPanel.setSize(new Dimension(1000,1000));
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

    public void mainTableChanged(TableModelEvent e) {
        
    }
    public void comboBoxEvent(ListDataEvent e) {

    }

    public void itemStateChanged(ItemEvent e) {
        
    }
}

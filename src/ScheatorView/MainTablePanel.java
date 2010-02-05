/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import java.beans.PropertyChangeEvent;
import org.jdesktop.application.Action;
import javax.swing.*;
import java.awt.*;

import ScheatorController.MainController;
/**
 *
 * @author mep
 */
public class MainTablePanel extends AbstractViewPanel {

    JTable mainTable;
    javax.swing.JButton upButton = new javax.swing.JButton();
    javax.swing.JButton downButton = new javax.swing.JButton();
    javax.swing.JButton saveButton = new javax.swing.JButton();

    private MainController controller;

    public MainTablePanel(MainController controller) {

        init();

    }

    private void init() {

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(MainView.class);
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(MainView.class, this);

        javax.swing.JPanel panel = new javax.swing.JPanel();
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();

        mainTable = initTable(resourceMap);

        mainTable.setFillsViewportHeight(true);
        JScrollPane scrollPanel = new JScrollPane(mainTable);

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        upButton.setAction(actionMap.get("moveUp"));
        upButton.setText(resourceMap.getString("upButton.text"));
        upButton.setName("upButton");

        downButton.setAction(actionMap.get("moveDown"));
        downButton.setText(resourceMap.getString("downButton.text"));
        downButton.setName("downButton");

        saveButton.setAction(actionMap.get("save")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text"));
        saveButton.setName("saveButton");

        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        buttonPanel.add(saveButton);

        add(scrollPanel);
        add(buttonPanel, BorderLayout.NORTH);

        add(panel);
        setName("tablePane");
    }
    private JTable initTable(org.jdesktop.application.ResourceMap resourceMap) {

        String[] columnNames;
        columnNames = new String[3];

        columnNames[0] = resourceMap.getString("ColRound.text");
        columnNames[1] = resourceMap.getString("ColHome.text");
        columnNames[2] = resourceMap.getString("ColAway.text");
        Object[][] data = {{"", "", ""}};

        JTable table = new JTable(data, columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        table.setFillsViewportHeight(true);
        table.setDragEnabled(true);
        table.setShowVerticalLines(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(10);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        return table;
    }

    /** A very basic printing facility.
     *
     * Printing is done here becaus the table component supports direct
     * printing.
     *
     * @todo Add more sophisticated printing by returning a Printable obejct
     * from this class to a caller.
     */
    public void print() {
        try {
            mainTable.print();
        } catch (java.awt.print.PrinterException e) {
            System.err.format("Cannot print %s%n", e.getMessage());
        }
    }

    @Action
    public void save() {

    }

    @Action
    public void moveUp() {
     //   if (mainTable.)
    }

    @Action
    public void moveDown() {

    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {

    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import scheator.*;
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
    private org.jdesktop.application.ResourceMap resourceMap;
    private javax.swing.ActionMap actionMap;
    private MainController controller;

    public MainTablePanel(MainController controller) {

        this.controller = controller;
        init();

    }

    private void init() {

        javax.swing.JPanel panel = new javax.swing.JPanel();
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();

        this.resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(MainTablePanel.class);
        this.actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(MainTablePanel.class, this);

        mainTable = initTable();

        mainTable.setFillsViewportHeight(true);
        JScrollPane scrollPanel = new JScrollPane(mainTable);

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        upButton.setText(resourceMap.getString("moveUp.text"));
        upButton.setName("moveUp");
        upButton.setAction(actionMap.get("moveUp"));

        downButton.setText(resourceMap.getString("moveDown.text"));
        downButton.setName("moveDown");
        downButton.setAction(actionMap.get("moveDown"));

        saveButton.setText(resourceMap.getString("save.text"));
        saveButton.setName("save");
        saveButton.setAction(actionMap.get("save")); // NOI18N

        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        buttonPanel.add(saveButton);

        add(scrollPanel);
        add(buttonPanel, BorderLayout.NORTH);

        add(panel);
        setName("tablePane");
    }
    private JTable initTable() {

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

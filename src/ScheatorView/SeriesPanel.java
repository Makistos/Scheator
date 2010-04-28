/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import ScheatorModel.*;
import ScheatorController.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.DefaultCellEditor;
import javax.swing.BoxLayout;
import org.jdesktop.application.Action;

/** Panel that includes a table for series in the database and buttons
 *  to manipulate them.
 *
 * @author mep
 */
public class SeriesPanel extends AbstractView {

    SeriesTableModel tableModel;
    JTable seriesTable;

    AbstractController controller;

    SeriesPanel(AbstractController controller, SeriesTableModel tableModel) {
        Integer id = null;
        this.tableModel = tableModel;
        tableModel.addTableModelListener(new TableListener());
        this.controller = controller;
        controller.addView(this);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(SeriesPanel.class);
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(SeriesPanel.class, this);

        //this.setLayout(new FlowLayout(FlowLayout.));

        seriesTable = new JTable(tableModel);

        seriesTable.setDefaultEditor(String.class, new DefaultCellEditor(new JTextField()));

        JPanel contents = new JPanel();
        contents.setLayout(new BoxLayout(contents, BoxLayout.X_AXIS));

        JScrollPane tableScroll = new JScrollPane(seriesTable);
        contents.add(tableScroll);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        contents.add(buttonPanel);
        
        JButton addButton = new JButton();
        addButton.setAction(actionMap.get("add"));
        addButton.setText(resourceMap.getString("addButton.Action.text"));
        buttonPanel.add(addButton);

        JButton delButton = new JButton();
        delButton.setAction(actionMap.get("delete"));
        delButton.setText(resourceMap.getString("delButton.Action.text"));
        buttonPanel.add(delButton);

        add(contents);
        //add(buttonPanel);
    }

    public void saveSeries() {
        tableModel.saveSeries();
    }

    public void undoTeamChanges() {
        // Do nothing, the changes will be deleted when this window closes.
    }

    public void teamWindowClosing() {
        controller.removeView(this);
    }

    @Action
    public void add() {
        tableModel.addRow("");
        validate();
    }

    @Action
    public void delete() {
        tableModel.removeTeam(seriesTable.getSelectedRow());
    }
    
    class TableListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {

            //tableModel.save();
        }
    }


}

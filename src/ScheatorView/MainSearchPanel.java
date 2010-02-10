/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import ScheatorController.MainController;
import org.jdesktop.application.Action;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 *
 * @author mep
 */
public class MainSearchPanel extends AbstractViewPanel {

    private MainController controller;
    private org.jdesktop.application.ResourceMap resourceMap;
    private javax.swing.ActionMap actionMap;
    JComboBox seriesList;
    JComboBox seasonList;
    JButton searchButton = new JButton();
    public MainSearchPanel(MainController controller) {

        this.controller = controller;
        
        String[] seriesNames = {"SM-liiga", "Veikkausliiga", "Valioliiga"};
        String[] seasonNames = {"2008-09", "2009-10"};

        this.resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(MainSearchPanel.class);
        this.actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(MainSearchPanel.class, this);

        javax.swing.JPanel panel = new javax.swing.JPanel();
        JLabel seriesLabel = new JLabel();
        JLabel seasonLabel = new JLabel();

        seriesList = new JComboBox(seriesNames);
        seriesList.setAction(actionMap.get("seriesList"));
        seriesList.setEditable(false);

        seasonList = new JComboBox(seasonNames);
        seasonList.setAction(actionMap.get("seasonList"));
        seasonList.setEditable(false);

        searchButton = new JButton();
        searchButton.setAction(actionMap.get("search"));

        seriesLabel.setText(resourceMap.getString("seriesLabel.text"));
        seasonLabel.setText(resourceMap.getString("seasonLabel.text"));
        searchButton.setText(resourceMap.getString("searchButton.Action.text"));

        add(seriesLabel);
        add(seriesList);
        add(seasonLabel);
        add(seasonList);
        add(searchButton);

        setName("searchPane");
    }

    @Action
    public void search() {
       // controller.searchMainTable();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {

    }

    @Action
    public void seriesList() {
        controller.seriesSelected(seriesList.getSelectedIndex());
    }

    @Action
    public void seasonList() {

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import ScheatorController.MainController;
import org.jdesktop.application.Action;
import java.beans.PropertyChangeEvent;
import javax.swing.*;

/**
 *
 * @author mep
 */
public class MainSearchPanel extends AbstractViewPanel {

    MainController controller;

    public MainSearchPanel(MainController controller) {

        this.controller = controller;
        
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(MainView.class);
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(MainView.class, this);

        String[] seriesNames = {"SM-liiga", "Veikkausliiga", "Valioliiga"};
        String[] seasonNames = {"2008-09", "2009-10"};

        javax.swing.JPanel panel = new javax.swing.JPanel();
        JLabel seriesLabel = new JLabel();
        JLabel seasonLabel = new JLabel();

        JComboBox seriesList = new JComboBox(seriesNames);
        JComboBox seasonList = new JComboBox(seasonNames);
        JButton searchButton = new JButton();

        seriesLabel.setText(resourceMap.getString("seriesLabel.text"));
        seasonLabel.setText(resourceMap.getString("seasonLabel.text"));
        searchButton.setText(resourceMap.getString("searchButton.text"));
        searchButton.setAction(actionMap.get("search"));

        add(seriesLabel);
        add(seriesList);
        add(seasonLabel);
        add(seasonList);
        add(searchButton);

        setName("searchPane");
    }

    @Action
    public void search() {

    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {

    }

}

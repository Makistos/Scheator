/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import ScheatorController.MainController;
import ScheatorModel.*;
import ScheatorDb.*;
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
    SeriesComboBoxModel seriesModel;
    SeasonComboBoxModel seasonModel;
    public Boolean searchEnabled = true;

    public MainSearchPanel(MainController controller) {

        this.controller = controller;
        controller.addView(this);
        
        String[] seriesNames = {"SM-liiga", "Veikkausliiga", "Valioliiga"};
        String[] seasonNames = {"2008-09", "2009-10"};

        this.resourceMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getResourceMap(MainSearchPanel.class);
        this.actionMap = org.jdesktop.application.Application.getInstance(scheator.ScheatorApp.class).getContext().getActionMap(MainSearchPanel.class, this);

        javax.swing.JPanel panel = new javax.swing.JPanel();
        JLabel seriesLabel = new JLabel();
        JLabel seasonLabel = new JLabel();

        seriesModel = new SeriesComboBoxModel();
        seriesList = new JComboBox(seriesModel);
        seriesList.setName("series");
        seriesList.setAction(actionMap.get("seriesList"));
        seriesList.setEditable(false);
//        seriesList.addItemListener(new ComboBoxListener());

        seasonModel = new SeasonComboBoxModel();
        seasonList = new JComboBox(seasonModel);
        seasonList.setName("season");
        seasonList.setAction(actionMap.get("seasonList"));
        seasonList.setEditable(false);
//        seasonList.addItemListener(new ComboBoxListener());
        
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


    public void setSearchEnabled(Boolean searchEnabled) {
        Boolean oldValue = searchEnabled;
        this.searchEnabled = searchEnabled;
        firePropertyChange("searchEnabled", oldValue, this.searchEnabled);
    }

    public Boolean isSearchEnabled() {
        return searchEnabled;
    }

    @Action (enabledProperty = "searchEnabled")
    public void search() {
        //int seasonId = (ScheatorDb.DbObject.Data) seasonList.getSelectedItem().field_id;
       //controller.searchMainTable(seasonId);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {

    }

    @Action
    public void seriesList() {
        //seasonModel.fill(seriesList.getSelectedId());
        //controller.seriesSelected(seriesList.getSelectedIndex());
        Series.Data selected = (Series.Data)seriesModel.getSelectedItem();
        int id = selected.field_id;
        seasonModel.update(id);
    }

    @Action
    public void seasonList() {
        if ((Season.Data)seasonModel.getSelectedItem() != null) {
            setSearchEnabled(true);
        } else {
            setSearchEnabled(false);
        }
    }

    class ComboBoxListener implements ItemListener {
        ComboBoxListener() {

        }
        public void itemStateChanged(ItemEvent e) {
/*            JComboBox cb = (JComboBox) e.getSource();
            String originator = cb.getName();
            if (originator.equals("series")) {
                seasonModel.fill("0");
            }
            else if (originator.equals("season")) {
                // controller.searchSchedule(seriesModel.getSelected(), seasonModel.getSelected());
            }
*/
        }
    }
}

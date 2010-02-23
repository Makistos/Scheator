/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorView;

import ScheatorController.MainController;
import ScheatorModel.*;
import ScheatorDb.*;
import org.jdesktop.application.Action;
import javax.swing.*;
import java.awt.event.*;
import ScheatorDb.*;

/**
 *
 * @author mep
 */
public class MainSearchPanel extends AbstractView {

    private MainController controller;
    private org.jdesktop.application.ResourceMap resourceMap;
    private javax.swing.ActionMap actionMap;
    JComboBox seriesList;
    JComboBox seasonList;
    JButton searchButton = new JButton();
    SeriesComboBoxModel seriesModel;
    SeasonComboBoxModel seasonModel;
    private boolean searchEnabled = false;

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

        seriesModel = new SeriesComboBoxModel(controller);
        seriesList = new JComboBox(seriesModel);
        seriesList.setPrototypeDisplayValue("XXXXXXXXXXXX");
        seriesList.setName("series");
        seriesList.setAction(actionMap.get("seriesList"));
        seriesList.setEditable(false);
//        seriesList.addItemListener(new ComboBoxListener());

        seasonModel = new SeasonComboBoxModel(controller);
        seasonList = new JComboBox(seasonModel);
        seasonList.setPrototypeDisplayValue("XXXXXXXXXXXX");
        seasonList.setName("season");
        seasonList.setAction(actionMap.get("seasonList"));
        seasonList.setEditable(false);
//        seasonList.addItemListener(new ComboBoxListener());
        
        searchButton = new JButton();
        //searchButton.setSize(10, 10);
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


    public void setSearchEnabled(boolean searchEnabled) {
        boolean oldValue = this.searchEnabled;
        this.searchEnabled = searchEnabled;
        firePropertyChange("searchEnabled", oldValue, this.searchEnabled);
    }

    public Boolean isSearchEnabled() {
        return searchEnabled;
    }

    @Action (enabledProperty = "searchEnabled")
    public void search() {
        System.err.println("Searching");
        ScheatorDb.Season.Data data = (ScheatorDb.Season.Data) seasonList.getSelectedItem();
        Integer seasonId = (Integer) data.get("id");
        controller.searchMainTable(seasonId);
    }

    @Action
    public void seriesList() {
        //seasonModel.fill(seriesList.getSelectedId());
        //controller.seriesSelected(seriesList.getSelectedIndex());
        Series.Data selected = (Series.Data)seriesModel.getSelectedItem();
        Integer id = (Integer) selected.get("id");
        System.err.println("id = " + id);
        seasonModel.update(id);
    }

    @Action
    public void seasonList() {
        if ((Season.Data)seasonModel.getSelectedItem() != null) {
            setSearchEnabled(true);
            System.err.println("SELECTED");
        } else {
            setSearchEnabled(false);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItem().getClass().getName();
        
        if (e.getStateChange() == ItemEvent.SELECTED) {

        }

        System.err.println("itemStateChanged source: " + source);

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

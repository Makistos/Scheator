package ScheatorController;

import ScheatorView.*;
import java.util.EventListener;
import java.util.ArrayList;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.AbstractListModel;
import java.awt.event.*;
import java.awt.*;

/**
 * This class provides base level functionality for each controller. This includes the
 * ability to register multiple models and views, propogating model change events to
 * each of the views, and providing a utility function to broadcast model property
 * changes when necessary.
 * @author Robert Eckstein
 */
public abstract class AbstractController implements EventListener {

    //  Vectors that hold a list of the registered models and views for this controller.

    ArrayList<AbstractView> registeredViews;
    MainView mainFrame;
    ArrayList<Object> registeredModels;


    /** Creates a new instance of Controller */
    public AbstractController() {
        registeredViews = new ArrayList<AbstractView>();
        registeredModels = new ArrayList<Object>();
    }

    /**
     * Binds a model to this controller. Once added, the controller will listen for all
     * model property changes and propogate them on to registered views. In addition,
     * it is also responsible for resetting the model properties when a view changes
     * state.
     * @param model The model to be added
     */
    public void addModel(Object model) {
        registeredModels.add(model);
        if(model instanceof AbstractTableModel) {
            AbstractTableModel T = (AbstractTableModel) model;
            T.addTableModelListener(new TableModelListenerImpl());
        }
        if (model instanceof AbstractListModel) {
            AbstractListModel T = (AbstractListModel) model;
            T.addListDataListener(new ListDataListenerImpl());
        }
        if (model instanceof ItemSelectable) {
            ItemSelectable T = (ItemSelectable) model;
            T.addItemListener(new ItemListenerImpl());
        }
    }

    /**
     * Unbinds a model from this controller.
     * @param model The model to be removed
     */
    public void removeModel(Object model) {
        registeredModels.remove(model);
        if(model instanceof AbstractTableModel) {
            AbstractTableModel T = (AbstractTableModel) model;
            T.removeTableModelListener(new TableModelListenerImpl());
        }
        if (model instanceof AbstractListModel) {
            AbstractListModel T = (AbstractListModel) model;
            T.removeListDataListener(new ListDataListenerImpl());
        }    
        if (model instanceof ItemSelectable) {
            System.err.println("Item is selectable");
            ItemSelectable T = (ItemSelectable) model;
            T.addItemListener(new ItemListenerImpl());
        }
    }


    /**
     * Binds a view to this controller. The controller will propogate all model property
     * changes to each view for consideration.
     * @param view The view to be added
     */
    public void addView(AbstractView view) {
        registeredViews.add(view);
    }

    /**
     * Unbinds a view from this controller.
     * @param view The view to be removed
     */
    public void removeView(AbstractView view) {
        registeredViews.remove(view);
    }

    public void addMainFrame(MainView frame) {
        this.mainFrame = frame;
    }

    class TableModelListenerImpl implements TableModelListener {
        public void tableChanged(TableModelEvent e) {
            for (AbstractView view: registeredViews) {
                view.mainTableChanged(e);
            }
            mainFrame.mainTableChanged(e);
        }
    }

    class ListDataListenerImpl implements ListDataListener {
        public void contentsChanged(ListDataEvent e) {
            for (AbstractView view: registeredViews) {
                view.comboBoxEvent(e);
            }
            System.err.println("contentsChanged()");
            mainFrame.comboBoxEvent(e);
        }

        public void intervalRemoved(ListDataEvent e) {
            for (AbstractView view: registeredViews) {
                view.comboBoxEvent(e);
            }
            mainFrame.comboBoxEvent(e);
        }

        public void intervalAdded(ListDataEvent e) {
            for (AbstractView view: registeredViews) {
                view.comboBoxEvent(e);
            }
            mainFrame.comboBoxEvent(e);
        }

    }



    class ItemListenerImpl implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            for (AbstractView view: registeredViews) {
                view.itemStateChanged(e);
            }
            System.err.println("itemStateChanged");
            mainFrame.itemStateChanged(e);
        }
    }
}

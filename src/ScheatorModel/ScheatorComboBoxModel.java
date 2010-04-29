/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorModel;

import javax.swing.AbstractListModel;
import ScheatorDb.*;
import javax.swing.ComboBoxModel;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/** Provides base operability for combo boxes used in the app.
 *
 * The contents are read from the database into a LinkedHashMap. The only thing
 * a class really needs to do is to read the data using the db classes into the
 * list in the constructor. See SeriesComboBoxModel class for an example.
 * 
 * @author mep
 */
public abstract class ScheatorComboBoxModel extends AbstractListModel implements ComboBoxModel, ItemSelectable {
    DbObject provider;
    LinkedHashMap<Integer, DbObject.Data> list;
    DbObject.Data selection = null;

    /** Returns item in collection at certain location
     *
     * @note This is a horribly slow implementation, but it seems LinkedHashMap
     * does not allow finding a specific index point with direct indexing.
     *
     * @param index
     * @return Series.SeriesData located in the specific index, or null if index
     * not found.
     */
    public Object getElementAt(int index) {
        DbObject.Data retval = null;
        int i = 1;
        for(Iterator it=list.values().iterator(); it.hasNext();) {
            retval = (DbObject.Data) it.next();
            if (i == index) {
                break;
            }
            i++;
        }
        return retval;
    }

    /** Returns size of the list
     *
     * @return Size of the list.
     */
    public int getSize() {
        return list.size();
    }

    /** Sets the selected item.
     *
     * @param anItem Item to be set.
     */
    public void setSelectedItem(Object anItem) {
        selection =  (DbObject.Data) anItem;

    }

    /** Returns the currectly selected item.
     *
     * @return Currently selected item.
     */
    public Object getSelectedItem() {
        return selection;
    }

    /* ItemSelectable */

    //protected EventListenerList listenerList = new EventListenerList();

    public Object[] getSelectedObjects() {
        return new String[] { "a", "b", "c" };
    }

    /** Returns the index of an item based on its' name
     *
     * @param name Name to search for.
     * @return Item's index in the list.
     */
    public Integer getIndexByName(String name) {
        Integer retval = 0;
        Boolean found = false;
        for(Iterator it=list.values().iterator(); it.hasNext();) {
            retval++;
            DbObject.Data data = (DbObject.Data) it.next();
            System.err.println("Series: " + data.get("name"));
            if (data.get("name").equals(name)) {
                found = true;
                break;
            }
        }

        if (found == true) {
            return retval;
        } else {
            return null;
        }
    }

    public void addItemListener(ItemListener l) {
        listenerList.add(ItemListener.class, l);
    }

    public void removeItemListener(ItemListener l) {
        listenerList.remove(ItemListener.class, l);
    }
    
    void fireItemEvent(Object item, boolean sel) {
        ItemEvent evt = new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, item,
        sel ? ItemEvent.SELECTED : ItemEvent.DESELECTED);

        Object[] listeners = listenerList.getListenerList();

        for (int i = 0; i < listeners.length - 2; i += 2) {
            if (listeners[i] == ItemListener.class) {
                ((ItemListener) listeners[i + 1]).itemStateChanged(evt);
            }
        }
    }
}

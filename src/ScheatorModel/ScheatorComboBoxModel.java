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

/** Provides base operability for combo boxes used in the app.
 *
 * The contents are read from the database into a LinkedHashMap. The only thing
 * a class really needs to do is to read the data using the db classes into the
 * list in the constructor. See SeriesComboBoxModel class for an example.
 * 
 * @author mep
 */
public abstract class ScheatorComboBoxModel extends AbstractListModel implements ComboBoxModel {
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
        int i = 0;
        for(Iterator it=list.values().iterator(); it.hasNext();) {
            if (i == index) {
                retval = (DbObject.Data) it.next();
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

}

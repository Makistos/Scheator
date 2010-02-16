/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorDb;
import java.util.LinkedHashMap;

/**
 *
 * @author mep
 */
public abstract class DbObject<Object, T> {
    LinkedHashMap<Object, T> list;
    AbstractDb db;
    
    public LinkedHashMap<Object, T> getList() {
        return list;
    }

    public T getItemByIndex(Object index) {
        return list.get(index);
    }
}

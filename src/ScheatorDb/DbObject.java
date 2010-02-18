package ScheatorDb;
import java.util.LinkedHashMap;

/** An abstract definition of a database object. 
 * 
 * @brief An object can be the contents of an entire table, part of a table or a
 * collection of fields from different tables or any other combination that
 * represents a mapping between the database and the application.
 *
 * @author mep
 */
public abstract class DbObject<Object, T> {
    LinkedHashMap<Object, T> list;
    AbstractDb db;
    int currentId;

    /** Fetches one or more items from the database to fill the data fields
     * in this object.
     *
     * @param key Id for item(s) to fetch.
     */
    public abstract void fetch(int key);

    /** Returns the LinkedHashMap containing the data.
     *
     * @return Data items.
     */
    public LinkedHashMap<Object, T> getList() {
        return list;
    }

    /** Returns items by given index.
     *
     * @param index Item index.
     * @return Item having the selected index.
     */
    public T getItemByIndex(Object index) {
        return list.get(index);
    }

    /** The actual data for this class.
     *
     * @brief This class holds the actual data retrieved from the database. Each
     * class needs to define this themselves. Each field retrieved directly from
     * the database needs to have the "field_" prefix.
     */
    abstract public class Data {

        /** Id in the database. */
        public int field_id;
        /** Season "name", e.g. "2007-08". */
        public String field_name;
        
        @Override
        public String toString() {
            return field_name;
        }

    }
}

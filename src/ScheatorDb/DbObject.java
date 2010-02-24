package ScheatorDb;
import java.util.LinkedHashMap;
import java.lang.reflect.Field;

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
    Integer currentId;

    enum FieldState {SAVED, NEW, CHANGED}

    /** Fetches one or more items from the database to fill the data fields
     * in this object.
     *
     * @param key Id for item(s) to fetch.
     */
    public abstract void fetch(Integer key);

    /** Saves the contents to the database.
     *
     * Empty by default - we can quite possibly have objects that we do
     * not need to save.
     * 
     */
    public void save() {

    }

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

        FieldState state = FieldState.SAVED;

        /** Sets a field in the object to the given value.
         *
         * This function uses reflection to determine the field where the value
         * will be set.
         * 
         * @param field Name (minus field_) of field to set.
         * @param value Value used.
         */
        public void set(String field, Object value) {
            String name = "field_".concat(field);
            try {
                Class c = Class.forName(this.toString());
                Field f = c.getField(name);
                f.set(this, value);
            } catch (Exception e) {
                System.err.println("Field does not exist: " + name + "(" + e.getMessage() + ")");
            }
            if (state == FieldState.SAVED) {
                state = FieldState.CHANGED;
            }
        }

        /** Returns the value of a field.
         *
         * This method uses reflection to determine the field where the value
         * will be retrieved.
         * 
         * @param field Field name.
         * @return Value of the field.
         */
        public Object get(String field) {
            String name = "field_".concat(field);
            Object retval = null;
            try {
                Class c = Class.forName(this.toString());
                Field f = c.getDeclaredField(name);
                retval = (Object)f.get(this);
            } catch (Exception e) {
                System.err.println("Field does not exist: " + name + " (" + e.getMessage() + ")");
            }
            return retval;
        }

    }

    /** Dummy class needed to separate string fields from reference strings
     * which should not be escaped.
     */
    public class FieldReference {
        String value;

        FieldReference(String val) {
            this.value = val;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}

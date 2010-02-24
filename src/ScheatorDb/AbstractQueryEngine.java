package ScheatorDb;

import java.util.*;

/** An interface of a query engine.
 *
 * @brief This is an abstraction of a query engine. A query engine can create
 * queries for various data manipulation tasks. Implementing classes will
 * each provide an interface to a different datasource.
 *
 * @author mep
 */
interface AbstractQueryEngine {
    /** Returns a query to retrieve fields from the database.
     *
     * @param table Table names.
     * @param fields Fields to retrieve. It's up to the implementation whether
     * this is mandatory or not.
     * @param idFields Fields used to make the query. It's up to the implementation
     * whether this is mandatory or not.
     * @param ids Values for the ifFields. Must be in same order. It's up to the
     * implementation  whether this is mandatory or not.
     * @return Query to run.
     */
    abstract String getItems(String[] table, String[] fields,
            HashMap<String, Object> idFields,
            String[] orderBy);

    /** Returns a query to add items to the database.
     *
     * @param entity The entity to which to add items (typically, a database
     * table).
     * @param toAdd The item to add.
     * @return Query to run.
     */
    abstract String addItem(String entity, HashMap<String, Object> toAdd);

    /** Returns a query to update values of an item in the database.
     * 
     * @param entity The entity in which to update the item (typically, a 
     * database table).
     * @param toUpdate Fields to update.
     * @return Query to run.
     */
    abstract String updateItem(String entity, HashMap<String, Object> toUpdate,
            HashMap<String, Object> idFields);

    /** Returns a query to delete one or more items from the database.
     *
     * @param entity The entity from which to delete items (typically, a
     * database table).
     * @param idField The id fields used to identify the items.
     * @param id The values of the id fields.
     * @return Query to run.
     */
    abstract String deleteItems(String entity, HashMap<String, Object> idField);
}

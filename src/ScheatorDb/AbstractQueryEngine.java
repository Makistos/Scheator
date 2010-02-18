/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorDb;

/**
 *
 * @author mep
 */
interface AbstractQueryEngine<T> {
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
    abstract String getItems(String[] table, String[] fields, String[] idFields, 
            String[] ids, String[] orderBy);

    /** Returns a query to add items to the database.
     *
     * @param entity The entity to which to add items (typically, a database
     * table).
     * @param toAdd The item to add.
     * @return Query to run.
     */
    abstract String addItem(String entity, Object toAdd);

    /** Returns a query to delete one or more items from the database.
     *
     * @param entity The entity from which to delete items (typically, a
     * database table).
     * @param idField The id fields used to identify the items.
     * @param id The values of the id fields.
     * @return Query to run.
     */
    abstract String deleteItems(String entity, String[] idField, String[] id);
}

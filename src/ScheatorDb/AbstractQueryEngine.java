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
    abstract String getItems(String[] table, String[] fields, String[] idFields, String[] ids);
    abstract String addItem(String entity, Object toAdd);
    abstract String deleteItems(String entity, String[] idField, String[] id);
}

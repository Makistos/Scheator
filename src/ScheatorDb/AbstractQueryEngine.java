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
    abstract String getItems(String[] table, String[] idFields, String[] ids);
    abstract String addItems(String entity, Object toAdd);
    abstract String deleteItems(String entity, String idField, String id);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorDb;

import java.sql.*;
import java.lang.reflect.Field;

/**
 *
 * @author mep
 */
class SqlQueryEngine implements AbstractQueryEngine {

    /** Returns an SQL query for retrieving information from a database.
     *
     * @todo get and delete do not add escape characters for String type fields.
     * 
     * @param table Table names.
     * @param fields Field names. If this is null, * is used.
     * @param idFields Fields used to query the database. If this is null, every
     * row is returned.
     * @param ids Values for the id fields. Must be in the same order as the
     * idFields. If this is null, every row is returned.
     * @return SQL query string.
     */
    @Override
    public String getItems(String[] table, String[] fields, String[] idFields, String[] ids) {
        StringBuilder sb = new StringBuilder();

        if (fields == null) {
            sb.append("SELECT * FROM ");
        } else {
            sb.append("SELECT ");
            for(int i=0;i<fields.length-1;i++) {
                 sb.append(fields[i]);
                 if (i < fields.length-1) {
                     sb.append(", ");
                 }
            }
            sb.append(" FROM ");
        }
        for(int i=0;i<table.length;i++) {
            sb.append(table[i]);
            if (i<table.length-1) {
                sb.append(",");
            }
        }
        sb.append(createWhereClause(idFields, ids));

        System.err.println("SqlQueryEngine.getItems() returns " + sb.toString());
        return sb.toString();
    }

    /** Builds an SQL query string to add an item to the database.
     *
     * @param table Table name.
     * @param toAdd Object to add.
     * @return SQL query.
     */
    @Override
    public String addItem(String table, Object toAdd) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbFields = new StringBuilder();
        StringBuilder sbValues = new StringBuilder();
        
        sb.append("INSERT INTO ");
        sb.append(table);
        sb.append(" VALUES ");

        sbFields.append("(");
        sbValues.append("(");
        
        for (Field f: toAdd.getClass().getDeclaredFields()) {
            String fieldName = f.getName();
            if (fieldName.startsWith("field_")) {

                String type = f.getClass().getName();
                String value = new String();
                sbFields.append(fieldName);
                if (type.equals("String")) {
                    sbValues.append("'");
                    try {
                        sbValues.append(f.get(value));
                    } catch (Exception e) {
                        System.err.println("No such field!");
                    }
                    sbValues.append("'");
                } else {
                    try {
                        sbValues.append(f.get(value));
                    } catch (Exception e) {
                        System.err.println("No such field!" + e.getMessage());
                    }
                }
                sbFields.append(",");
                sbValues.append(",");
            }
        }
        // Remove extra commas and add missing closing parentheses
        sb.append(sbFields.toString().substring(0, sbFields.toString().length()-1));
        sb.append(")");
        sb.append(sbValues.toString().substring(0, sbValues.toString().length()-1));
        sb.append(")");

        System.err.println("addItems returns " + sb.toString());
        return sb.toString();
    }

    /** Builds an SQL query to delete items from the database.
     *
     * @param table Table name. Mandatory.
     * @param idField Field names for the query. If this is null, contents of the
     * entire table is cleared.
     * @param id Id values for the query. Must be in the same order as the
     * fields in idField. If this is null, contents of the entire table is cleared.
     * @return SQL query string.
     */
    @Override
    public String deleteItems(String table, String[] idFields, String[] ids) {
        StringBuilder sb = new StringBuilder();

        sb.append("DELETE FROM ");
        sb.append(table);
        sb.append(createWhereClause(idFields, ids));

        return sb.toString();
    }

    /** Builds the WHERE part of an SQL query.
     *
     * @param idFields Fields of the query.
     * @param ids Values of the query.
     * @return  WHERE part of an SQL query.
     */
    private String createWhereClause(String[] idFields, String[] ids) {
        StringBuilder sb = new StringBuilder();

        if (idFields != null && ids != null) {
            sb.append(" WHERE ");
            for(int i=0;i<idFields.length-1;i++) {
                sb.append(idFields[i] + " = ");
                sb.append(ids[i]);
                if (i < ids.length-1) {
                    sb.append(" AND ");
                }
            }
        }
        return sb.toString();
    }
}
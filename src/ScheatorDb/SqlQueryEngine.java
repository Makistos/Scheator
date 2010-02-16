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

    @Override
    public String getItems(String[] table, String[] idFields, String[] ids) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM ");
        for(int i=0;i<table.length-1;i++) {
            sb.append(table);
            if (i<table.length-1) {
                sb.append(",");
            }
        }
        int i = 0;
        if (ids.length > 0) {
            sb.append(" WHERE ");
            sb.append(idFields + " = ");
            sb.append(ids);
            if (i < ids.length-1) {
                sb.append(" AND ");
            }
            i++;
        }

        System.err.println("getItems returns" + sb.toString());
        return sb.toString();
    }

    @Override
    public String addItems(String table, Object toAdd) {
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
                        System.err.println("No such field!");
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

    @Override
    public String deleteItems(String table, String idField, String id) {
        StringBuilder sb = new StringBuilder();

        sb.append("DELETE FROM ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(idField);
        sb.append(" = ");
        sb.append(id);

        return sb.toString();
    }

}

package ScheatorDb;

import java.lang.reflect.Field;

/** A class for creating SQL queries.
 *
 * @todo getItems() and deleteItems do not support varchar (=String) type
 * search fields as the values should be enclosed in single quotes. This is done
 * in addItem() but since I don't need this feature it has not been implemented.
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
    public String getItems(String[] table, String[] fields, Object[] idFields,
            String[] ids, String[] orderBy) {
        StringBuilder sb = new StringBuilder();

        if (fields == null) {
            sb.append("SELECT * FROM ");
        } else {
            sb.append("SELECT ");
            for(int i=0;i<fields.length;i++) {
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
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }
        sb.append(createWhereClause(idFields, ids));

        sb.append(" ");
        
        sb.append(createOrderByClause(orderBy));

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
    public String addItem(String table, Object[] toAdd) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbFields = new StringBuilder();
        StringBuilder sbValues = new StringBuilder();
        
        sb.append("INSERT INTO ");
        sb.append(table);
        sb.append(" VALUES ");

        sbFields.append("(");
        sbValues.append("(");

        for (int i=0;i<toAdd.length;i++) {
            for (Field f: toAdd[i].getClass().getDeclaredFields()) {
                String fieldName = f.getName();
                if (fieldName.startsWith("field_")) {
                    sbFields.append(fieldName);
                    sbValues.append(formatValue(f));
                    sbFields.append(",");
                    sbValues.append(",");
                }
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

    /** Creates an SQL query for updating a row's values in the database.
     *
     * @param entity Table name.
     * @param toUpdate Fields and values to update. All of the fields will be
     * updated, so each field must have a meaningful value!
     * @param idFields Fields used to identify the row(s).
     * @param ids Id values for id fields. Values must be in the same order as
     * the fields.
     * @return
     */
    @Override
    public String updateItem(String entity, Object[] toUpdate, Object[] idFields,
            String[] ids) {
        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE ");
        sb.append(entity);
        sb.append("SET ");

        for(int i=0;i<toUpdate.length;i++) {
            for (Field f: toUpdate[i].getClass().getDeclaredFields()) {
                String fieldName = f.getName();
                if (fieldName.startsWith("field_")) {
                    sb.append(fieldName);
                    sb.append("=");
                    sb.append(formatValue(f));
                    sb.append(",");
                }
            }
        }

        /* Remove last comma */
        sb.deleteCharAt(sb.toString().length()-1);

        /* Add where clause */
        sb.append(createWhereClause(idFields, ids));
        
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
    private String createWhereClause(Object[] idFields, String[] ids) {

        if (idFields != null) {
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<idFields.length;i++) {
                for (Field f: idFields[i].getClass().getDeclaredFields()) {
                    String fieldName = f.getName();
                    if (fieldName.contains("field_")) {
                        sb.append(fieldName);
                        sb.append("=");
                        sb.append(formatValue(f));
                        if (i<idFields.length-1) {
                            sb.append(" AND ");
                        }
                    }
                }
            }
            return sb.toString();
        } else {
            return " ";
        }
    }

    /** Creates the order by clause part of an sql query.
     *
     * @param list Field list for the clause.
     * @return String containing the ORDER BY clause.
     */
    private String createOrderByClause(String[] list) {
        StringBuilder sb = new StringBuilder();
        
        if (list != null) {
            sb.append("ORDER BY ");
            for (int i =0;i<list.length;i++) {
                sb.append(list[i]);
                if (i < list.length-1) {
                    sb.append(", ");
                }
            }
        }
        
        return sb.toString();
    }

    /** Formats a value for an sql query.
     *
     * This function assumes that the field type is equivalent to the field
     * in the database. In practice this means that this field support two
     * types of fields: string (varchar) and numeric.
     *
     * A string type field will be escaped with single parenthesis (') because
     * SQL databases expect that. Numbers are returned as-is.
     *
     * If the field is null, a string containing "NULL" is returned instead.
     * 
     * @param f The field where the value is.
     * @return Formatted value.
     */
    String formatValue(Field f) {
        String retval = null;
        String type = f.getClass().getName();
        if (type.equals("String")) {
            try {
                String value = (String)f.get(f);
                if (value != null) {
                    retval = "'" + value + "'";
                } else {
                    retval = "NULL";
                }
            } catch (Exception e) {
                System.err.println("No such field: " + e.getMessage());
            }
        } else if (type.equals("Integer")) {
            try {
                Integer value = (Integer)f.get(f);
                if (value != null) {
                    retval = String.valueOf(value);
                } else {
                    retval = "NULL";
                }
            } catch (Exception e) {
                System.err.println("No such field: " + e.getMessage());
            }
        } else {
            try {
                String value = (String) f.get(f);
                if (value != null) {
                    retval = value;
                } else {
                    retval = "NULL";
                }
            } catch (Exception e) {
                System.err.println("No such field: " + e.getMessage());
            }
        }
        return retval;
    }
}

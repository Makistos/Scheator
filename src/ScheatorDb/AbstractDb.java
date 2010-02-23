package ScheatorDb;

import java.sql.*;

/** An abstraction of a database connector.
 *
 * @author mep
 */
public abstract class AbstractDb {

    /** Database connection object. */
    Connection con = null;

    /** Query engine object. */
    AbstractQueryEngine qe;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorDb;

import java.sql.*;
import ScheatorModel.*;

/** An abstraction of a database connector.
 *
 * @author mep
 */
public abstract class AbstractDb {
        
    Connection con = null;

    AbstractQueryEngine qe;
}

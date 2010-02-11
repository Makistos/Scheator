/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorController;

import javax.swing.*;

/**
 *
 * @author mep
 */
public class MainController extends AbstractController {

    public static final String SERIES_LIST = "SERIES_LIST";
    public static final String SEASON_LIST = "SEASON_LIST";
    public static final String SCHEDULE_DATA = "SCHEDULE_DATA";


    public void seriesSelected(Integer index) {

    }

    public void seasonSelected(Integer index) {
        
    }

    /** Saves the schedule list.
     *
     * A JTable is received for performance reasons. The only way to abstract
     * this would be to deliver an array list, but creating that would only
     * be an extra step since that will then still have to split.
     *
     * @param table
     */
    public void scheduleEdited() {

    }

    public void moveMatch(Integer startPos, Integer endPos) {

    }
}

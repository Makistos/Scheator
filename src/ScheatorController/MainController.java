/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ScheatorController;

import java.lang.reflect.*;

/**
 *
 * @author mep
 */
public class MainController extends AbstractController {

    public static final String SERIES_LIST = "SERIES_LIST";
    public static final String SEASON_LIST = "SEASON_LIST";
    public static final String SCHEDULE_DATA = "SCHEDULE_DATA";
    private static final String MAINTABLE_MODEL_SEARCH = "MainTableModel";

    public void searchMainTable(int seasonId) {
        System.err.println("searchMainTable()");
        for (Object model: registeredModels) {
            Class c = model.getClass();
            System.err.println("Class = " + c.getName());
            if (c.getName().equals("ScheatorModel.MainTableModel")) {
                System.err.println("Model found");
                Class parTypes[] = new Class[1];
                parTypes[0] = Integer.TYPE;
                try {
                    Method m = c.getMethod("update", parTypes);
                    Object argList[] = new Object[1];
                    argList[0] = new Integer(seasonId);
                    m.invoke(model, argList);
                } catch (Throwable e) {
                    //System.err.println("Fail " + e.toString());
                }
            }
        }
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

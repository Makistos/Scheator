package ScheatorController;

import java.lang.reflect.*;
import java.util.*;
import ScheatorDb.*;
import ScheatorModel.Scheduler;

/** Main application controller.
 *
 * @author mep
 */
public class MainController extends AbstractController {

    public static final String SERIES_LIST = "SERIES_LIST";
    public static final String SEASON_LIST = "SEASON_LIST";
    public static final String SCHEDULE_DATA = "SCHEDULE_DATA";

    /** Main table model class name. */
    private static final String MAINTABLE_MODEL_NAME = "ScheatorModel.MainTableModel";
    /** Method name for updating main table contents. */
    private static final String MAINTABLE_UPDATE = "update";
    /** Method name for saving main table contents. */
    private static final String MAINTABLE_SAVE = "save";

    private static final String SAVE_TEAMS = "saveTeams";
    
    /** Event happened that require filling the in the main table.
     *
     * @param seasonId Database id for the season to be used to fill in the
     * table.
     */
    public void searchMainTable(int seasonId) {
        //System.err.println("searchMainTable()");
        for (Object model: registeredModels) {
            Class c = model.getClass();
            //System.err.println("Class = " + c.getName());
            if (c.getName().equals(MAINTABLE_MODEL_NAME)) {
                System.err.println("Model found");
                Class parTypes[] = new Class[1];
                parTypes[0] = Integer.TYPE;
                try {
                    Method m = c.getMethod(MAINTABLE_UPDATE, parTypes);
                    Object argList[] = new Object[1];
                    argList[0] = new Integer(seasonId);
                    m.invoke(model, argList);
                } catch (Throwable e) {
                    //System.err.println("Fail " + e.toString());
                }
            }
        }
    }


    public void saveSchedule() {
        for (Object model: registeredModels) {
            Class c = model.getClass();
            if (c.getName().equals(MAINTABLE_MODEL_NAME)) {
                Class parTypes[] = null;
                try {
                    Method m = c.getMethod(MAINTABLE_SAVE, parTypes);
                    Object argList[] = null;
                    m.invoke(model, argList);
                } catch (Throwable e) {

                }
            }
        }
    }

    public void saveTeams() {
        for (Object model: registeredModels) {
            Class c = model.getClass();
            Class parTypes[] = null;
            try {
                Method m = c.getMethod(SAVE_TEAMS, parTypes);
                Object argList[] = null;
                m.invoke(model, argList);
            } catch (Throwable e) {
                // Do nothing
            }
        }
    }

    public void scheduleEdited() {

    }

    public void moveMatch(Integer startPos, Integer endPos) {

    }

    public void generateSchedule(String name, Series.Data series, Teams teamList) {
        Integer seriesId = (Integer) series.get("id");
        Scheduler schedule = new Scheduler(seriesId, teamList);
        Matches matches = schedule.get();

        matches.print();
        
        matches.save();
    }
}

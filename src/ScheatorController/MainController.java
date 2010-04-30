package ScheatorController;

import java.lang.reflect.*;
import ScheatorDb.*;
import ScheatorModel.Scheduler;
import java.util.*;

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
    private static final String SERIES_SAVED = "seriesSaved";
    
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

    /** Creates the season and generates the schedule
     *
     *  It is completely possible to rerun the schedule for the same season.
     *  This will just add new matches to that schedule.
     *
     * @todo If the same schedule is recreatead, set round info such that
     * it will be one larger than the largest value existing and invert
     * the team order for this season. This will allow the user to create
     * round robin series with more than one round.
     * 
     * @param seasonName Name of season to generate.
     * @param series Series for which the season belongs to.
     * @param teamList List of teams included in teh schedule.
     */
    public void generateSchedule(String seasonName, Series.Data series, Teams teamList) {
        Integer seriesId = (Integer) series.get("id");
        Season season = new Season(seriesId);
        Integer seasonId;

        if (season.getItemByName(seasonName) == null) {
            // No season by this name exists for this series.
            season.addNew(seriesId, seasonName);
            season.save();
        }
        
        Season.Data data = (Season.Data) season.getItemByName(seasonName);
        seasonId = (Integer)data.get("id");
        Scheduler schedule = new Scheduler(seasonId, teamList);
        Matches matches = schedule.get();

        matches.print();
        
        matches.save();
    }

    /** Called when the series list has changed.
     * 
     */
    public void seriesSaved() {
        for (Object model: registeredModels) {
            Class c = model.getClass();
            Class parTypes[] = null;
            try {
                Method m = c.getMethod(SERIES_SAVED, parTypes);
                System.err.println("Applying " + SERIES_SAVED + "()");
                Object argList[] = null;
                m.invoke(model, argList);
            } catch (Throwable e) {
                // Do nothing
            }
        }
    }

    /** Deletes a schedule and associated matches from the database.
     *
     * @param seasonId ID of schedule/season to delete.
     */
    public void deleteSchedule(Integer seasonId) {

        /* Delete matches */
        Matches matches = new Matches(seasonId);
        matches.deleteAll();

        /* Delete season */
        Season seasons = new Season();
        seasons.fetch(null);
        seasons.delete(seasonId);

        /* Run the sql queries to actually delete the items */
        matches.save();
        seasons.save();

        seriesSaved();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scheator;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 *
 * @author mep
 */
public class ScheatorApp extends SingleFrameApplication {

    @Override protected void startup() {
        show(new ScheatorMainView(this));
    }

    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of SwingApp
     */
    public static ScheatorApp getApplication() {
        return Application.getInstance(ScheatorApp.class);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(ScheatorApp.class, args);
    }

}

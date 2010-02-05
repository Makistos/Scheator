/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scheator;

import ScheatorView.MainView;
import ScheatorController.MainController;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import java.awt.*;

/**
 *
 * @author mep
 */
public class ScheatorApp extends SingleFrameApplication {

    @Override protected void startup() {

        MainController controller = new MainController();

        
        show(new MainView(this, controller));
    }

    @Override protected void configureWindow(java.awt.Window root) {
        root.setMinimumSize(new Dimension(640,480));
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

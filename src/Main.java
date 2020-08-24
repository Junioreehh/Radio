/**
 * Written by Jesper Riekkola 2020-01-11
 * dv17jra Jesper.riekkola@hotmail.com
 */

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String [] args) {

        SRController SRController = new SRController();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    SRController.swingWork();
                });
            }
        };
        timer.scheduleAtFixedRate(task,60*60*1000,60*60*1000);
    }
}

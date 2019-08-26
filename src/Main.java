import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String [] args) {
        SRController SRController = new SRController();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                SRController.updateTable();
            }
        };
        timer.scheduleAtFixedRate(task,3*1000,3*1000);
    }
}

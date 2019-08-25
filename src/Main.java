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
                System.out.println(SRController.getGui().getSelectedChannel());
            }
        };

        timer.scheduleAtFixedRate(task,2*1000,2*1000);
    }
}

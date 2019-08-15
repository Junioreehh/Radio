import javax.swing.*;
import java.awt.*;

public class SRRadioMenuBar extends JMenuBar {
    private JMenuItem update;

    /**
     * Constructs a SRRadioMenuBar with "About" and "How to" menuItems
     */
    public SRRadioMenuBar() {
        JMenu options = new JMenu("Alternativ");
        JMenuItem about = new JMenuItem("Om");
        JMenuItem howTo = new JMenuItem("Användning");
        update = new JMenuItem("Uppdatera");
        about.addActionListener(e -> {
            JFrame aboutFrame = new JFrame("About");
            aboutFrame.setResizable(false);
            aboutFrame.setLocationRelativeTo(null);
            aboutFrame.setLayout(new BorderLayout());
            aboutFrame.setSize(400,100);
            aboutFrame.setDefaultCloseOperation(
                    WindowConstants.DISPOSE_ON_CLOSE);
            JTextArea text = new JTextArea("Skapad av Jesper Riekkola " +
                    "2019-01-30\nEtt program för att hämta kanaler och " +
                    "sändingar från Sveriges Radio\nFör kontakt maila" +
                    " dv17jra@cs.umu.se");
            text.setEditable(false);
            aboutFrame.add(text);
            aboutFrame.setVisible(true);
        });
        
        howTo.addActionListener(e -> {
            JFrame howToFrame = new JFrame("How To");
            howToFrame.setLocationRelativeTo(null);
            howToFrame.setLayout(new BorderLayout());
            howToFrame.setResizable(false);
            howToFrame.setSize(400,110);
            howToFrame.setDefaultCloseOperation(WindowConstants.
                    DISPOSE_ON_CLOSE);
            JTextArea text = new JTextArea("Välj kanal från menyn på " +
                    "högersidan\nProgrammet uppdaterar tabellen automatiskt" +
                    " en gång i timmen\nFör att uppdatera själv tryck på" +
                    " knappen\nFör att se info om ett program så klicka" +
                    " på det i tabellen");
            text.setEditable(false);
            howToFrame.add(text);
            howToFrame.setVisible(true);
        });
        
        options.add(about);
        options.add(howTo);
        this.add(options);
        this.setVisible(true);
    }
    
    Public JMenuItem getUpdate() {return update;}
    
}

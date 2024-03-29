/**
 * Written by Jesper Riekkola 2020-01-11
 * dv17jra Jesper.riekkola@hotmail.com
 */

import javax.swing.*;
import java.awt.*;

public class SRRadioMenuBar extends JMenuBar {
    private JMenuItem update;

    /**
     * Constructs a SRRadioMenuBar with
     * "About", "Update" and "How to" menuItems
     */
    public SRRadioMenuBar() {
        update = new JMenuItem("Uppdatera");
        JMenu options = new JMenu("Alternativ");
        JMenuItem about = new JMenuItem("Om");
        JMenuItem howTo = new JMenuItem("Användning");


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
                    " en gång i timmen\nFör att uppdatera själv gå till" +
                    " \"Alternativ och klicka på \"Uppdatera\"\nFör att se " +
                    "info om ett program så klicka på det i tabellen");
            text.setEditable(false);
            howToFrame.add(text);
            howToFrame.setVisible(true);
        });
        
        options.add(about);
        options.add(howTo);
        options.add(update);
        this.add(options);
        this.setVisible(true);
    }
    
     /**
     * Returns the "Uppdatera" JMenuItem
     * @return A JMenuItem
     */
    public JMenuItem getUpdate() {
        return update;
    }
    
}

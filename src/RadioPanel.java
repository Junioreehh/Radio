import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RadioPanel extends JPanel {
    private JComboBox<String> channels;
    private JTextArea description;
    private JLabel image;
    private JButton update;

    /**
     * Constructs a Radiopanel
     * @param channels Channels to show in JComboBox
     */
    public RadioPanel(ArrayList<String> channels) {
        String[] channelArr = {" "};
        channelArr = channels.toArray(channelArr);
        description = new JTextArea();
        description.setEditable(false);
        description.setPreferredSize(new Dimension(512,200));
        description.setBackground(Color.LIGHT_GRAY);
        description.setLineWrap(true);
        this.channels = new JComboBox<>(channelArr);
        this.channels.setPreferredSize(new Dimension(220,40));
        this.channels.setBackground(Color.GRAY);
        add(this.channels);
        add(description);
        
        try{
            BufferedImage SRImage = ImageIO.read(new URL("https://static-cdn" +
                    ".sr.se/sida/images/3113/2216702_512_512.jpg?preset=" +
                    "socialmedia-share-image"));
            image = new JLabel(new ImageIcon(SRImage));
        }catch(IOException e){
            image = new JLabel();
            image.setBackground(Color.BLACK);
        }
        
        add(image);
        setPreferredSize(new Dimension(512,800));
        this.channels.setVisible(true);
        setVisible(true);
    }

    /**
     * Returns the Jlabel on which the images are shown
     * @return A Jlabel
     */
    public JLabel getJlabel() {
        return image;
    }

    /**
     * Sets the text in the description box
     * @param description A String
     */
    public void setDescription(String description) {
        this.description.setText(description);
    }

    /**
     * Returns the name of the currently selected in the JComboBox
     * @return A String
     */
    public String getChannel() {
        return channels.getSelectedItem().toString();
    }

    /**
     * Returns the JComboBox
     * @return A JComboBox
     */
    public JComboBox<String> getComboBox() {
        return channels;
    }
}

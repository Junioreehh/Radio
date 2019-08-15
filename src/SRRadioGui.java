import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SRRadioGui extends JFrame {
    private JPanel leftPanel;
    private RadioPanel rightPanel;
    private JScrollPane table;

    /**
     * Constructs a Gui to show radiochannels and their broadcasts and
     * information surrounding them
     * @param channels An Arraylist with names of the channels as strings
     */
    public SRRadioGui(ArrayList<String> channels) {
        setSize(1000,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setJMenuBar(new SRRadioMenuBar());
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(550,770));
        add(leftPanel,BorderLayout.WEST);
        rightPanel = new RadioPanel(channels);
        add(rightPanel,BorderLayout.EAST);
        setVisible(true);
    }

    /**
     * Removes old Jtable and inserts new one
     * @param table JTable to be inserted
     */
    public void addJtable(JTable table) {
        if(table != null){
            if(leftPanel.getComponentCount() == 1){
                leftPanel.remove(0);
            }
            this.table = new JScrollPane(table);
            leftPanel.add(this.table);
            leftPanel.revalidate();
            leftPanel.repaint();
            pack();
        }
    }

    /**
     * Returns the table currently showing
     * @return A Jtable
     */
    public JTable getTable() {
        return (JTable)table.getViewport().getComponent(0);
    }

    /**
     * Returns the Update button
     * @return A Jbutton
     */
    public JButton getUpdateButton() {
        return rightPanel.getButton();
    }

    /**
     * Returns the Channel ComboBox
     * @return A JComboBox
     */
    public JComboBox<String> getComboBox() {
        return rightPanel.getComboBox();
    }

    /**
     * Sets the description in the description text area
     * @param description A string
     */
    public void setDescription(String description) {
        rightPanel.setDescription(description);
    }

    /**
     * Returns the Jlabel showing the images
     * @return A Jlabel
     */
    public JLabel getJlabel() {
        return rightPanel.getJlabel();
    }

    /**
     * Returns the name of the channel currently selected in the JComboBox
     * @return A String
     */
    public String getSelectedChannel() {
        return rightPanel.getChannel();
    }

}


import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class SRController {
    private SRChannelParser channelParser;
    private SRBroadcastsParser broadcastsParser;
    private SRRadioGui gui;

    /**
     * Constructs a SRController
     */
    public SRController() {
        broadcastsParser = new SRBroadcastsParser();
        boolean exceptionCaught = false;

        try {
            channelParser = new SRChannelParser();
        } catch (IOException e) {
            exceptionCaught = true;
            gui = new SRRadioGui(new ArrayList<>());
            gui.errorMessage();
        }

        /*
         * If exception is caught then the program will create an empty
         * Gui and only show an errormessage
         */
        if (!exceptionCaught) {
                gui = new SRRadioGui(channelParser.getChannels());
                gui.getComboBox().addActionListener(e -> updateTable());
                gui.getUpdate().addActionListener(e -> updateTable());
        }

    }

    /**
     * Updates the table with the broadcasts of the currently selected channel
     */
    public synchronized void updateTable() {
        String[] columnNames = {" ","Titel","Sändingstid","Längd"};

        try {
            broadcastsParser.getSchedule(channelParser.getChannelID
                    (gui.getSelectedChannel()));
        } catch (IOException | NullPointerException e) {
            gui.errorMessage();
        }

        Object[][] data = getData();

        SwingUtilities.invokeLater(() -> {
            gui.addJtable(setUpJTable(data,columnNames));
        });

    }

    /**
     * Sets up the settings for the Jtable with the data provided
     * @param data A Object matrix
     * @param columNames A string array
     * @return A Jtable
     */
    private JTable setUpJTable(Object[][] data,String[] columNames) {
        JTable SRTable = new JTable(data,columNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        SRTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        SRTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        SRTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        SRTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        SRTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        SRTable.getSelectionModel().addListSelectionListener(e -> {
            if (gui.getTable().getSelectedRow() > -1) {
                SwingUtilities.invokeLater(() -> {
                    if (broadcastsParser.getEpisodeSize() >
                                SRTable.getSelectedRow()) {
                        gui.setDescription(broadcastsParser.getTitles().get
                                (SRTable.getSelectedRow()) + "\n" +
                                broadcastsParser.getNodesContent("description")
                                        .get(SRTable.getSelectedRow()));

                        try {
                            URL url = new URL(broadcastsParser.getImageURL
                                    (SRTable.getSelectedRow()));
                            ImageIcon icon = new ImageIcon(ImageIO.read(url));
                            gui.getJlabel().setIcon(icon);
                        } catch (IOException ioe) {
                            System.err.println("Exception caught when trying" +
                                    " to change image, keeping the old one");
                        }

                    }
                });
            }
        });
        return SRTable;
    }

    /**
     * Gets the data needed to set up the JTable
     * @return A Object matrix
     */
    private Object[][] getData() {
        Object[][] data;
        ArrayList<String> startTimes = broadcastsParser.getNodesContent
                ("starttimeutc");
        ArrayList<String> titles = broadcastsParser.getTitles();
        ArrayList<String> endTimes = broadcastsParser.getNodesContent
                ("endtimeutc");
        Instant time = Instant.now();
        data = new Object[broadcastsParser.getEpisodeSize()][4];
        for (int i = 0; i < broadcastsParser.getEpisodeSize(); i++) {
            if (time.plus(Duration.ofHours(1)).isAfter(Instant.parse
                    (startTimes.get(i)).plus(Duration.ofHours(1)))) {
                data[i][0] = "Har redan sänt";
            } else {
                data[i][0] = "Kommer sändas";
            }
            data[i][1] = titles.get(i);
            data[i][2] = formatTime(startTimes.get(i));
            data[i][3] = Duration.between(Instant.parse(startTimes.get(i)),
                    Instant.parse(endTimes.get(i))).toMinutes()+" Min";
        }

        return data;
    }

    /**
     * Formats the string
     * @param time a string in the format of Instant.Parse()
     * @return formatted string
     */
    private String formatTime(String time) {
        String newTime = Instant.parse(time).toString();
        return newTime.substring(0,10)+" "+newTime.substring(11,19);
    }
}

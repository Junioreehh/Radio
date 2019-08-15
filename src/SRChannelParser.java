import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class SRChannelParser {
    private Document XMLDocument;

    /**
     * Constructs a SRChannelParser
     */
    public SRChannelParser() {
        updateDocument();
    }

    /**
     * Returns the names of the "channel" nodes attribute "name" of the current
     * XMLDocument
     * @return An ArrayList with strings
     */
    public ArrayList<String> getChannels() {
        ArrayList<String> channelNames = new ArrayList<>();
        if(XMLDocument == null){
            channelNames.add("Error occurred retrieving channels");
            return channelNames;
        }
        NodeList channelsNodes = XMLDocument.getElementsByTagName("channel");
        for(int i = 0; i < channelsNodes.getLength();i++){
            channelNames.add(channelsNodes.item(i).getAttributes()
                            .getNamedItem("name").getNodeValue());
        }
        return channelNames;
    }

    /**
     * Returns the description of the channel
     * @param channelName A string of the name of the channel
     * @return A string
     */
    public String getChannelDesc(String channelName) {
        Node currentNode = getChannelsNode();
        NodeList channelNodeList = currentNode.getChildNodes();
        if(XMLDocument == null){
            return "Couldn't retrieve description";
        }

        /*Finds the description for the corresponding channel name*/
        for(int i = 0;i < channelNodeList.getLength();i++){
            currentNode = channelNodeList.item(i);
            if(currentNode.getNodeType() == 1){
                if(currentNode.getAttributes().getNamedItem("name")
                        .getNodeValue().equals(channelName)){
                    for(int j = 0; j < currentNode.getChildNodes().getLength()
                            ;j++){
                        if(currentNode.getChildNodes().item(j).getNodeName()
                                      .equals("tagline")){
                            return currentNode.getChildNodes().item(j).
                                    getTextContent();
                        }
                    }
                }
            }
        }
        return "Couldn't retrieve description";
    }

    /**
     * Returns the image associated with the channel
     * @param channelName The name of the channel
     * @return A String of the URL of the image
     */
    public String getChannelImageURL(String channelName) {
        Node currentNode = getChannelsNode();
        NodeList channelNodeList = currentNode.getChildNodes();
        if(XMLDocument == null){
            return "https://static-cdn.sr.se/sida/images/3113/2216702_" +
                    "512_512.jpg?preset=socialmedia-share-image";
        }

        /*Finds the image URL for the corresponding channel name*/
        for(int i = 0;i < channelNodeList.getLength();i++){
            currentNode = channelNodeList.item(i);
            if(currentNode.getNodeType() == 1){
                if(currentNode.getAttributes().getNamedItem("name")
                              .getNodeValue().equals(channelName)){
                    return currentNode.getChildNodes().item(1)
                                      .getTextContent();
                }
            }
        }
        return "https://static-cdn.sr.se/sida/images/3113/2216702_" +
                "512_512.jpg?preset=socialmedia-share-image";
    }

    /**
     * Returns the ID of the channel
     * @param channelName The name of the channel
     * @return A String of the ID
     */
    public String getChannelID(String channelName) {
        Node currentNode = getChannelsNode();
        NodeList channelNodeList = currentNode.getChildNodes();

        /*Finds the image URL for the corresponding channel name*/
        for(int i = 0;i < channelNodeList.getLength();i++){
            currentNode = channelNodeList.item(i);
            if(currentNode.getNodeType() == 1){
                if(currentNode.getAttributes().getNamedItem("name")
                              .getNodeValue().equals(channelName)){
                    return currentNode.getAttributes().getNamedItem("id")
                                      .getNodeValue();
                }
            }
        }
        return "Couldn't find channelID";
    }

    /**
     * Updates the XMLDocument containing the channels
     */
    public void updateDocument() throws ParserConfigurationException,
                                 IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory
                = DocumentBuilderFactory.newInstance();

        /*Recovers the XML and parses it from the Sveriges radio API*/
            DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
            XMLDocument = db.parse(new URL("http://api.sr.se/api/v2/" +
                    "channels?pagination=false")
                    .openStream());
                                     
            //System.err.println("Unable to configure parser");
            //JFrame errorMessage = new JFrame();
            //errorMessage.add(new JTextArea("Någonting gick snett,prova igen"));
            System.err.println("Stream from SR closed unexpectedly");
            System.err.println("Not correct format");
        }
    }

    /**
     * locates the "channels" node and returns it
     * @return A Node
     */
    private Node getChannelsNode() {
        Node currentNode = null;

        /*Finds the "channels" node in the document*/
        NodeList channelNodeList = XMLDocument.getFirstChild().getChildNodes();
        for(int i = 0;i < channelNodeList.getLength();i++){
            currentNode = channelNodeList.item(i);
            if(currentNode.getNodeName().equals("channels")){
                return currentNode;
            }
        }
        return currentNode;
    }
}

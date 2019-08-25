import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;

public class SRBroadcastsParser {
    private ArrayList<Node> episodes;

    /**
     * Constructs a SRBroadcastsParser
     */
    public SRBroadcastsParser() {
        episodes = new ArrayList<>();
    }

    /**
     * Saves the episodes of the channel with the id
     * @param id A string of the channel id
     */
    public void getSchedule(String id) throws IOException {
        episodes.clear();
        String URL = "http://api.sr.se/api/v2/scheduledepisodes?channelid="+
                      id+"&pagination=false"+"&date=";
        getEpisodes(getDocument(URL+Instant.now().minus(Duration.ofDays(1))
                .toString().substring(0,10)));
        getEpisodes(getDocument(URL+Instant.now().toString()
                                .substring(0,10)));
        getEpisodes(getDocument(URL+Instant.now().plus(Duration.ofDays(1))
                .toString().substring(0,10)));
        removeEpisodes();
    }

    /**
     * Returns the titles as string
     * @return An ArrayList with strings
     */
    public ArrayList<String> getTitles(){
        ArrayList<String> titles = new ArrayList<>();
        Iterator<Node> i = episodes.iterator();
        String title;

        while(i.hasNext()){
            Node currentNode = i.next();
            title = "";
            for(int j = 0; j < currentNode.getChildNodes().getLength(); j++){
                if (currentNode.getChildNodes().item(j).getNodeName()
                        .equals("title")){
                    title = currentNode.getChildNodes().item(j)
                            .getTextContent();
                }
                if (currentNode.getChildNodes().item(j).getNodeName()
                        .equals("subtitle")){
                    title = title+" "+currentNode.getChildNodes().item(j)
                            .getTextContent();
                }
            }
            if(!title.equals("")) {
                titles.add(title);
            }
        }
        return titles;
    }

    /**
     * Returns the content asked for with the string parameter
     * @param node A string of the content name requested eg. "starttimeutc"
     * @return An ArrayList with strings
     */
    public ArrayList<String> getNodesContent(String node){
        ArrayList<String> nodeContents = new ArrayList<>();
        Iterator<Node> i = episodes.iterator();
        while(i.hasNext()){
            Node currentNode = i.next();
            for(int j = 0; j < currentNode.getChildNodes().getLength(); j++){
                if (currentNode.getChildNodes().item(j).getNodeName()
                        .equals(node)){
                    nodeContents.add(currentNode.getChildNodes().item(j)
                            .getTextContent());
                }
            }
        }
        return nodeContents;
    }

    /**
     * Returns the imageURL of the broadcast with the index in the episode list
     * @param index An int
     * @return A string of the URL
     */
    public String getImageURL(int index){
        Node currentNode = episodes.get(index);
        for(int j = 0; j < currentNode.getChildNodes().getLength(); j++){
            if(currentNode.getChildNodes().item(j).getNodeName()
                    .equals("imageurl")){
                return currentNode.getChildNodes().item(j).getTextContent();
            }
        }
        return "https://static-cdn.sr.se/sida/images/3113/2216702_" +
                "512_512.jpg?preset=socialmedia-share-image";
    }

    /**
     * Returns the amount of episodes in the list
     * @return An int
     */
    public int getEpisodeSize(){
        return episodes.size();
    }

    /**
     * Returns the Document from the URL
     * @param URL A string of the URL
     * @return A Document
     */
    private Document getDocument(String URL) throws IOException {
        Document doc = null;
        DocumentBuilderFactory documentBuilderFactory
                = DocumentBuilderFactory.newInstance();

        /*Recovers the XML and parses it from the Sveriges radio API*/
        try {
            DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
            doc = db.parse(new URL(URL).openStream());
        }catch(ParserConfigurationException e){
            System.err.println("Unable to configure parser");
            System.exit(1);
        }catch(SAXException e){
            System.err.println("Not correct format");
            System.exit(1);
        }
        return doc;
    }

    /**
     * Saves the episodes from the XMLDocument
     * @param XMLDocument A Document
     */
    private void getEpisodes(Document XMLDocument){
        NodeList episodeNodes = XMLDocument.getElementsByTagName
                ("scheduledepisode");
        for(int i = 0; i < episodeNodes.getLength(); i++){
            episodes.add(episodeNodes.item(i));
        }
    }

    /**
     * Removes the episodes which are further than 12 hours ago or further than
     * 12 hours ahead
     */
    private void removeEpisodes(){
        Iterator<Node> i = episodes.iterator();
        while(i.hasNext()){
            Node currentNode = i.next();
            for(int j = 0; j < currentNode.getChildNodes().getLength(); j++){
                if(currentNode.getChildNodes().item(j).getNodeName()
                        .equals("starttimeutc")){
                    if(Instant.now().minus(Duration.ofHours(12)).compareTo
                            (Instant.parse(currentNode.getChildNodes().item(j)
                                    .getTextContent())) > 0){
                        i.remove();
                    }else if(Instant.now().plus(Duration.ofHours(12)).compareTo
                            (Instant.parse(currentNode.getChildNodes().item(j)
                                    .getTextContent())) < 0){
                        i.remove();
                    }
                }
            }
        }
    }

}


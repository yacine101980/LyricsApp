package app.lyricsapp.model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchLyricText {
    ArrayList<Song> toPrint;
    public SearchLyricText(){
        this.toPrint = new ArrayList<>();
    }

    public ArrayList<Song> getToPrint() {
        return toPrint;
    }

    public void searchLyricText(String lyrics) throws IOException, SAXException, ParserConfigurationException {



        String Artist = null;

        String song = null;
        String SongUrl = null;


        String lien = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricText?lyricText=" + URLEncoder.encode(lyrics, StandardCharsets.UTF_8);

        System.out.print(lien);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(lien);

        NodeList booksList = document.getElementsByTagName("ArrayOfSearchLyricResult");

        Node node = booksList.item(0);

        NodeList nodeList = node.getChildNodes();

        for(int i = 1; i < nodeList.getLength(); i++) {

            Node newNode = nodeList.item(i);
            NodeList newNodeList = newNode.getChildNodes();
            for(int j = 1; j < newNodeList.getLength(); j++) {

                Node nodeToCheck = newNodeList.item(j);


                if (nodeToCheck.getNodeName().equals("SongUrl")) {
                    SongUrl = newNodeList.item(j).getTextContent();
                }

                if (nodeToCheck.getNodeName().equals("Artist")) {
                    Artist = newNodeList.item(j).getTextContent();
                }
                if (nodeToCheck.getNodeName().equals("Song")) {
                    song = newNodeList.item(j).getTextContent();
                }

            }
            Song music = new Song(SongUrl, Artist, song);
            if(!getToPrint().contains(music)){
                toPrint.add(music);
            }
        }
        for(Song songToPrint : toPrint){
            System.out.println(songToPrint);
        }
    }
}

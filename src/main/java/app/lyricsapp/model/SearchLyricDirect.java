package app.lyricsapp.model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class SearchLyricDirect{
    public static Song searchLyricDirect(String artist, String title) throws IOException, ParserConfigurationException, SAXException {

        String LyricArtist = null;
        String LyricSong = null;

        String Lyric =null;


        Document document = null;
        DocumentBuilderFactory factory = null;
        DocumentBuilder builder = null;

        String lien = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist=" + artist + "&song=" + title;


        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(lien);

        NodeList booksList = document.getElementsByTagName("GetLyricResult");
        Node n = booksList.item(0);
        NodeList v = n.getChildNodes();
        for(int i = 0; i < v.getLength(); i++)
        {
            Node r = v.item(i);
            if (r.getNodeName().equals("LyricSong")) LyricSong = new String(r.getTextContent());
            if (r.getNodeName().equals("LyricArtist")) LyricArtist = new String(r.getTextContent());
            if (r.getNodeName().equals("Lyric")) Lyric = new String(r.getTextContent());
        }
        return new Song(Lyric, LyricArtist, LyricSong);
    }
}

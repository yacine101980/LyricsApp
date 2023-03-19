package app.lyricsapp.model;

public class songException extends Exception {
    private String artist;
    private String title;

    public songException(String title, String artist){
        this.title = title;
        this.artist = artist;
    }

    public String getMessage(){
        return "Cette chanson " + title + " de l'artiste  " + artist + " exsiste dans la playlist.";
    }
}

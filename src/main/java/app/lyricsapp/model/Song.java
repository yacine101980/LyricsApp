package app.lyricsapp.model;

import java.util.Objects;

public class Song {

    String lyric;
    String author;
    String title;


    public Song(String lyric, String author, String title) {

        this.lyric = lyric;

        this.author = author;
        this.title = title;

    }

    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }

    public String getLyric() {
        return lyric;
    }



    public String toString(){

        return
                "\nLyric: " + getLyric() +
             "\nArtist: " + getAuthor() +
                "\nTitre: " + getTitle() +"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(lyric, song.lyric) && Objects.equals(author, song.author) && Objects.equals(title, song.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lyric,  author, title);
    }
}



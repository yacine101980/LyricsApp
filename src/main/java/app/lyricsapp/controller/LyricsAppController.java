package app.lyricsapp.controller;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javafx.geometry.Insets;
import app.lyricsapp.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import java.util.ResourceBundle;

public class LyricsAppController implements Initializable {
    @FXML private TextField TitleTextField;
    @FXML private Button favoritesButton;
    @FXML private Button SearchByTitleArtistButton;
    @FXML private Button SearchByLyricsButton;
    @FXML private Spinner<Integer> resultsSpinner;
    @FXML private TextField ArtistTextField;
    @FXML private TextField LyricsTextField;
    @FXML private GridPane gridPane;


    Playlist MyFav = new Playlist("myFavorites");

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 1);
        resultsSpinner.setValueFactory(value);

        CornerRadii buttonCornerRadii = new CornerRadii(50, 50, 50, 50, false);
        BackgroundFill buttonBackgroundFill = new BackgroundFill(Color.rgb(173,0,37), buttonCornerRadii, null);
        Background buttBackground = new Background(buttonBackgroundFill);

        favoritesButton.setBackground(buttBackground);
        SearchByLyricsButton.setBackground(buttBackground);
        SearchByTitleArtistButton.setBackground(buttBackground);

        favoritesButton.setTextFill(Color.rgb(169, 160, 144));
        SearchByLyricsButton.setTextFill(Color.rgb(169, 160, 144));
        SearchByTitleArtistButton.setTextFill(Color.rgb(169, 160, 144));

        biggerButton(favoritesButton, SearchByLyricsButton, SearchByTitleArtistButton);

        SearchByTitleArtistButton.setOnAction(event -> {
            gridPane.getChildren().clear();

            String titre = TitleTextField.getText();
            String artiste = ArtistTextField.getText();

            Song song = null;
            try {
                song = new SearchLyricDirect().searchLyricDirect(artiste, titre);
            } catch (IOException | ParserConfigurationException | SAXException e) {
                throw new RuntimeException(e);
            }
            String string = null;
            try {
                 string = SongNonValable.toVerify(artiste, titre);
            } catch (ParserConfigurationException | IOException | SAXException e) {
                e.printStackTrace();
            }
            if(string.equals("http://www.chartlyrics.com")){
                Label label = new Label("Song Non Available");
                label.setStyle("-fx-font-size: 17px;-fx-text-fill: white;");
                GridPane.setConstraints(label, 0, 0);

                GridPane.setHalignment(label, HPos.CENTER);

                gridPane.setAlignment(Pos.CENTER);

                gridPane.getChildren().add(label);
            }else {

                Button songButton = new Button();
                songButton.setText(song.getAuthor() + " - " + song.getTitle());
                songButton.setPadding(new Insets(0, 20, 0, 20));
                songButton.setPrefWidth(700);
                songButton.setPrefHeight(70);
                songButton.setStyle("-fx-font-size: 17px;");

                gridPane.add(songButton, 0, 0);
                gridPane.setPadding(new Insets(0, 100, 250, 100));
                Song finalSong = song;
                songButton.setOnAction(event1 -> {
                    try {
                        windowOfMyLyrics(finalSong);
                    } catch (IOException | ParserConfigurationException | SAXException e) {
                        throw new RuntimeException(e);
                    }
                });


                Button addFav = new Button("\u2661");
                addFav.setStyle("-fx-text-fill: black;-fx-font-size: 30px;");

                addFav.setPrefSize(70, 70);
                gridPane.add(addFav, 1, 0);

                CornerRadii songButtonCornerRadii = new CornerRadii(50, 0, 0, 50, false);
                BackgroundFill songButtonBackgroundFill = new BackgroundFill(Color.rgb(173, 0, 37), songButtonCornerRadii, null);
                Background songButtonBackground = new Background(songButtonBackgroundFill);

                CornerRadii addFavCornerRadii = new CornerRadii(0, 50, 50, 0, false);
                BackgroundFill addFavBackgroundFill = new BackgroundFill(Color.rgb(173, 0, 37), addFavCornerRadii, null);
                Background addFavBackground = new Background(addFavBackgroundFill);

                songButton.setBackground(songButtonBackground);
                addFav.setBackground(addFavBackground);

                songButton.setTextFill(Color.rgb(169, 160, 144));

                Song finalSong1 = song;
                addFav.setOnAction(event1 -> {
                    addFav.setStyle("-fx-text-fill: #A9A090; -fx-font-size: 30px;");
                    try {
                        MyFav.addMusic(finalSong1);
                    } catch (songException e) {
                        throw new RuntimeException(e);
                    }

                });

                songButton.setOnMouseEntered(event1 -> {
                    songButton.setScaleX(1.1);
                    songButton.setScaleY(1.1);
                    addFav.setScaleX(1.1);
                    addFav.setScaleY(1.1);
                });
                songButton.setOnMouseExited(event1 -> {
                    songButton.setScaleX(1.0);
                    songButton.setScaleY(1.0);
                    addFav.setScaleX(1.0);
                    addFav.setScaleY(1.0);
                });

                addFav.setOnMouseEntered(event1 -> {
                    addFav.setScaleX(1.1);
                    addFav.setScaleY(1.1);
                    songButton.setScaleX(1.1);
                    songButton.setScaleY(1.1);
                });

                addFav.setOnMouseExited(event1 -> {
                    addFav.setScaleX(1.0);
                    addFav.setScaleY(1.0);
                    songButton.setScaleX(1.0);
                    songButton.setScaleY(1.0);
                });
            }
        });

        SearchByLyricsButton.setOnAction(event -> {
            gridPane.getChildren().clear();

            int numberOfResult = value.getValue();
            String lyrics = LyricsTextField.getText();
            SearchLyricText search = new SearchLyricText();

            try {
                search.searchLyricText(lyrics);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }

            for (int i = 1; i <= numberOfResult; i++) {
                Song song = search.getToPrint().get(i - 1);
                if (song.getAuthor() ==  null && song.getTitle() == null) {
                    Label label = new Label("Song Non Available");
                    label.setStyle("-fx-font-size: 17px;-fx-text-fill: white;");
                    GridPane.setConstraints(label, 0, 0);

                    GridPane.setHalignment(label, HPos.CENTER);

                    gridPane.setAlignment(Pos.CENTER);

                    gridPane.getChildren().add(label);
                } else {
                    Button songButton = new Button();
                    songButton.setText(song.getAuthor() + " - " + song.getTitle());
                    songButton.setPadding(new Insets(0.0, 20, 0.0, 20));
                    songButton.setPrefWidth(700);
                    songButton.setPrefHeight(70);
                    songButton.setStyle("-fx-font-size: 17px;");
                    gridPane.add(songButton, 0, i);
                    gridPane.setPadding(new Insets(20, 100, 250, 100));
                    songButton.setOnAction(event1 -> {
                        try {
                            windowOfMyLyrics(song);
                        } catch (IOException | ParserConfigurationException | SAXException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    Button addFav = new Button("\u2661");
                    addFav.setStyle("-fx-text-fill: black;-fx-font-size: 30px;");

                    addFav.setPrefSize(70, 70);
                    gridPane.add(addFav, 1, i);
                    gridPane.setVgap(15);

                    CornerRadii songButtonCornerRadii = new CornerRadii(50, 0, 0, 50, false);
                    BackgroundFill songButtonBackgroundFill = new BackgroundFill(Color.rgb(173, 0, 37), songButtonCornerRadii, null);
                    Background songButtonBackground = new Background(songButtonBackgroundFill);

                    CornerRadii addFavCornerRadii = new CornerRadii(0, 50, 50, 0, false);
                    BackgroundFill addFavBackgroundFill = new BackgroundFill(Color.rgb(173, 0, 37), addFavCornerRadii, null);
                    Background addFavBackground = new Background(addFavBackgroundFill);

                    songButton.setBackground(songButtonBackground);
                    addFav.setBackground(addFavBackground);

                    songButton.setTextFill(Color.rgb(169, 160, 144));


                    addFav.setOnAction(event1 -> {

                        addFav.setStyle("-fx-text-fill: #A9A090; -fx-font-size: 30px;");
                        if (!MyFav.getSongs().contains(song)) {
                            try {
                                MyFav.addMusic(song);
                            } catch (songException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    });

                    songButton.setOnMouseEntered(event1 -> {
                        songButton.setScaleX(1.1);
                        songButton.setScaleY(1.1);
                        addFav.setScaleX(1.1);
                        addFav.setScaleY(1.1);
                    });
                    songButton.setOnMouseExited(event1 -> {
                        songButton.setScaleX(1.0);
                        songButton.setScaleY(1.0);
                        addFav.setScaleX(1.0);
                        addFav.setScaleY(1.0);
                    });

                    addFav.setOnMouseEntered(event1 -> {
                        addFav.setScaleX(1.1);
                        addFav.setScaleY(1.1);
                        songButton.setScaleX(1.1);
                        songButton.setScaleY(1.1);
                    });

                    addFav.setOnMouseExited(event1 -> {
                        addFav.setScaleX(1.0);
                        addFav.setScaleY(1.0);
                        songButton.setScaleX(1.0);
                        songButton.setScaleY(1.0);
                    });
                }
            }
        });

        favoritesButton.setOnAction(event -> {

            gridPane.getChildren().clear();

            for (int i = 0; i <= MyFav.getSongs().size() - 1; i++) {

                Song song = MyFav.getSongs().get(i);
                Button songButton = new Button();
                songButton.setText(song.getAuthor() + " - " +  song.getTitle());
                songButton.setPadding(new Insets(0.0, 20, 0.0, 20));
                songButton.setPrefWidth(700);
                songButton.setPrefHeight(70);
                songButton.setStyle("-fx-font-size: 17px;");
                gridPane.add(songButton, 0, i + 2);
                gridPane.setPadding(new Insets(0,100,250,100));
                songButton.setOnAction(event1 -> {
                    try {
                        windowOfMyLyrics(song);
                    } catch (IOException | ParserConfigurationException | SAXException e) {
                        throw new RuntimeException(e);
                    }
                });

                Button addFav = new Button("\u2661");
                addFav.setStyle("-fx-text-fill: #A9A090;-fx-font-size: 30px;");
                addFav.setPrefSize(70, 70);
                gridPane.add(addFav, 1, i + 2);
                gridPane.setVgap(15);

                CornerRadii songButtonCornerRadii = new CornerRadii(50, 0, 0, 50, false);
                BackgroundFill songButtonBackgroundFill = new BackgroundFill(Color.rgb(173,0,37), songButtonCornerRadii, null);
                Background songButtonBackground = new Background(songButtonBackgroundFill);

                CornerRadii addFavCornerRadii = new CornerRadii(0, 50, 50, 0, false);
                BackgroundFill addFavBackgroundFill = new BackgroundFill(Color.rgb(173,0,37), addFavCornerRadii, null);
                Background addFavBackground = new Background(addFavBackgroundFill);

                songButton.setBackground(songButtonBackground);
                addFav.setBackground(addFavBackground);

                songButton.setTextFill(Color.rgb(169, 160, 144));

                addFav.setOnAction(event1 -> {
                    addFav.setStyle("-fx-text-fill: black; -fx-font-size: 30px;");
                    MyFav.getSongs().remove(song);
                    gridPane.getChildren().remove(addFav);
                    gridPane.getChildren().remove(songButton);
                });

                songButton.setOnMouseEntered(event1 -> {
                    songButton.setScaleX(1.1);
                    songButton.setScaleY(1.1);
                    addFav.setScaleX(1.1);
                    addFav.setScaleY(1.1);
                });
                songButton.setOnMouseExited(event1 -> {
                    songButton.setScaleX(1.0);
                    songButton.setScaleY(1.0);
                    addFav.setScaleX(1.0);
                    addFav.setScaleY(1.0);
                });

                addFav.setOnMouseEntered(event1 -> {
                    addFav.setScaleX(1.1);
                    addFav.setScaleY(1.1);
                    songButton.setScaleX(1.1);
                    songButton.setScaleY(1.1);
                });

                addFav.setOnMouseExited(event1 -> {
                    addFav.setScaleX(1.0);
                    addFav.setScaleY(1.0);
                    songButton.setScaleX(1.0);
                    songButton.setScaleY(1.0);
                });
            }
        });
    }


    public void windowOfMyLyrics(Song song) throws IOException, ParserConfigurationException, SAXException {
        BorderPane borderPane = new BorderPane();
        String pathToCovert = CovertOfSong.showCovert(song.getAuthor(), song.getTitle());
        Image image = null;
        if(pathToCovert.isEmpty() || pathToCovert == null){
            image = new Image(new FileInputStream("src/main/resources/app/lyricsapp/No_Cover.jpg"));
        } else {
            image =new Image(pathToCovert);
            if (image.isError()) {
                image = new Image(new FileInputStream("src/main/resources/app/lyricsapp/No_Cover.jpg"));
            }
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        TextArea textArea = new TextArea();
        textArea.setPrefSize(500, 720);
        String Lyric = SearchLyricDirect.searchLyricDirect(song.getAuthor(), song.getTitle()).getLyric();
        if(Lyric == null || Lyric.equals("")){
            Lyric = "No Lyrics Founded";
        }
        textArea.setText(Lyric);

        borderPane.setCenter(textArea);
        borderPane.setLeft(imageView);

        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.setTitle("Lyrics of " + song.getAuthor() + " - " + song.getTitle());
        stage.setScene(scene);
        stage.show();
    }

    public void biggerButton(Button button, Button button1, Button button2){
        button.setOnMouseEntered(event1 -> {
            button.setScaleX(1.3);
            button.setScaleY(1.3);
        });
        button.setOnMouseExited(event1 -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        button1.setOnMouseEntered(event1 -> {
            button1.setScaleX(1.3);
            button1.setScaleY(1.3);
        });

        button1.setOnMouseExited(event1 -> {
            button1.setScaleX(1.0);
            button1.setScaleY(1.0);
        });

        button2.setOnMouseEntered(event1 -> {
            button2.setScaleX(1.3);
            button2.setScaleY(1.3);
        });
        button2.setOnMouseExited(event1 -> {
            button2.setScaleX(1.0);
            button2.setScaleY(1.0);
        });
    }
}

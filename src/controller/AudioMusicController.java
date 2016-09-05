package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Game;
import model.fileFormats.Music;
import model.fileFormats.Sound;
import view.MusicPlayer;
import view.SoundPlayer;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class AudioMusicController implements Initializable, Observer {
    private static final String CSS_ROOT_STYLE_CSS = "/css/rootStyle.css";
    private static final String ICONS_1471297276_MINUS_PNG = "icons/1471297276_minus.png";
    private static final String MY_RESOURCES = "myResources";

    @FXML
    private ListView<SoundPlayer> lstSounds;
    @FXML
    private ComboBox<Sound> cmbGameSound;
    @FXML
    private MusicPlayer sdpMusic;
    @FXML
    private ComboBox<Music> cmbGameMusic;


    private final Game  game = Game.getInstance();
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = ResourceBundle.getBundle(MY_RESOURCES);
        game.addObserver(this);
        update(null, null);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        cmbGameMusic.setItems(FXCollections.observableArrayList(game.getMusics()));
        cmbGameSound.setItems(FXCollections.observableArrayList(game.getSounds()));
    }

    public void setUpMusic() {
        game.setActualMusic(cmbGameMusic.getSelectionModel().getSelectedItem());
        sdpMusic.setFile(game.getActualMusic());
    }


    public void addSound() {
        if (!cmbGameSound.getSelectionModel().isEmpty()) {
            try {
                MediaPlayer item = new MediaPlayer(new Media(new File("sounds/" +
                        cmbGameSound.getSelectionModel().getSelectedItem().getPath()).toURI().toString()));
                SoundPlayer player = new SoundPlayer(item);
                player.setMaxWidth(100);
                try {
                    String name = cmbGameSound.getSelectionModel().getSelectedItem().getName();
                    player.setText(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Button btnRemove = new Button();
                btnRemove.setGraphic(new ImageView(new Image(ICONS_1471297276_MINUS_PNG, 9, 9, true, true)));
                btnRemove.setOnAction(a -> {
                    Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
                    dialog.setContentText(resources.getString("deleteReally"));
                    dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
                    if (dialog.showAndWait().get() == ButtonType.OK) {
                        lstSounds.getItems().remove(player);
                        player.stopPlayer();
                    }
                });
                player.setGraphic(new HBox(player.getGraphic(), btnRemove));

                lstSounds.getItems().add(0, player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

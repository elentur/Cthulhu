package view;

import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.fileFormats.DataFile;

import java.io.File;

/**
 * Created by Marcus Baetz on 19.08.2016.
 *
 * @author Marcus Bätz
 */
public class MusicPlayer extends VBox {
    private static final String ICONS_1471629017_PAUSE_PNG = "icons/1471629017_pause.png";
    private static final String ICONS_1471629021_STOP_PNG = "icons/1471629021_stop.png";
    private static final String ICONS_1471628977_PLAY_PNG = "icons/1471628977_play.png";
    private static final String LOOP = "Loop";
    private static final String STATUS = "Status: ";
    private final Slider sldVolume = new Slider();
    private ChangeListener changeListener;

    public void setFile(final DataFile file) {
        this.file.set(file);
    }

    private final CheckBox chkLoop = new CheckBox(LOOP);
    private final ProgressBar prbStatus = new ProgressBar();
    private final ObjectProperty<DataFile> file = new SimpleObjectProperty<>(null);
    private MediaPlayer player = null;


    public MusicPlayer() {
        ImageView imgPlay = new ImageView(new Image(ICONS_1471628977_PLAY_PNG, 18, 18, true, true));
        Button btnPlay = new Button();
        btnPlay.setGraphic(imgPlay);
        btnPlay.setOnAction(a -> play());
        ImageView imgStop = new ImageView(new Image(ICONS_1471629021_STOP_PNG, 18, 18, true, true));
        Button btnStop = new Button();
        btnStop.setGraphic(imgStop);
        btnStop.setOnAction(a -> stop());
        ImageView imgPause = new ImageView(new Image(ICONS_1471629017_PAUSE_PNG, 18, 18, true, true));
        Button btnPause = new Button();
        btnPause.setGraphic(imgPause);
        btnPause.setOnAction(a -> pause());
        sldVolume.setValue(30);
        sldVolume.setMin(0);
        sldVolume.setMax(100);
        sldVolume.setMajorTickUnit(50);
        sldVolume.setMinorTickCount(10);
        sldVolume.setShowTickLabels(true);
        sldVolume.setShowTickMarks(true);
        chkLoop.setOnAction(a -> {
                    if (player != null) {
                        if (chkLoop.isSelected())
                            player.setCycleCount(-1);
                        else
                            player.setCycleCount(1);
                    }
                }
        );

        Label lblStatus = new Label(STATUS);
        prbStatus.setProgress(0);
        lblStatus.textProperty().bind(Bindings.format(STATUS+ "%.0f", prbStatus.progressProperty().multiply(100)));
        getChildren().addAll(new HBox(5, btnPlay, btnPause, btnStop, new VBox(prbStatus, lblStatus, chkLoop)), new Label("Volume"), sldVolume);
    }

    private void pause() {
        if (player != null && player.getStatus() == MediaPlayer.Status.PLAYING) {
            player.pause();
        }
    }

    private void stop() {
        if (player != null) {
            player.stop();
            player.currentTimeProperty().removeListener(changeListener);
            player = null;
        }
    }

    private void play() {
        if (file.getValue() == null) return;
        if (player == null) {
            player = new MediaPlayer(new Media(new File("musics/" + file.getValue().getPath()).toURI().toString()));
            player.volumeProperty().bind(sldVolume.valueProperty().divide(100.0));
            prbStatus.setProgress(0);
            changeListener = (a, b, c) -> prbStatus.setProgress(1.0 * player.getCurrentTime().toMillis() / player.getMedia().getDuration().toMillis());
            player.currentTimeProperty().addListener(changeListener);
            player.setOnEndOfMedia(() -> {
                player.stop();
                player.currentTimeProperty().removeListener(changeListener);
                if (chkLoop.isSelected()) {
                    player=null;
                    play();
                }
            });
            player.play();
        } else if (player.getMedia().getSource().contains(file.getValue().getPath())) {
            if (player.getStatus() == MediaPlayer.Status.PAUSED) player.play();
        } else {
            player.currentTimeProperty().removeListener(changeListener);
            MediaPlayer playerNew = new MediaPlayer(new Media(new File("musics/" + file.getValue().getPath()).toURI().toString()));
            prbStatus.setProgress(0);
            changeListener = (a, b, c) -> prbStatus.setProgress(1.0 * playerNew.getCurrentTime().toMillis() / playerNew.getMedia().getDuration().toMillis());
            playerNew.currentTimeProperty().addListener(changeListener);
            playerNew.setOnEndOfMedia(() -> {
                playerNew.stop();
                playerNew.currentTimeProperty().removeListener(changeListener);
                if (chkLoop.isSelected()) {
                    player=null;
                    play();
                }
            });

            playerNew.setVolume(0);

            playerNew.setOnReady(() -> {
                playerNew.play();
                Transition trans1 = new Transition() {
                    {
                        setCycleDuration(Duration.millis(3000));
                    }

                    @Override
                    protected void interpolate(double frac) {
                        playerNew.setVolume(Math.pow(frac,2) * (sldVolume.getValue() / 100.0));
                    }
                };
                trans1.setOnFinished(a -> {
                    playerNew.volumeProperty().bind(sldVolume.valueProperty().divide(100.0));
                    player.stop();
                    player = playerNew;
                });
                double v = player.getVolume();
                player.volumeProperty().unbind();
                Transition trans2 = new Transition() {
                    final MediaPlayer p = player;
                    {
                        setCycleDuration(Duration.millis(3000));
                    }
                    @Override
                    protected void interpolate(double frac) {
                       if(player==p) player.setVolume(v-(Math.pow(frac,2) * v));
                       else stop();
                    }
                };
                trans1.play();
                trans2.play();
            });
        }

    }
}

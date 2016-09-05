package view;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Created by Marcus Baetz on 19.08.2016.
 *
 * @author Marcus Bätz
 */
public class SoundPlayer extends TitledPane {
    private final Button btnPlay = new Button();
    private final Slider sldVolume = new Slider();
    private final Slider sldBalance = new Slider();
    private final CheckBox chkLoop = new CheckBox("L");
    private final ProgressBar prbStatus = new ProgressBar();
    private final ImageView imgPlay = new ImageView(new Image("icons/1471628977_play.png", 9, 9, true, true));
    private final ImageView imgPause = new ImageView(new Image("icons/1471629017_pause.png", 9, 9, true, true));
    private MediaPlayer player = null;
    private Media media = null;


    public SoundPlayer(final MediaPlayer player) {
        this.media = player.getMedia();
        this.player = new MediaPlayer(media);
        btnPlay.setGraphic(imgPlay);
        btnPlay.setOnAction(a -> play());
        ImageView imgStop = new ImageView(new Image("icons/1471629021_stop.png", 9, 9, true, true));
        Button btnStop = new Button();
        btnStop.setGraphic(imgStop);
        btnStop.setOnAction(a -> stop());
        sldVolume.setValue(30);
        sldVolume.setMin(0);
        sldVolume.setMax(100);
        sldVolume.setMajorTickUnit(50);
        sldVolume.setMinorTickCount(10);

        sldBalance.setValue(0);
        sldBalance.setMin(-50);
        sldBalance.setMax(50);
        sldBalance.setMajorTickUnit(50);
        sldBalance.setMinorTickCount(10);
        chkLoop.setOnAction(a -> {
                    if (this.player != null) {
                        if (chkLoop.isSelected())
                            this.player.setCycleCount(MediaPlayer.INDEFINITE);
                        else
                            this.player.setCycleCount(1);
                    }
                }
        );
        Label lblBalance = new Label("Balance: ");
        lblBalance.textProperty().bind(Bindings.format("Balance: %.0f", sldBalance.valueProperty()));
        Label lblVolume = new Label("Volume: ");
        lblVolume.textProperty().bind(Bindings.format("Volume: %.0f", sldVolume.valueProperty()));
        setContent(
                new VBox(new HBox(5, btnPlay, btnStop, chkLoop), lblBalance,
                        sldBalance, lblVolume, sldVolume));
        this.setGraphic(prbStatus);
        this.setContentDisplay(ContentDisplay.BOTTOM);
        prbStatus.setMouseTransparent(true);

        stop();

    }


    private void stop() {
        if (player != null) {
            player.stop();
            player = new MediaPlayer(media);
            player.volumeProperty().bind(sldVolume.valueProperty().divide(100.0));
            player.balanceProperty().bind(sldBalance.valueProperty().divide(50.0));
            player.setOnEndOfMedia(() -> {
                stop();
                if (chkLoop.isSelected()) {
                    play();
                }
            });
            ChangeListener<Duration> changeListener = (a, b, c) -> prbStatus.setProgress(1.0 * player.getCurrentTime().toMillis() / player.getMedia().getDuration().toMillis());
            player.currentTimeProperty().addListener(changeListener);
            btnPlay.setGraphic(imgPlay);
            // player.currentTimeProperty().removeListener(changeListener);
            prbStatus.setProgress(0.0);
        }
    }

    public void stopPlayer(){
        player.stop();
    }

    private void play() {
        if (player.getStatus() == MediaPlayer.Status.PLAYING) {
            player.pause();
            btnPlay.setGraphic(imgPlay);
        } else {
            if (player.getStatus() != MediaPlayer.Status.PAUSED) {

            }
            player.play();
            btnPlay.setGraphic(imgPause);
        }
    }
}

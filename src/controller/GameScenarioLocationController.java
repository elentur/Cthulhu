package controller;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.Game;
import model.Scenario;
import model.character.NPC;
import model.character.PC;
import model.interfaces.ILocation;
import view.ImageViewDialog;
import view.InnsmouthMap;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class GameScenarioLocationController implements Initializable, Observer {
    private static final String FILE_IMAGES_DEFAULT_JPG = "file:images/default.jpg";
    @FXML
    private WebView webLocation;
    @FXML
    private ComboBox<Scenario> cmbGameScenario;
    @FXML
    private ComboBox<ILocation> cmbGameLocation;
    @FXML
    private ComboBox<NPC> cmbGameNPCs;
    @FXML
    private ScrollPane scpCharacters;
    @FXML
    private HBox hbxGameLocation;
    @FXML
    private ImageView imgGameLocation;

    private InnsmouthMap map;

    private final Game game = Game.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.addObserver(this);
        hbxGameLocation.visibleProperty().bind(cmbGameLocation.getSelectionModel().selectedItemProperty().isNotNull());
        cmbGameNPCs.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (!cmbGameNPCs.getSelectionModel().isEmpty())
                game.setActualCharacter(cmbGameNPCs.getSelectionModel().getSelectedItem());
        });
        try {

            map = new InnsmouthMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        update(null, Game.SCENARIOS);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        if ((int) arg == Game.SCENARIOS)
            cmbGameScenario.setItems(FXCollections.observableArrayList(game.getScenarios()));
        else if (game.getActualScenario() != null && (int) arg == Game.ASCENARIO) {
            cmbGameScenario.getSelectionModel().select(game.getActualScenario());
            cmbGameLocation.setItems(FXCollections.observableArrayList(
                    game.getActualScenario().getLocations()
            ));
            cmbGameNPCs.setItems(FXCollections.observableArrayList(
                    game.getActualScenario().getNpcs()
            ));
            FlowPane pane = new FlowPane();
            for (PC pc : game.getActualScenario().getPcs()) {
                ImageView imgView = new ImageView(new Image(pc.getImage().getImage(), 80, 80, true, false, true));
                imgView.setOnMouseClicked(a -> game.setActualCharacter(pc));
                pane.getChildren().add(imgView);
            }
            scpCharacters.setContent(pane);
        } else if ((int) arg == Game.LOCATIONS)
            cmbGameLocation.setItems(FXCollections.observableArrayList(game.getActualScenario().getLocations()));
        else if ((int) arg == Game.ALOCATION) {
            WebEngine webEngine = webLocation.getEngine();
            if (game.getActualLocation() != null) {
                cmbGameLocation.getSelectionModel().select(game.getActualLocation());
                webEngine.loadContent(game.getActualLocation().getDescription().replace(
                        "contenteditable=\"true\"", "contenteditable=\"false\""
                ));
                imgGameLocation.setImage(loadImage());
            } else {
                webEngine.loadContent("");
                cmbGameLocation.getSelectionModel().clearSelection();

            }
        } else if ((int) arg == Game.ACHARACTER) {
            if (game.getActualCharacter() != null) {
                if (game.getActualCharacter() instanceof PC) {
                    cmbGameNPCs.getSelectionModel().clearSelection();
                } else if (game.getActualCharacter() instanceof NPC) {
                    cmbGameNPCs.getSelectionModel().select((NPC) game.getActualCharacter());
                }
            }
        } else if ((int) arg == Game.IMAGE) imgGameLocation.setImage(loadImage());
        else if ((int) arg == Game.CHARACTERIMAGE) {
            if (game.getActualScenario() == null) return;
            FlowPane pane = new FlowPane();
            for (PC pc : game.getActualScenario().getPcs()) {
                ImageView imgView = new ImageView(new Image(pc.getImage().getImage(), 80, 80, true, false, true));
                imgView.setOnMouseClicked(a -> game.setActualCharacter(pc));
                pane.getChildren().add(imgView);
            }
            scpCharacters.setContent(pane);
        }
    }

    @FXML
    private void previousImage() {
        game.previousImage();
    }

    @FXML
    private void nextImage() {
        game.nextImage();
    }

    @FXML
    private void setUpScenario() {
        game.setActualScenario(cmbGameScenario.getSelectionModel().getSelectedItem());

    }


    public void setUpLocation() {
        game.setActualLocation(cmbGameLocation.getSelectionModel().getSelectedItem());

    }

    private Image loadImage() {
        if (game.getActualImage() == null)
            return new Image(FILE_IMAGES_DEFAULT_JPG, true);
        return new Image(game.getActualImage().getImage(), true);
    }


    public void openFullScreenImage(Event event) {
        ImageViewDialog dialog = new ImageViewDialog(((ImageView) event.getSource()).getImage());
        dialog.showAndWait();
    }


    public void showInnsmouthMap() {
        if (map != null) map.showAndWait();
    }
}

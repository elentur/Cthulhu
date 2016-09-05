package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import model.Game;
import model.Scenario;
import model.character.ACharacter;
import model.character.NPC;
import model.character.PC;
import model.interfaces.ILocation;
import view.EditorTextField;
import view.ImageViewDialog;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class CreateScenarioController implements Initializable, Observer {
    private static final String FILE_IMAGES_DEFAULT_JPG = "file:images/default.jpg";
    private static final String CSS_ROOT_STYLE_CSS = "/css/rootStyle.css";
    private static final String MY_RESOURCES = "myResources";

    private static final String SCENARIOS = "scenarios/";
    private static final String SCN = "*.scn";

    @FXML
    private VBox vbxCreateScenario;
    @FXML
    private ComboBox<Scenario> cmbCSChooseScenario;
    @FXML
    private VBox vbxCSScenarioOptions;
    @FXML
    private ComboBox<ACharacter> cmbCSPC;
    @FXML
    private ComboBox<ACharacter> cmbCSNPC;
    @FXML
    private ComboBox<ILocation> cmbCSChooseLocation;
    @FXML
    private TitledPane tlpCSLocationOptions;
    @FXML
    private EditorTextField txtCSDescription;
    @FXML
    private ImageView imgCSLocation;

    private final Game game = Game.getInstance();
    public final static ResourceBundle resources = ResourceBundle.getBundle(MY_RESOURCES);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.addObserver(this);
        vbxCSScenarioOptions.visibleProperty().bind(cmbCSChooseScenario.getSelectionModel().selectedItemProperty().isNotNull());
        tlpCSLocationOptions.visibleProperty().bind(cmbCSChooseLocation.getSelectionModel().selectedItemProperty().isNotNull());

        cmbCSPC.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (!cmbCSPC.getSelectionModel().isEmpty())
                game.setActualCharacter(cmbCSPC.getSelectionModel().getSelectedItem());
        });
        cmbCSNPC.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (!cmbCSNPC.getSelectionModel().isEmpty())
                game.setActualCharacter(cmbCSNPC.getSelectionModel().getSelectedItem());
        });
        txtCSDescription.focusedProperty().addListener(a -> {
            if (!txtCSDescription.isFocused()) {
                ILocation l = game.getActualLocation();
                if (l != null) {
                    game.setActualLocation(l);
                }
            }
        });
        txtCSDescription.addEventHandler(InputEvent.ANY, event -> {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) onCSLOcationDescriptionChanged();
        });

        update(null, Game.SCENARIOS);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        if ((int) arg == Game.SCENARIOS)
            cmbCSChooseScenario.setItems(FXCollections.observableArrayList(game.getScenarios()));
        else if ((int) arg == Game.LOCATIONS)
            cmbCSChooseLocation.setItems(FXCollections.observableArrayList(game.getActualScenario().getLocations()));
        else if ((int) arg == Game.ASCENARIO) {
            if (game.getActualScenario() != null) {
                cmbCSChooseScenario.getSelectionModel().select(game.getActualScenario());
                cmbCSChooseLocation.setItems(FXCollections.observableArrayList(game.getActualScenario().getLocations()));

            }
        } else if ((int) arg == Game.ACHARACTER) {
            if (game.getActualCharacter() != null) {
                if (game.getActualCharacter() instanceof PC) {
                    cmbCSNPC.getSelectionModel().clearSelection();
                    if (cmbCSPC.getItems().contains(game.getActualCharacter()))
                        cmbCSPC.getSelectionModel().select(game.getActualCharacter());
                    else
                        cmbCSPC.getSelectionModel().clearSelection();
                } else if (game.getActualCharacter() instanceof NPC) {
                    cmbCSPC.getSelectionModel().clearSelection();
                    if (cmbCSNPC.getItems().contains(game.getActualCharacter()))
                        cmbCSNPC.getSelectionModel().select(game.getActualCharacter());
                    else
                        cmbCSNPC.getSelectionModel().clearSelection();
                }
            }
        } else if ((int) arg == Game.ALOCATION) {
            cmbCSChooseLocation.getSelectionModel().select(game.getActualLocation());
        } else if ((int) arg == Game.IMAGE) imgCSLocation.setImage(loadImage());
    }

    @FXML
    private void newScenario() {
        TextInputDialog dialog = new TextInputDialog(resources.getString("newScenario"));
        dialog.setTitle(resources.getString("newScenario"));
        dialog.setContentText(resources.getString("nameForScenario"));
        dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
        dialog.showAndWait().ifPresent(name -> game.newScenario(name));


    }


    @FXML
    private void saveScenario() {
        Scenario scenario = game.getActualScenario();
        if (scenario != null) {
            if (scenario.getPath() != null && scenario.getPath().exists()) {
                game.saveScenario(scenario.getPath());
                return;
            }
            File file = getFileChooser(scenario.getName())
                    .showSaveDialog(vbxCreateScenario.getScene().getWindow());
            if (file != null) game.saveScenario(file);
        }
    }

    private FileChooser getFileChooser(String name) {
        FileChooser chooser = new FileChooser();
        String[] nameSplit = name.split(" ");
        String nameCamelCase = "";
        for (String part : nameSplit) {
            nameCamelCase += part.substring(0, 1).toUpperCase() + part.substring(1);
        }
        chooser.setInitialFileName(nameCamelCase);
        chooser.setInitialDirectory(new File(CreateScenarioController.SCENARIOS));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(CreateScenarioController.SCN.toUpperCase(), CreateScenarioController.SCN));
        return chooser;
    }

    @FXML
    private void newLocation() {
        TextInputDialog dialog = new TextInputDialog(resources.getString("newLocation"));
        dialog.setTitle(resources.getString("newLocation"));
        dialog.setContentText(resources.getString("nameForLocation"));
        dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
        dialog.showAndWait().ifPresent(name -> {
            game.newLocation(name);

        });
    }


    private boolean dialogOK(String string) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setContentText(MessageFormat.format(resources.getString("doYouReallyWantTo.0.delete"), string));
        dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
        return dialog.showAndWait().get() == ButtonType.OK;
    }

    @FXML
    private void newImage() {
        FileChooser f = new FileChooser();
        File file = f.showOpenDialog(vbxCreateScenario.getScene().getWindow());
        if (file != null) {
            game.addImage(file);
            imgCSLocation.setImage(loadImage());
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
    private void setUpScenario(Event event) {
        ComboBox<Scenario> cmb = (ComboBox<Scenario>) event.getSource();
        game.setActualScenario(cmb.getSelectionModel().getSelectedItem());
        if (game.getActualScenario() != null) {
            cmbCSChooseLocation.setItems(FXCollections.observableArrayList(
                    game.getActualScenario().getLocations()
            ));


            cmbCSNPC.setItems(FXCollections.observableArrayList(
                    game.getActualScenario().getNpcs()
            ));

            cmbCSPC.setItems(FXCollections.observableArrayList(
                    game.getActualScenario().getPcs()
            ));


        }
    }


    public void setUpLocation(Event event) {
        game.setActualLocation(((ComboBox<ILocation>) event.getSource()).getSelectionModel().getSelectedItem());
        if (game.getActualLocation() != null) {
            txtCSDescription.setHtmlText(game.getActualLocation().getDescription());
            imgCSLocation.setImage(loadImage());
        } else {
            txtCSDescription.setHtmlText("");
            imgCSLocation.setImage(null);

        }

    }

    private Image loadImage() {
        if (game.getActualImage() == null)
            return new Image(FILE_IMAGES_DEFAULT_JPG, true);
        return new Image(game.getActualImage().getImage(), true);
    }


    public void deleteScenario() {
        if (game.getActualScenario() != null && dialogOK(game.getActualScenario().getName())) {
            game.deleteScenario();
        }
    }

    public void deleteLocation() {
        if (game.getActualLocation() != null && dialogOK(game.getActualLocation().getName())) {
            game.deleteLocation();
        }
    }

    public void openFullScreenImage(Event event) {
        ImageViewDialog dialog = new ImageViewDialog(((ImageView) event.getSource()).getImage());
        dialog.showAndWait();
    }

    private class TextUpdateThread extends Thread {
        HTMLEditor a;

        public void setA(final HTMLEditor a) {
            this.a = a;
        }

        @Override
        public void run() {
            try {
                sleep(1000);
                Platform.runLater(() -> {
                    game.setLocationText(a.getHtmlText());
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    TextUpdateThread thread = new TextUpdateThread();

    public void onCSLOcationDescriptionChanged() {
        if (thread.isAlive()) return;
        thread = new TextUpdateThread();
        thread.setA(txtCSDescription);
        thread.start();
    }

    public void deleteImage() {
        if (dialogOK(resources.getString("thisImage"))) {
            game.deleteImage();
        }
    }


    public void addCharacter() {
        if (game.getActualCharacter() != null) {
            if (game.getActualCharacter() instanceof NPC) {
                game.addNPCToScenario();
                cmbCSNPC.setItems(FXCollections.observableArrayList(game.getActualScenario().getNpcs()));
                cmbCSNPC.getSelectionModel().select(game.getActualCharacter());
            } else {
                game.addPCToScenario();
                cmbCSPC.setItems(FXCollections.observableArrayList(game.getActualScenario().getPcs()));
                cmbCSPC.getSelectionModel().select(game.getActualCharacter());
            }
        }
    }

    public void removeCharacter() {
        if (game.getActualCharacter() != null) {
            if (game.getActualCharacter() instanceof NPC) {
                if (dialogOK(game.getActualCharacter().getName())) {
                    game.getActualScenario().getNpcs().remove(game.getActualCharacter());
                    game.setActualCharacter(null);
                    cmbCSNPC.setItems(FXCollections.observableArrayList(game.getActualScenario().getNpcs()));
                }
            } else if (game.getActualCharacter() instanceof PC) {
                if (dialogOK(game.getActualCharacter().getName())) {
                    game.getActualScenario().getPcs().remove(game.getActualCharacter());
                    game.setActualCharacter(null);
                    cmbCSPC.setItems(FXCollections.observableArrayList(game.getActualScenario().getPcs()));
                }
            }
        }
    }

}

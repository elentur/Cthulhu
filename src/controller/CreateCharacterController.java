package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.Game;
import model.Scenario;
import model.character.ACharacter;
import model.character.NPC;
import model.character.PC;
import model.properties.Ability;
import model.properties.Attribute;
import model.properties.CommonProperty;
import view.HorizontalTitledPane;

import java.io.File;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class CreateCharacterController implements Initializable, Observer {
    private static final String FILE_IMAGES_DEFAULT_JPG = "/images/default.jpg";
    private static final String CSS_ROOT_STYLE_CSS = "/css/rootStyle.css";
    private static final String MY_RESOURCES = "myResources";
    private static final String NPC = "npc/";
    private static final String NPC1 = "*.npc";
    private static final String PC = "pc/";
    private static final String PC1 = "*.pc";
    private static final String SCENARIOS = "scenarios/";
    private static final String SCN = "*.scn";
    @FXML
    private VBox vbxCreateCharacter;
    @FXML
    private ComboBox<ACharacter> cmbCCAllPC;
    @FXML
    private ComboBox<ACharacter> cmbCCAllNPC;
    @FXML
    private TitledPane tlpCCCharacterOptions;
    @FXML
    private ImageView imgCCCharacter;
    @FXML
    private TableView<CommonProperty> tblCommon;
    @FXML
    private TableView<Attribute> tblAttribute;
    @FXML
    private TableView<Attribute> tblPoints;
    @FXML
    private TableView<Ability> tblAbilities;
    @FXML
    private TableColumn<Ability, String> tbcAbilitiesName;
    @FXML
    private TableView<Ability> tblWeaponAbilities;
    @FXML
    private HBox vbxSpecifics;
    @FXML
    private TextArea txtCCDescription;
    @FXML
    private HBox vbxPCSpecifics;
    @FXML
    private TextArea txtCCIdeology;
    @FXML
    private TextArea txtCCPersons;
    @FXML
    private TextArea txtCCPlaces;
    @FXML
    private TextArea txtCCTreasures;
    @FXML
    private TextArea txtCCTraits;
    @FXML
    private TextArea txtCCInjuries;
    @FXML
    private TextArea txtCCPhobics;
    @FXML
    private TextArea txtCCTomes;
    @FXML
    private TextArea txtCCEncounters;
    @FXML
    private TextArea txtCCPossessions;
    @FXML
    private TextArea txtCCCash;

    private final Game game = Game.getInstance();
    public final static ResourceBundle resources = ResourceBundle.getBundle(MY_RESOURCES);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.addObserver(this);
        tlpCCCharacterOptions.visibleProperty().bind(
                cmbCCAllPC.getSelectionModel().selectedItemProperty().isNotNull()
                        .or(cmbCCAllNPC.getSelectionModel().selectedItemProperty().isNotNull()));

        cmbCCAllPC.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (!cmbCCAllPC.getSelectionModel().isEmpty())
                game.setActualCharacter(cmbCCAllPC.getSelectionModel().getSelectedItem());
        });
        cmbCCAllNPC.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (!cmbCCAllNPC.getSelectionModel().isEmpty())
                game.setActualCharacter(cmbCCAllNPC.getSelectionModel().getSelectedItem());
        });

        vbxPCSpecifics.getChildren().stream().filter(n -> n instanceof HorizontalTitledPane).forEach(n -> {
            HorizontalTitledPane p = (HorizontalTitledPane) n;
            p.closedProperty().addListener((a, b, c) -> {
                if (!c) {
                    vbxPCSpecifics.getChildren().stream().filter(n1 -> n1 instanceof HorizontalTitledPane).forEach(n1 -> {
                        HorizontalTitledPane p1 = (HorizontalTitledPane) n1;
                        if (p1 != p) p1.setClosed(true);
                    });
                }
            });
        });
        update(null, Game.CHARACTERS);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        if ((int) arg == Game.CHARACTERS) {
            cmbCCAllPC.setItems(FXCollections.observableArrayList(
                    game.getPcs()
            ));
            cmbCCAllNPC.setItems(FXCollections.observableArrayList(
                    game.getNpcs()
            ));
        } else if ((int) arg == Game.ACHARACTER) {
            if (game.getActualCharacter() != null) {
                ACharacter c = game.getActualCharacter();
                if (c instanceof PC) {
                    cmbCCAllPC.getSelectionModel().select(c);
                    cmbCCAllNPC.getSelectionModel().clearSelection();
                    vbxPCSpecifics.setVisible(true);
                } else if (c instanceof NPC) {
                    cmbCCAllNPC.getSelectionModel().select(c);
                    cmbCCAllPC.getSelectionModel().clearSelection();
                    vbxPCSpecifics.setVisible(false);
                }
                refreshTables();
            }
        }
    }


    @FXML
    private void saveScenario() {
        Scenario scenario = game.getActualScenario();
        if (scenario != null) {
            if (scenario.getPath() != null && scenario.getPath().exists()) {
                game.saveScenario(scenario.getPath());
                return;
            }

            File file = getFileChooser(scenario.getName(), SCENARIOS, SCN)
                    .showSaveDialog(vbxCreateCharacter.getScene().getWindow());
            if (file != null) game.saveScenario(file);
        }
    }

    private FileChooser getFileChooser(String name, String directory, String extension) {
        FileChooser chooser = new FileChooser();
        String[] nameSplit = name.split(" ");
        String nameCamelCase = "";
        for (String part : nameSplit) {
            nameCamelCase += part.substring(0, 1).toUpperCase() + part.substring(1);
        }
        chooser.setInitialFileName(nameCamelCase);
        chooser.setInitialDirectory(new File(directory));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extension.toUpperCase(), extension));
        return chooser;
    }


    private boolean dialogOK(String string) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setContentText(MessageFormat.format(resources.getString("doYouReallyWantTo.0.delete"), string));
        dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
        return dialog.showAndWait().get() == ButtonType.OK;
    }


    private Image loadCharacterImage() {
        if (game.getActualCharacter().getImage() == null)
            return new Image(FILE_IMAGES_DEFAULT_JPG,true);
        return new Image(game.getActualCharacter().getImage().getImage(),true);
    }


    public void newPC() {
        TextInputDialog dialog = new TextInputDialog("name");
        dialog.setTitle(resources.getString("newPlayer"));
        dialog.setContentText(resources.getString("nameOfCharacter"));
        dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> game.newPC(name));
    }


    public void newNPC() {
        TextInputDialog dialog = new TextInputDialog("name");
        dialog.setTitle(resources.getString("newNonPlayerCharacter"));
        dialog.setContentText(resources.getString("nameOfCharacter"));
        dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
        dialog.showAndWait().ifPresent(name -> game.newNPC(name));
    }


    private void refreshTables() {
        if (game.getActualCharacter() == null) return;
        imgCCCharacter.setImage(loadCharacterImage());
        tblCommon.getItems().clear();
        tblCommon.setItems(
                FXCollections.observableArrayList(game.getActualCharacter().getCommonProperties()));
        tlpCCCharacterOptions.setText(game.getActualCharacter().getName());

        tblAttribute.getItems().clear();
        tblAttribute.setItems(
                FXCollections.observableArrayList(game.getActualCharacter().getAttributes()));
        tblPoints.getItems().clear();
        tblPoints.setItems(
                FXCollections.observableArrayList(game.getActualCharacter().getMagicPoints(),
                        game.getActualCharacter().getHitPoints(),
                        game.getActualCharacter().getSanity(),
                        game.getActualCharacter().getLuck(),
                        game.getActualCharacter().getBuild(),
                        game.getActualCharacter().getDamageBonus()));
        tblAbilities.getItems().clear();
        tblAbilities.setItems(
                FXCollections.observableArrayList(game.getActualCharacter().getAbilities()));
        tbcAbilitiesName.setSortType(TableColumn.SortType.ASCENDING);
        tblAbilities.getSortOrder().add(tbcAbilitiesName);
        tblWeaponAbilities.getItems().clear();
        tblWeaponAbilities.setItems(
                FXCollections.observableArrayList(game.getActualCharacter().getFightAbilities())
        );

        txtCCDescription.setText(game.getActualCharacter().getDescription());
        if (game.getActualCharacter() instanceof PC) {
            PC c = (PC) game.getActualCharacter();
            txtCCIdeology.setText(c.getIdeology());
            txtCCPersons.setText(c.getImportantPersons());
            txtCCPlaces.setText(c.getImportantPlaces());
            txtCCTreasures.setText(c.getTreasuredPossessions());
            txtCCTraits.setText(c.getTraits());
            txtCCInjuries.setText(c.getInjuries());
            txtCCPhobics.setText(c.getPhobias());
            txtCCTomes.setText(c.getTomes());
            txtCCEncounters.setText(c.getEncounters());
            txtCCPossessions.setText(c.getPossessions());
            txtCCCash.setText(c.getCash());
        }
    }


    public void randomAttributes() {
        game.randomAttributes();
    }

    public void addWeaponAbility() {
        TextInputDialog dialog = new TextInputDialog(resources.getString("rangedWeapons"));
        dialog.setTitle(resources.getString("newFightAbility"));
        dialog.setContentText(resources.getString("nameOfAbility"));
        dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (name.equals("")) return;
            if (game.getActualCharacter() != null) {
                Ability a = game.addWeaponAbility(name);
                tblWeaponAbilities.requestFocus();
                tblWeaponAbilities.getSelectionModel().select(a);
                tblWeaponAbilities.scrollTo(a);
            }
        });
    }

    public void removeWeaponAbility() {
        if (!tblWeaponAbilities.getSelectionModel().isEmpty() &&
                dialogOK(tblWeaponAbilities.getSelectionModel().getSelectedItem().getName())) {
            game.removeWeaponAbility(tblWeaponAbilities.getSelectionModel().getSelectedItem());
        }
    }

    public void addAbility() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(resources.getString("newAbility"));
        dialog.setContentText(resources.getString("nameOfAbility"));
        dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (name.equals("")) return;
            if (game.getActualCharacter() != null) {
                Ability a = game.addAbility(name);
                tblAbilities.requestFocus();
                tblAbilities.getSelectionModel().select(a);
                tblAbilities.scrollTo(a);
            }
        });
    }

    public void removeAbility() {
        if (!tblAbilities.getSelectionModel().isEmpty() &&
                dialogOK(tblAbilities.getSelectionModel().getSelectedItem().getName())) {
            game.removeAbility(tblAbilities.getSelectionModel().getSelectedItem());
        }
    }

    public void newCharacterImage() {
        FileChooser f = new FileChooser();
        f.setInitialDirectory(new File("images/"));
        File file = f.showOpenDialog(vbxCreateCharacter.getScene().getWindow());
        if (file != null) {
            game.addCharacterImage(file);
            imgCCCharacter.setImage(loadCharacterImage());
        }
    }

    public void saveCharacter() {
        ACharacter character = game.getActualCharacter();
        if (character != null) {
            if (character.getPath() != null && character.getPath().exists()) {
                game.saveCharacter(character.getPath());
                return;
            }
            FileChooser chooser = getFileChooser(character.getName(), NPC, NPC1);
            if (character instanceof PC) chooser = getFileChooser(character.getName(), PC, PC1);
            File file = chooser.showSaveDialog(vbxCreateCharacter.getScene().getWindow());
            if (file != null) game.saveCharacter(file);
        }

    }

    private class TextUpdateThread extends Thread{
        TextArea a;
        public void setA(final TextArea a) {
            this.a = a;
        }

        @Override
        public void run()  {
            try {
                sleep(1000);
                Platform.runLater(() -> {
                    int cursor = a.getCaretPosition();
                    game.setCharacterText(a.getId(),a.getText());
                    a.positionCaret(cursor);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
    TextUpdateThread thread = new TextUpdateThread();

    public void onCCTextAreaChanged(Event event) {
        if (!(event.getSource() instanceof TextArea))return;
        TextArea a = (TextArea)event.getSource();
        if(thread.isAlive())return;
        thread = new TextUpdateThread();
        thread.setA(a);
        thread.start();
    }

    public void deleteCharacter() {
        if (game.getActualCharacter() != null && dialogOK(game.getActualCharacter().getName())) {
                game.deleteCharacter();
        }
    }


}

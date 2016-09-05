package controller;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.Game;
import model.character.ACharacter;
import model.character.PC;
import model.properties.APoint;
import model.properties.Attribute;
import view.DiceButton;
import view.ImageViewDialog;

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
public class GameCharacterController implements Initializable, Observer {

    private static final String FILE_IMAGES_DEFAULT_JPG = "/images/default.jpg";
    private static final String CSS_ROOT_STYLE_CSS = "/css/rootStyle.css";
    private static final String BTN_DICE = "#btnDice";
    @FXML
    private WebView webGameCharacter;
    @FXML
    private TitledPane tlpGameCharacter;
    @FXML
    private ImageView imgGameCharacter;
    @FXML
    private ListView<Attribute> lstWeaponAbilities;

    @FXML
    private ListView<Attribute> lstAbilities;
    @FXML
    private ListView<Attribute> lstAttributes;
    @FXML
    private ListView<APoint> lstPoints;

    private final Game game = Game.getInstance();
    private ResourceBundle resources;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.addObserver(this);
        this.resources = ResourceBundle.getBundle("myResources");
    }


    @Override
    public void update(final Observable o, final Object arg) {
        if ((int) arg == Game.ACHARACTER) refreshTables();
    }

    private Image loadCharacterImage() {
        if (game.getActualCharacter().getImage() == null)
            return new Image(FILE_IMAGES_DEFAULT_JPG, true);
        return new Image(game.getActualCharacter().getImage().getImage(), true);
    }


    private void refreshTables() {
        if (game.getActualCharacter() == null) return;
        if (game.getActualScenario() == null) return;
        if (!game.getActualScenario().getNpcs().contains(game.getActualCharacter()) &&
                !game.getActualScenario().getPcs().contains(game.getActualCharacter())
                ) return;
        imgGameCharacter.setImage(loadCharacterImage());
        lstPoints.setItems(null);
        lstPoints.setItems(FXCollections.observableArrayList(game.getActualCharacter().getHitPoints(),
                game.getActualCharacter().getSanity(),
                game.getActualCharacter().getLuck(),
                game.getActualCharacter().getMagicPoints(),
                game.getActualCharacter().getDamageBonus(),
                game.getActualCharacter().getBuild()));
        lstAttributes.setItems(null);
        lstAttributes.setItems(FXCollections.observableArrayList(game.getActualCharacter().getAttributes()));
        lstAbilities.setItems(null);
        lstAbilities.setItems(FXCollections.observableArrayList(game.getActualCharacter().getAbilities()));
        lstWeaponAbilities.setItems(null);
        lstWeaponAbilities.setItems(FXCollections.observableArrayList(game.getActualCharacter().getFightAbilities()));

        ACharacter c = game.getActualCharacter();
        String content =
                "<h3>" + c.getCommonProperties().get(0).getName() + ":</h3>" + c.getCommonProperties().get(0).getValue() + "<br>" +
                        "<h3>" + c.getCommonProperties().get(1).getName() + ":</h3>" + c.getCommonProperties().get(1).getValue() + "<br>" +
                        "<h3>" + c.getCommonProperties().get(2).getName() + ":</h3>" + c.getCommonProperties().get(2).getValue() + "<br>" +
                        "<h3>" + c.getCommonProperties().get(3).getName() + ":</h3>" + c.getCommonProperties().get(3).getValue() + "<br>" +
                        "<h3>" + c.getCommonProperties().get(4).getName() + ":</h3>" + c.getCommonProperties().get(4).getValue() + "<br>" +
                        "<h3>" + c.getCommonProperties().get(5).getName() + ":</h3>" + c.getCommonProperties().get(5).getValue() + "<br>" +
                        "<h3>" + c.getCommonProperties().get(6).getName() + ":</h3>" + c.getCommonProperties().get(6).getValue() + "<br>" +
                        "<h3>" + c.getCommonProperties().get(7).getName() + ":</h3>" + c.getCommonProperties().get(7).getValue() + "<br>";


        if (c instanceof PC) {
            PC d = (PC) c;
            content +=
                    "<h3>" + resources.getString("description") + "</h3>" + c.getDescription() + "<br>" +
                            "<h3>" + resources.getString("ideology") + "</h3>" + d.getIdeology() + "<br>" +
                            "<h3>" + resources.getString("importantPersons") + "</h3>" + d.getImportantPersons() + "<br>" +
                            "<h3>" + resources.getString("importantPlaces") + "</h3>" + d.getImportantPlaces() + "<br>" +
                            "<h3>" + resources.getString("treasuredPossessions") + "</h3>" + d.getTreasuredPossessions() + "<br>" +
                            "<h3>" + resources.getString("traits") + "</h3>" + d.getTraits() + "<br>" +
                            "<h3>" + resources.getString("injuries") + "</h3>" + d.getInjuries() + "<br>" +
                            "<h3>" + resources.getString("phobias") + "</h3>" + d.getPhobias() + "<br>" +
                            "<h3>" + resources.getString("tomes") + "</h3>" + d.getTomes() + "<br>" +
                            "<h3>" + resources.getString("encounters") + "</h3>" + d.getEncounters() + "<br>" +
                            "<h3>" + resources.getString("possessions") + "</h3>" + d.getPossessions() + "<br>" +
                            "<h3>" + resources.getString("cash") + "</h3>" + d.getCash() + "<br>";
        } else {
            content = "<h3>" + resources.getString("description") + "</h3>" + c.getDescription() + "<br>" + content;
        }
        content = "<html dir=\"ltr\"><head></head><body contenteditable=\"false\" bgcolor=\"#909090\">" +content +"</body></html>";
        WebEngine webEngine = webGameCharacter.getEngine();
        webEngine.loadContent(content);
        tlpGameCharacter.setText(game.getActualCharacter().getName());
        tlpGameCharacter.setVisible(true);
    }


    public void rollOnAttribute(Event event) {
        if (((MouseEvent) event).getClickCount() == 2) {
            ListView lst = (ListView) event.getSource();
            Attribute a = (Attribute) lst.getSelectionModel().getSelectedItem();
            if (a != null) {
                Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
                dialog.setContentText(MessageFormat.format(resources.getString("reallyRollOn"), a.getName()));
                dialog.getDialogPane().getStylesheets().add(CSS_ROOT_STYLE_CSS);
                if (dialog.showAndWait().get() == ButtonType.OK) {
                    DiceButton btnDice = (DiceButton) tlpGameCharacter.getParent().lookup(BTN_DICE);
                    if (btnDice != null) {
                        btnDice.setState(0);
                        btnDice.setResult(a.rollOn());
                    }


                }
            }
        }
    }

    public void openFullScreenImage(Event event) {
        ImageViewDialog dialog = new ImageViewDialog(((ImageView) event.getSource()).getImage());
        dialog.showAndWait();
    }
}



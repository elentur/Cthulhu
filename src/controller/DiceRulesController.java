package controller;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Dice;
import model.properties.Attribute;
import view.DiceButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class DiceRulesController implements Initializable {

    private static final String MY_RESOURCES = "myResources";
    @FXML
    private ComboBox<String> cmbRules;
    @FXML
    private TextArea txtRules;
    @FXML
    private Label txtSuccess;
    @FXML
    private TextField txtBonus2;
    @FXML
    private TextField txtRollDice;
    @FXML
    private TextField txtBonus1;
    @FXML
    private Slider sldNumber;
    @FXML
    private Slider sldBonusDice;
    @FXML
    private DiceButton btnDice;

    private ResourceBundle resources;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = ResourceBundle.getBundle(MY_RESOURCES);
        btnDice.numberProperty().bind(sldNumber.valueProperty());
        btnDice.stateProperty().addListener(a -> {
            if (btnDice.getState() != 0) {
                txtBonus1.setText("");
                txtBonus2.setText("");
            } else {
                updateDiceView();
            }
        });
        txtBonus2.disableProperty().bind(
                sldBonusDice.valueProperty().isNotEqualTo(-2).and(
                        sldBonusDice.valueProperty().isNotEqualTo(2)).or(
                        btnDice.stateProperty().isNotEqualTo(0)
                ));

        txtBonus1.disableProperty().bind(sldBonusDice.valueProperty().isEqualTo(0).or(
                btnDice.stateProperty().isNotEqualTo(0)));
        sldBonusDice.disableProperty().bind(btnDice.stateProperty().isNotEqualTo(0));
        sldBonusDice.valueProperty().addListener((a, b, c) -> {
            sldBonusDice.setValue(c.intValue());
            updateDiceView();
        });

        sldNumber.disableProperty().bind(btnDice.stateProperty().isEqualTo(0));
        sldNumber.valueProperty().addListener((a, b, c) -> {
            sldNumber.setValue(c.intValue());
        });

        FilteredList<String> filteredItems = new FilteredList<>(cmbRules.getItems(), p -> true);

        cmbRules.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = cmbRules.getEditor();
            final String selected = cmbRules.getSelectionModel().getSelectedItem();

            Platform.runLater(() -> {
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> item.toLowerCase().contains(newValue.toLowerCase()));
                }
                if (selected != null && selected.split("\n")[0].trim().equals(editor.getText())) {
                    filteredItems.setPredicate(item -> true);
                    editor.selectAll();
                }
                if (editor.getText().equals("")) {
                    cmbRules.hide();
                } else {
                    cmbRules.show();
                }
            });
        });
        btnDice.resultProperty().addListener(a->updateDiceView());
        cmbRules.setItems(filteredItems);
    }

    public void rollDice() {
        if (btnDice.getState() == 0) {
            btnDice.setResult(Dice.roll(null));
        } else {
            txtSuccess.setText("");
            txtRollDice.setText(Dice.roll(btnDice.getNumber(), btnDice.getState()) + "");
        }
    }

    private void updateDiceView() {
        if (btnDice.getResult() != null) {
            int value;
            if (sldBonusDice.getValue() == 0) {
                txtRollDice.setText(btnDice.getResult().getValues()[0] + "");
                value = btnDice.getResult().getValues()[0];
                txtBonus1.setText("");
                txtBonus2.setText("");
            } else {
                List<Integer> res = new ArrayList<>();
                for (int i = 0; i <= Math.abs(sldBonusDice.getValue()); i++) {
                    res.add(btnDice.getResult().getValues()[i]);
                }
                if (sldBonusDice.getValue() > 0) {
                    Collections.sort(res);
                    txtRollDice.setText(res.get(0) + "");
                    txtBonus1.setText(res.get(1) + "");
                    if (sldBonusDice.getValue() == 2) txtBonus2.setText(res.get(2) + "");
                    else txtBonus2.setText("");
                    value = res.get(0);
                } else {
                    Collections.sort(res, Collections.reverseOrder());
                    txtRollDice.setText(res.get(0) + "");
                    txtBonus1.setText(res.get(1) + "");
                    if (sldBonusDice.getValue() == -2) txtBonus2.setText(res.get(2) + "");
                    else txtBonus2.setText("");
                    value = res.get(0);
                }
            }
            if (btnDice.getResult().getProperty() != null && btnDice.getResult().getProperty() instanceof Attribute) {
                Attribute a = (Attribute) btnDice.getResult().getProperty();
                if (value <= a.getExtreme())
                    txtSuccess.setText(resources.getString("success") + ": " + resources.getString("extreme"));
                else if (value <= a.getDifficult())
                    txtSuccess.setText(resources.getString("success") + ": " + resources.getString("difficult"));
                else if (value <= a.getNormal())
                    txtSuccess.setText(resources.getString("success") + ": " + resources.getString("normal"));
                else if ((a.getValue() < 50 && value >= 96) || (a.getValue() >= 50 && value == 100)) {
                    txtSuccess.setText(resources.getString("success") + ": " + resources.getString("patzer"));
                } else {
                    txtSuccess.setText(resources.getString("success") + ": " + resources.getString("failure"));
                }
            } else {
                txtSuccess.setText(resources.getString("success") + ":");
            }

        }
    }

    public void showRule() {
        if(cmbRules.getSelectionModel().isEmpty())return;
        if (cmbRules.getSelectionModel().getSelectedItem().contains("\n"))
            txtRules.setText(cmbRules.getSelectionModel().getSelectedItem());
    }

}

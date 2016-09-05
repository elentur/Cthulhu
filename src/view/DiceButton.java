package view;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import model.Result;

/**
 * Created by Marcus Baetz on 17.08.2016.
 *
 * @author Marcus Bätz
 */
public class DiceButton extends Button {

    public int getState() {
        return state.get();
    }

    public IntegerProperty stateProperty() {
        return state;
    }

    private static final String W = "W";
    private final IntegerProperty state = new SimpleIntegerProperty(0);
    private final IntegerProperty number = new SimpleIntegerProperty(1);
    private final StringProperty dice = new SimpleStringProperty();
    public final ObjectProperty<Result> result = new SimpleObjectProperty<>();

    public Result getResult() {
        return result.get();
    }

    public ObjectProperty<Result> resultProperty() {
        return result;
    }

    public void setResult(final Result result) {
        this.result.set(result);
    }

    public String getDice() {
        return dice.get();
    }

    public StringProperty diceProperty() {
        return dice;
    }

    public void setDice(final String dice) {
        this.dice.set(dice);
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(final int number) {
        this.number.set(number);
    }

    public DiceButton() {

        setOnScroll(a -> {
            if (a.getDeltaY() < 0) {
                state.setValue((state.getValue() + 1) % 21);
            } else if (a.getDeltaY() > 0) {
                int x = (state.getValue() - 1);
                if (x < 0) x = 20;
                state.setValue(x % 21);
            }
        });
        textProperty().bind(dice);
        state.addListener(a -> updateText());
        number.addListener(a -> updateText());
        updateText();
    }

    private void updateText() {
        if (state.getValue() == 0) {
            dice.setValue(W + 100);
        } else {
            dice.setValue(number.getValue() + W + state.getValue());
        }
    }

    public void setState(final int state) {
        this.state.set(state);
    }
}

package view;

import javafx.scene.control.Label;
import javafx.util.StringConverter;

/**
 * Created by Marcus Baetz on 07.08.2016.
 *
 * @author Marcus Bätz
 */
public class LabelStringConverter extends StringConverter<Label> {
    @Override
    public String toString(final Label object) {
        return object.getText();
    }

    @Override
    public Label fromString(final String string) {
        return new Label(string);
    }
}



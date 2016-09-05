package view;

import javafx.util.StringConverter;
import model.Scenario;

/**
 * Created by Marcus Baetz on 07.08.2016.
 *
 * @author Marcus Bätz
 */
public class ScenarioStringConverter extends StringConverter<Scenario> {
    @Override
    public String toString(final Scenario object) {
        return object.getName();
    }

    @Override
    public Scenario fromString(final String string) {
        return null;
    }
}



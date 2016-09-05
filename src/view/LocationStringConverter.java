package view;

import javafx.util.StringConverter;
import model.Location;

/**
 * Created by Marcus Baetz on 07.08.2016.
 *
 * @author Marcus Bätz
 */
public class LocationStringConverter extends StringConverter<Location> {
    @Override
    public String toString(final Location object) {
        return object.getName();
    }

    @Override
    public Location fromString(final String string) {
        return null;
    }
}



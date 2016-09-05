package view;

import javafx.util.StringConverter;
import model.character.ACharacter;

/**
 * Created by Marcus Baetz on 07.08.2016.
 *
 * @author Marcus Bätz
 */
public class CharacterStringConverter extends StringConverter<ACharacter> {
    @Override
    public String toString(final ACharacter object) {
        return object.getName();
    }

    @Override
    public ACharacter fromString(final String string) {
        return null;
    }
}



package view;

import javafx.util.StringConverter;

/**
 * Created by Marcus Baetz on 07.08.2016.
 *
 * @author Marcus Bätz
 */
public class RuleStringConverter extends StringConverter<String> {
    @Override
    public String toString(final String object) {
        try{
            return object.split("\n")[0].trim();
        }catch (Exception e){
            return "Fehler in "+ object;
        }

    }

    @Override
    public String fromString(final String string) {
        return string;
    }
}



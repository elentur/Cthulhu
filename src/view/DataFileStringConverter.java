package view;

import javafx.util.StringConverter;
import model.fileFormats.DataFile;

/**
 * Created by Marcus Baetz on 07.08.2016.
 *
 * @author Marcus Bätz
 */
public class DataFileStringConverter extends StringConverter<DataFile> {
    @Override
    public String toString(final DataFile object) {

        return object.getName();
    }

    @Override
    public DataFile fromString(final String string) {
        return null;
    }
}



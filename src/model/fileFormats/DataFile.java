package model.fileFormats;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Marcus Baetz on 05.08.2016.
 *
 * @author Marcus Bätz
 */
public class DataFile implements Serializable {
    /**
     * The Path of the Sound-File
     */
    private String path;

    /**
     * Represents the name of the object
     */
    private String name;

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        if (path == null) throw new IllegalArgumentException();
        if (Objects.equals(path, "")) return;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        if (name == null) throw new IllegalArgumentException();
        if (name == "") return;
        this.name = name;
    }


}

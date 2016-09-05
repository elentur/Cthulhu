package model.properties;

import java.io.Serializable;

/**
 * Created by Marcus Baetz on 11.08.2016.
 *
 * @author Marcus Bätz
 */
public class CommonProperty implements Serializable {
    private String name;
    private String value;

    public CommonProperty(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}

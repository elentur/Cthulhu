package model.properties;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class Dodge extends Ability {


    public Dodge(final String name, final int value) {
        super(name,value);
    }

    @Override
    protected void refresh(final int value) {
        setValue(value/2);
    }
}

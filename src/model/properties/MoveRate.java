package model.properties;

import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class MoveRate extends Attribute {

    private final Attribute dexterity;
    private final Attribute size;
    private final Attribute strength;

    public MoveRate(Attribute dexterity, Attribute size, Attribute strength){
        super(ResourceBundle.getBundle("myResources").getString("moveRate"));
        this.dexterity = dexterity;
        this.size = size;
        this.strength =strength;
        refresh(0);
    }

    @Override
    protected void refresh(final int value) {
        if(dexterity.value < size.value & strength.value<size.value )
            setValue(7);
        else if( dexterity.value > size.value & strength.value>size.value )
            setValue(9);
        else setValue(8);
    }
}

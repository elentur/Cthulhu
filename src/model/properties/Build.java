package model.properties;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class Build extends APoint {
    private final Attribute size;
    private final Attribute strength;
    private String bonus;

    public Build( Attribute size, Attribute strength){
        name="Statur";
        this.size = size;
        this.strength =strength;
        refresh(0);
    }
    @Override
    protected void refresh(final int value) {
        if(strength.getValue()+size.getValue()<64)
            setValue(-2);
        else if(strength.getValue()+size.getValue()<84)
            setValue(-1);
        else if(strength.getValue()+size.getValue()<124)
            setValue(0);
        else if(strength.getValue()+size.getValue()<164)
            setValue(1);
        else if(strength.getValue()+size.getValue()<204)
            setValue(2);
        else{
            int v = (strength.getValue()+size.getValue()-204)/80+3;
            setValue(v);
        }
    }
}

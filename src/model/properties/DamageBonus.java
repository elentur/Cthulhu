package model.properties;

import model.Dice;
import model.Result;

import java.util.ResourceBundle;

/**
 * Created by Marcus Baetz on 06.08.2016.
 *
 * @author Marcus Bätz
 */
public class DamageBonus extends APoint {
    private static final String _1W4 = "1w4";
    private static final String _1W6 = "1w6";
    private static final String _W6 = "w6";
    private final Attribute size;
    private final Attribute strength;
    private String bonus;

    public DamageBonus( Attribute size, Attribute strength){
        name= ResourceBundle.getBundle("myResources").getString("damageBonus");
        this.size = size;
        this.strength =strength;
        refresh(0);
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(final String bonus) {
        this.bonus = bonus;
    }

    @Override
    protected void refresh(final int value) {
        if(strength.getValue()+size.getValue()<64)
            setBonus("-2");
        else if(strength.getValue()+size.getValue()<84)
            setBonus("-1");
        else if(strength.getValue()+size.getValue()<124)
            setBonus("0");
        else if(strength.getValue()+size.getValue()<164)
            setBonus(_1W4);
        else if(strength.getValue()+size.getValue()<204)
            setBonus(_1W6);
        else{
            int v = (strength.getValue()+size.getValue()-204)/80+2;
            setBonus(v+ _W6);
        }
    }

    @Override
    public Result rollOn() {
        String[] b = getBonus().split("w");
        try {
            int a = Integer.parseInt(b[0]);
            int c = Integer.parseInt(b[1]);
            return new Result(new int[]{Dice.roll(a,c),0,0},this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

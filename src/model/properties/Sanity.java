package model.properties;

import java.util.ResourceBundle;

/**
 * Represents the Sanitypoints of an Character
 */
public class Sanity extends APoint {

    private final Ability cthulhu;
    private final Attribute mana;

    public Sanity(Ability cthulhu, Attribute mana){
        this.cthulhu = cthulhu;
        this.mana =mana;
        name = ResourceBundle.getBundle("myResources").getString("sanity");}

    @Override
    protected void refresh(final int value) {
        this.MAX = 99 -cthulhu.getValue();
        this.value = mana.getValue();

    }

    /**
     * Represents the temporal Sanity of a Character
     */
    private boolean temporalSanity;

    /**
     * Represents the indefinite Sanity of a Character
     */
    private boolean indefiniteSanity;

    public boolean isTemporalSanity() {
        return temporalSanity;
    }

    public void setTemporalSanity(final boolean temporalSanity) {
        this.temporalSanity = temporalSanity;
    }

    public boolean isIndefiniteSanity() {
        return indefiniteSanity;
    }

    public void setIndefiniteSanity(final boolean indefiniteSanity) {
        this.indefiniteSanity = indefiniteSanity;
    }
}
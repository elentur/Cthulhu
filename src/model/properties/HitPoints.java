package model.properties;

import java.util.ResourceBundle;

/**
 * Represents the Hitpoints of a Character
 */
public class HitPoints extends APoint {

    private final Attribute size;
    private final Attribute constituion;

    /**
     * Default constructor
     */
    public HitPoints(Attribute size, Attribute constitution) {
        this.size =size;
        this.constituion =constitution;
        MAX =20;
        name= ResourceBundle.getBundle("myResources").getString("hitPoints");
        refresh(0);
    }

    /**
     * Represents if a Character has a major wound or not
     */
    private boolean MajorWound;
    @Override
    protected void refresh(final int value) {
        setValue((size.getValue()+constituion.getValue())/10);
    }
    public boolean isMajorWound() {
        return MajorWound;
    }

    public void setMajorWound(final boolean majorWound) {
        MajorWound = majorWound;
    }
}
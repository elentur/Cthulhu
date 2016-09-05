package model.properties;

/**
 * This abstract Class Represents all Typs of Points
 */
public abstract class APoint extends Attribute {

    /**
     * Default constructor
     */
    public APoint() {
    }

    /**
     * Represents the maximum value for this kind of point
     */
    protected int MAX = 99;

    @Override
    public void setValue(final int value) {
        super.setValue(value);
        if(this.value>MAX) this.value=MAX;
    }
    public String getBonus(){return getValue()+"";}
}
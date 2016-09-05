package model.properties;

import java.util.ResourceBundle;

/**
 * Represents the Magicpoints of an Character
 */
public class MagicPoints extends APoint {

    /**
     * Default constructor
     */
    public MagicPoints() {
        MAX =26;
        name= ResourceBundle.getBundle("myResources").getString("magicPoints");
    }
    @Override
    protected void refresh(final int value) {
       setValue(value/5);
    }
}
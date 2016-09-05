package model;

import model.properties.AProperty;

import java.io.Serializable;

/**
 * A Result of a Dice Roll
 */
public class Result implements Serializable {



    /**
     * All Rolled values with bonus or malus values
     */
    private final int[] values;



    public int[] getValues() {
        return values;
    }

    public AProperty getProperty() {
        return property;
    }
    /**
     * Represents the Property that the roll is take on
     */
    private final AProperty property;

    public Result( final int[] values, final AProperty property) {
        this.values = values;
        this.property = property;
    }
}
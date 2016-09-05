package model.properties;

import model.Dice;
import model.Result;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the Primitvie Abstract Property class for Attributes, Points and abilities
 */
public abstract class AProperty implements Serializable {
    private final Set<AProperty> observer = new HashSet<>();
    /**
     * 
     */
    protected int value;

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
        for(AProperty prop : observer){
            prop.refresh(value);
        }
    }

    protected void refresh(int value){}

    public void addObserver(AProperty property){
        observer.add(property);
    }
    public void removeObserver(AProperty property){
        observer.remove(property);
    }

    /**
     * Rolls on this Attribute and returns a Result
     * @return a Result for this Roll
     */
    public Result rollOn() {
        return Dice.roll(this);
    }

}
package model.properties;

import model.Dice;

import java.util.ResourceBundle;

/**
 * Represents the Luck of a Character
 */
public class Luck extends APoint {

    /**
     * Default constructor
     */
    public Luck() {
        name= ResourceBundle.getBundle("myResources").getString("Luck");

        setValue(Dice.roll(3,6)*5);
    }

}
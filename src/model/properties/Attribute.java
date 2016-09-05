package model.properties;

/**
 * This Abstract Class Represents a Prototype of an Attribute
 */
public  class Attribute extends AProperty {

    /**
     * Default constructor
     */
    public Attribute() {
        this("");
    }
    public Attribute(final String name) {
        this.name=name;
        this.value = 0;
    }

    /**
     * 
     */
    protected String name;

    /**
     * Returns the regular value of this Attribute
     * @return
     */
    public int getNormal() {
        return value;
    }

    /**
     * Returns the difficult value of this Attribute equals half value
     * @return
     */
    public int getDifficult() {
        return value/2;
    }

    /**
     * Returns the difficult value of this Attribute equals half value
     * @return
     */
    public int getExtreme() {
        return value/5;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
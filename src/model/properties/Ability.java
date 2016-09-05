package model.properties;

/**
 * This Abstract Class Represents a Prototype of an Ability
 */
public  class Ability extends Attribute {

    private final boolean markable;
    private boolean deletable = false;

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(final boolean deletable) {
        this.deletable = deletable;
    }

    /**
     * Default constructor
     */
    public Ability() {
        this("",0,true);
    }
    public Ability(final String name, final int value){
        this(name,value,true);
    }
    public Ability(final String name, final int value, final boolean markable){
        this.name=name;
        this.value=value;
        this.markable =markable;
    }

    /**
     * Represents if this Ability is marked or not
     */
    private boolean marked;

    /**
     * Represent if the Ability is markable
     * @return
     */
    public boolean isMarkable() {
        return markable;
    }

    /**
     * Represents whether the Ability is marked or not
     * @return
     */
    public boolean isMarked() {
        return marked;
    }

}
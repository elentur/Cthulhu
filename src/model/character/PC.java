package model.character;

/**
 * Represents a Player Character
 */
public class PC extends ACharacter {

    public String getIdeology() {
        return ideology;
    }

    public void setIdeology(final String ideology) {
        this.ideology = ideology;
    }

    public String getImportantPlaces() {
        return importantPlaces;
    }

    public void setImportantPlaces(final String importantPlaces) {
        this.importantPlaces = importantPlaces;
    }

    public String getImportantPersons() {
        return importantPersons;
    }

    public void setImportantPersons(final String importantPersons) {
        this.importantPersons = importantPersons;
    }

    public String getTreasuredPossessions() {
        return treasuredPossessions;
    }

    public void setTreasuredPossessions(final String treasuredPossessions) {
        this.treasuredPossessions = treasuredPossessions;
    }

    public String getTraits() {
        return traits;
    }

    public void setTraits(final String traits) {
        this.traits = traits;
    }

    public String getInjuries() {
        return injuries;
    }

    public void setInjuries(final String injuries) {
        this.injuries = injuries;
    }

    public String getPhobias() {
        return phobias;
    }

    public void setPhobias(final String phobias) {
        this.phobias = phobias;
    }

    public String getTomes() {
        return tomes;
    }

    public void setTomes(final String tomes) {
        this.tomes = tomes;
    }

    public String getEncounters() {
        return encounters;
    }

    public void setEncounters(final String encounters) {
        this.encounters = encounters;
    }

    public String getPossessions() {
        return possessions;
    }

    public void setPossessions(final String possessions) {
        this.possessions = possessions;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(final String cash) {
        this.cash = cash;
    }

    /**
     * Default constructor
     */
    public PC() {
    }

    /**
     * Represents the Ideology/Beliefs of the Character
     */
    private String ideology = "";

    /**
     * Represents the important places of the Character
     */
    private String importantPlaces = "";

    /**
     * Represents the important persons of the Character
     */
    private String importantPersons = "";

    /**
     * Represents the treausred possessions of the Character
     */
    private String treasuredPossessions = "";

    /**
     * Represents the traits of the Character
     */
    private String traits = "";

    /**
     * Represents the injuries and scars  of the Character
     */
    private String injuries = "";

    /**
     * Represents the phobias/manias of the Character
     */
    private String phobias = "";

    /**
     * Represents the arcane tomes, spells and artifacts of the Character
     */
    private String tomes = "";

    /**
     * Represents the encounters with strange Entities of the Character
     */
    private String encounters = "";

    /**
     * Represents the gear and possessions of the Character
     */
    private String possessions = "";

    /**
     * Represents the cash and assets of the Character
     */
    private String cash = "";

}
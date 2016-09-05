package model;

import model.character.NPC;
import model.character.PC;
import model.interfaces.ILocation;
import model.interfaces.IScenario;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An Implementation of IScenario
 */
public class Scenario implements IScenario, Serializable {

    public List<ILocation> getLocations() {
        return locations;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public List<PC> getPcs() {
        return pcs;
    }

    public String getName() {

        return name;
    }

    protected File path;

    /**
     * The Path where this File is saved
     */
    public File getPath() {
        return path;
    }

    public void setPath(final File path) {
        this.path = path;
    }
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Represents the locations of this scenario
     */
    private final List<ILocation> locations;

    /**
     * Represents the Non-Player-Characters of this Scenario
     */
    private final List<NPC> npcs;

    /**
     * Represents the Player-Characters of this Scenario
     */
    private final List<PC> pcs;

    /**
     * Represents the Name of this Scenario
     */
    private String name = "new Scenario";

    public Scenario() {
        this.locations = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.pcs = new ArrayList<>();
        this.name = "";
    }
}
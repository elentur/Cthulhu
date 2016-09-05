package model;

import model.character.ACharacter;
import model.fileFormats.Image;
import model.interfaces.ILocation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the specific Location of a Scenario
 */
public class Location implements ILocation,Serializable {

    /**
     * Default constructor
     */
    public Location() {
        images = new ArrayList<>();
        npcs = new ArrayList<>();
    }

    /**
     * Represents all the informations of this location
     */
    private String description="<html dir=\"ltr\"><head></head><body contenteditable=\"true\" bgcolor=\"#909090\"></body></html>";

    /**
     * Represents the Non-Player-Characters of this location
     */
    private final List<ACharacter> npcs;

    /**
     * Represents the images for this location
     */
    private final List<Image> images;

    /**
     * Represents the name of this Location
     */
    private String name = "new Location";

    public List<ACharacter> getNpcs() {
        return npcs;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
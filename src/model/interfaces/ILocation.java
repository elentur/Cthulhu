package model.interfaces;

import model.character.ACharacter;
import model.fileFormats.Image;

import java.util.*;

/**
 * This Inteface Represents a Specific Location in a Scenario
 */
public interface ILocation {
    List<ACharacter> getNpcs() ;

    List<Image> getImages();

    String getDescription();

    void setDescription(final String description);

    String getName();

    void setName(final String name);
}
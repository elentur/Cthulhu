package model.character;

import model.fileFormats.Image;
import model.properties.*;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Represents the abstract Prototyp vor PC and NPC
 */
public abstract class ACharacter implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String MY_RESOURCES = "myResources";
    protected final long uuid;
    public ACharacter() {

        ResourceBundle resourceBundle = ResourceBundle.getBundle(MY_RESOURCES);
        this.image = new Image();
        this.attributes = new ArrayList<>();
        this.abilities = new ArrayList<>();
        this.fightAbilities = new ArrayList<>();
        this.commonProperties = new ArrayList<>();

/**
 * Represents the Name of a Character
 */
        commonProperties.add(new CommonProperty(resourceBundle.getString("name"), resourceBundle.getString("newName")));
        commonProperties.add(new CommonProperty(resourceBundle.getString("player"), resourceBundle.getString("newPlayer")));
        commonProperties.add(new CommonProperty(resourceBundle.getString("age"), "21"));
        commonProperties.add(new CommonProperty(resourceBundle.getString("occupation"), resourceBundle.getString("newOccupation")));
        commonProperties.add(new CommonProperty(resourceBundle.getString("sex"), resourceBundle.getString("male")));
        commonProperties.add(new CommonProperty(resourceBundle.getString("birthday"), resourceBundle.getString("newBirthday")));
        commonProperties.add(new CommonProperty(resourceBundle.getString("birthplace"), resourceBundle.getString("newBirthplace")));
        commonProperties.add(new CommonProperty(resourceBundle.getString("residence"), resourceBundle.getString("newResidence")));


        Attribute strength = new Attribute(resourceBundle.getString("strength"));
        attributes.add(strength);
        Attribute constitution = new Attribute(resourceBundle.getString("constitution"));
        attributes.add(constitution);
        Attribute size = new Attribute(resourceBundle.getString("size"));
        attributes.add(size);
        Attribute dexterity = new Attribute(resourceBundle.getString("dexterity"));
        attributes.add(dexterity);
        attributes.add(new Attribute(resourceBundle.getString("appearance")));
        attributes.add(new Attribute(resourceBundle.getString("intelligence")));
        Attribute mana = new Attribute(resourceBundle.getString("mana"));
        attributes.add(mana);
        Attribute education = new Attribute(resourceBundle.getString("education"));
        attributes.add(education);
        MoveRate moveRate = new MoveRate(dexterity, size, strength);
        dexterity.addObserver(moveRate);
        size.addObserver(moveRate);
        strength.addObserver(mana);
        attributes.add(moveRate);

        this.luck = new Luck();
        this.magicPoints = new MagicPoints();
        mana.addObserver(magicPoints);
        this.hitPoints = new HitPoints(size, constitution);
        size.addObserver(hitPoints);
        constitution.addObserver(hitPoints);

        Ability mythos = new Ability(resourceBundle.getString("cthulhuMythos"), 0, false);
        this.sanity = new Sanity(mythos, mana);
        mythos.addObserver(sanity);
        mana.addObserver(sanity);
        this.hitPoints.addObserver(constitution);
        this.magicPoints.addObserver(mana);

        abilities.add(new Ability(resourceBundle.getString("anthropology"), 1));
        abilities.add(new Ability(resourceBundle.getString("archeology"), 1));
        abilities.add(new Ability(resourceBundle.getString("driveCar"), 20));
        abilities.add(new Ability(resourceBundle.getString("libraryUse"), 20));
        abilities.add(new Ability(resourceBundle.getString("accounting"), 5));
        abilities.add(new Ability(resourceBundle.getString("charm"), 15));
        abilities.add(mythos);
        abilities.add(new Ability(resourceBundle.getString("intimidate"), 15));
        abilities.add(new Ability(resourceBundle.getString("electricReparation"), 10));
        abilities.add(new Ability(resourceBundle.getString("firstAid"), 30));
        abilities.add(new Ability(resourceBundle.getString("creditRating"), 0, false));
        abilities.add(new Ability(resourceBundle.getString("foreignLanguage"), 1));
        abilities.add(new Ability(resourceBundle.getString("history"), 5));
        abilities.add(new Ability(resourceBundle.getString("craftArt"), 5));
        abilities.add(new Ability(resourceBundle.getString("listen"), 20));
        abilities.add(new Ability(resourceBundle.getString("sleightOfHand"), 1));
        abilities.add(new Ability(resourceBundle.getString("climb"), 20));
        abilities.add(new Ability(resourceBundle.getString("mechanicReparation"), 1));
        abilities.add(new Ability(resourceBundle.getString("medicine"), 1));
        OwnLanguage ownLanguage = new OwnLanguage(resourceBundle.getString("ownLanguage"), education.getNormal());
        education.addObserver(ownLanguage);
        abilities.add(ownLanguage);
        abilities.add(new Ability(resourceBundle.getString("natureScience"), 1));
        abilities.add(new Ability(resourceBundle.getString("occultism"), 5));
        abilities.add(new Ability(resourceBundle.getString("orientation"), 10));
        abilities.add(new Ability(resourceBundle.getString("psychoAnalysis"), 1));
        abilities.add(new Ability(resourceBundle.getString("psychology"), 10));
        abilities.add(new Ability(resourceBundle.getString("law"), 5));
        abilities.add(new Ability(resourceBundle.getString("ride"), 5));
        abilities.add(new Ability(resourceBundle.getString("lockSmith"), 1));
        abilities.add(new Ability(resourceBundle.getString("heavyMachine"), 1));
        abilities.add(new Ability(resourceBundle.getString("swim"), 20));
        abilities.add(new Ability(resourceBundle.getString("jump"), 20));
        abilities.add(new Ability(resourceBundle.getString("track"), 10));
        abilities.add(new Ability(resourceBundle.getString("drive"), 1));
        abilities.add(new Ability(resourceBundle.getString("survival"), 10));
        abilities.add(new Ability(resourceBundle.getString("fastTalk"), 5));
        abilities.add(new Ability(resourceBundle.getString("persuade"), 10));
        abilities.add(new Ability(resourceBundle.getString("stealth"), 20));
        abilities.add(new Ability(resourceBundle.getString("spotHidden"), 25));
        abilities.add(new Ability(resourceBundle.getString("disguise"), 5));
        abilities.add(new Ability(resourceBundle.getString("throw"), 20));
        abilities.add(new Ability(resourceBundle.getString("appraise"), 5));
        Dodge dodge = new Dodge(resourceBundle.getString("dodge"), dexterity.getDifficult());
        dexterity.addObserver(dodge);
        fightAbilities.add(dodge);
        fightAbilities.add(new Ability(resourceBundle.getString("meleeUnarmed"), 25));
        fightAbilities.add(new Ability(resourceBundle.getString("rangeWeaponPistol"), 20));
        fightAbilities.add(new Ability(resourceBundle.getString("rangeWeaponGun"), 25));

        damageBonus = new DamageBonus(size, strength);
        build = new Build(size, strength);
        size.addObserver(damageBonus);
        size.addObserver(build);
        strength.addObserver(damageBonus);
        strength.addObserver(build);
        uuid = hashCode();
    }


    private final List<CommonProperty> commonProperties;

    public void setImage(final Image image) {
        this.image = image;
    }

    protected Image image;

    /**
     * Represents the buil of a Character
     */
    protected final Build build;

    /**
     * Represents the damage bonus of a Character
     */
    protected final DamageBonus damageBonus;

    /**
     * Represents the Attributes of the Character
     */
    protected final List<Attribute> attributes;

    /**
     * Represents the abilities of the Character
     */
    protected final List<Ability> abilities;

    /**
     * Represents the fight abilities of the Character
     */
    protected final List<Ability> fightAbilities;

    /**
     * represents the Luck of a Character
     */
    protected final Luck luck;

    /**
     * Represents the Magic points of a Character
     */
    protected final MagicPoints magicPoints;

    /**
     * Represents the Hitpoints of a Character
     */
    protected final HitPoints hitPoints;

    /**
     * Represents the Sanity of a Character
     */
    protected final Sanity sanity;

    /**
     * Represents the description of a Character
     */
    protected String description = "";


    /**
     * The Path where this File is saved
     */
    protected File path;

    public File getPath() {
        return path;
    }

    public void setPath(final File path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<Ability> getFightAbilities() {

        return fightAbilities;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<CommonProperty> getCommonProperties() {
        return commonProperties;
    }

    public Image getImage() {
        return image;
    }

    public Build getBuild() {
        return build;
    }

    public DamageBonus getDamageBonus() {
        return damageBonus;
    }

    public Luck getLuck() {
        return luck;
    }

    public MagicPoints getMagicPoints() {
        return magicPoints;
    }

    public Sanity getSanity() {
        return sanity;
    }

    public HitPoints getHitPoints() {
        return hitPoints;
    }

    public String getName() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(MY_RESOURCES);
        for (CommonProperty p : commonProperties) {
            if (p.getName().equals(resourceBundle.getString("name")))
                return p.getValue();
        }
        return resourceBundle.getString("no.name");
    }

    public void setName(final String name) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(MY_RESOURCES);
        commonProperties.stream().filter(p -> p.getName().equals(resourceBundle.getString("name"))).forEach(p -> p.setValue(name));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ACharacter)) return false;

        ACharacter that = (ACharacter) o;

        return uuid==(that.uuid);


    }

    @Override
    public int hashCode() {
        int result = (int) (uuid ^ (uuid >>> 32));
        result = 31 * result + getCommonProperties().hashCode();
        result = 31 * result + getImage().hashCode();
        result = 31 * result + getBuild().hashCode();
        result = 31 * result + getDamageBonus().hashCode();
        result = 31 * result + getAttributes().hashCode();
        result = 31 * result + getAbilities().hashCode();
        result = 31 * result + getFightAbilities().hashCode();
        result = 31 * result + getLuck().hashCode();
        result = 31 * result + getMagicPoints().hashCode();
        result = 31 * result + getHitPoints().hashCode();
        result = 31 * result + getSanity().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }
}
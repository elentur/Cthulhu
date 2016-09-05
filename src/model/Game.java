package model;

import model.character.ACharacter;
import model.character.NPC;
import model.character.PC;
import model.fileFormats.Image;
import model.fileFormats.Music;
import model.fileFormats.Sound;
import model.interfaces.ILocation;
import model.properties.AProperty;
import model.properties.Ability;
import model.properties.CommonProperty;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by Marcus Baetz on 07.08.2016.
 *
 * @author Marcus Bätz
 */
public class Game {
    private static final String TXT_CC_DESCRIPTION = "txtCCDescription";
    private static final String TXT_CC_IDEOLOGY = "txtCCIdeology";
    private static final String TXT_CC_PERSONS = "txtCCPersons";
    private static final String TXT_CC_PLACES = "txtCCPlaces";
    private static final String TXT_CC_TREASURES = "txtCCTreasures";
    private static final String TXT_CC_TRAITS = "txtCCTraits";
    private static final String TXT_CC_INJURIES = "txtCCInjuries";
    private static final String TXT_CC_PHOBICS = "txtCCPhobics";
    private static final String TXT_CC_TOMES = "txtCCTomes";
    private static final String TXT_CC_ENCOUNTERS = "txtCCEncounters";
    private static final String TXT_CC_POSSESSIONS = "txtCCPossessions";
    private static final String TXT_CC_CASH = "txtCCCash";
    public static final int SCENARIOS=0;
    public static final int ASCENARIO=1;
    public static final int LOCATIONS=2;
    public static final int ALOCATION=3;
    public static final int CHARACTERS=4;
    public static final int ACHARACTER=5;
    public static final int IMAGE=6;
    public static final int CHARACTERIMAGE = 7;


    private final Set<Scenario> scenarios;
    private final Set<NPC> npcs;
    private final Set<PC> pcs;
    private final Set<Music> musics;
    private final Set<Sound> sounds;

    private final ArrayList<Observer> observer = new ArrayList<>();
    private Scenario actualScenario;
    private ILocation actualLocation;
    private int imageCount;
    private Image actualImage;
    private ACharacter actualCharacter;
    private Music actualMusic;

    private static final Game instance = new Game();

    public static Game getInstance() {
        return instance;
    }

    /**
     * Add a new Scenario to the List of Scenarios
     *
     * @param scenario the Scenario that have to be added
     */
    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
        refresh(SCENARIOS);
    }

    /**
     * Returns all Scenarios
     *
     * @return A Set of all Scenarios
     */
    public Set<Scenario> getScenarios() {
        return scenarios;
    }

    /**
     * Returns all NPCs
     *
     * @return A Set of all NPCs
     */
    public Set<NPC> getNpcs() {
        return npcs;
    }

    /**
     * Returns all PCs
     *
     * @return A Set of all PCs
     */
    public Set<PC> getPcs() {
        return pcs;
    }

    /**
     * Returns all Musics
     *
     * @return A Set of all Musics
     */
    public Set<Music> getMusics() {
        return musics;
    }

    /**
     * Returns all Sounds
     *
     * @return A Set of all Sounds
     */
    public Set<Sound> getSounds() {
        return sounds;
    }

    /*
    Updates all Observers
     */
    private void refresh(int typ) {
        for (Observer obs : observer) {
            obs.update(null, typ);
        }
    }

    /**
     * Initiates all Sets
     */
    private Game() {
        npcs = loadNpcs();
        pcs = loadPcs();
        scenarios = loadScenarios();
        musics = loadMusics();
        sounds = loadSounds();
    }

    /*
        a loader function for Binary Files
     */
    private Object[] loadFile(File file) {

        if (file.exists()) {
            Object[] objects = new Object[file.list().length];
            int i = 0;
            for (String path : file.list()) {
                try {
                    String s = file.toPath() + "/" + path;
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(s));
                    objects[i] = in.readObject();
                    i++;
                    in.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return objects;
        }
        return new Object[0];
    }

    /*
        loads all Scenario *.scn Files from /scenarios and returns a Set with it
     */
    private Set<Scenario> loadScenarios() {
        try {
            Object[] o = loadFile(new File("scenarios/"));
            Scenario[] s = Arrays.copyOf(o, o.length, Scenario[].class);
            return new HashSet<>(Arrays.asList(s));
        } catch (ClassCastException e) {
            return new HashSet<>();
        }
    }

    /*
            loads all NPCs *.npc Files from /npc and returns a Set with it
         */
    private Set<NPC> loadNpcs() {
        try {
            Object[] o = loadFile(new File("npc/"));
            NPC[] s = Arrays.copyOf(o, o.length, NPC[].class);
            return new HashSet<>(Arrays.asList(s));
        } catch (ClassCastException e) {
            return new HashSet<>();
        }
    }

    /*
            loads all pc *.pc Files from /pc and returns a Set with it
         */
    private Set<PC> loadPcs() {
        try {
            Object[] o = loadFile(new File("pc/"));
            PC[] s = Arrays.copyOf(o, o.length, PC[].class);
            return new HashSet<>(Arrays.asList(s));
        } catch (ClassCastException e) {
            return new HashSet<>();
        }
    }

    /*
        loads all Musics *.mus Files from /musics and returns a Set with it
     */
    private Set<Music> loadMusics() {
        File file = new File("musics/");
        Set<Music> set = new HashSet<>();
        if (file.exists()) {
            for (String path : file.list()) {
                Music i = new Music();
                String[] name = path.split("\\.");
                i.setName(name[0]);
                i.setPath(path);
                set.add(i);
            }
        }
        return set;
    }

    /*
            loads all Sounds *.snd Files from /sounds and returns a Set with it
         */
    public Set<Sound> loadSounds() {
        File file = new File("sounds/");
        Set<Sound> set = new HashSet<>();
        if (file.exists()) {
            for (String path : file.list()) {
                Sound i = new Sound();
                String[] name = path.split("\\.");
                i.setName(name[0]);
                i.setPath(path);
                set.add(i);
            }
        }
        return set;
    }

    /**
     * Adds an Observer to the list of Observers
     *
     * @param observer a given Observer extending Observer
     */
    public void addObserver(final Observer observer) {
        this.observer.add(observer);
    }

    /**
     * Saves the actual Scenario under scenarios/ + 'name' + scn
     *
     * @param path The File name
     */
    public void saveScenario(final File path) {
        if (path == null) return;
        if (actualScenario != null)
            actualScenario.setPath(path);
        saveObject(path, actualScenario);
    }

    /*
        Saves a given Object at a given Filepath
     */
    private void saveObject(File file, Object object) {

        try {
            File directory = new File(file.getParent());
            if (!directory.exists()) Files.createDirectories(directory.toPath());

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(object);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the actual Scenario
     *
     * @param actualScenario the Scenario what have to be set actual
     */
    public void setActualScenario(final Scenario actualScenario) {

        if (this.actualScenario != actualScenario) {
            this.actualScenario = actualScenario;
            imageCount = 0;
            refresh(ASCENARIO);
        }
    }

    /**
     * Returns the actual Scenario
     *
     * @return the actual Scenario
     */
    public Scenario getActualScenario() {
        return actualScenario;
    }

    /**
     * Returns the actual Location
     *
     * @return the Actual Location
     */
    public ILocation getActualLocation() {
        return actualLocation;
    }

    /**
     * Sets the actual Location
     *
     * @param actualLocation the Location what have to be set actual
     */
    public void setActualLocation(final ILocation actualLocation) {
        this.actualLocation = actualLocation;
        imageCount = 0;
        if (actualLocation != null && !actualLocation.getImages().isEmpty()) {
            setActualImage(actualLocation.getImages().get(0));
        } else {
            actualImage = null;
        }

        refresh(ALOCATION);
    }

    /**
     * deletes a Scenario from list and from Filesystem
     */
    public void deleteScenario() {
        scenarios.remove(actualScenario);
        File file = new File("scenarios/" + actualScenario.getName() + ".scn");
        file.delete();
        setActualScenario(null);
        refresh(SCENARIOS);
    }

    /**
     * deletes a Location from list and from Filesystem
     */
    public void deleteLocation() {
        actualScenario.getLocations().remove(actualLocation);
        setActualLocation(null);
        refresh(LOCATIONS);
    }

    /**
     * Add a new Location to the list of Locations of the actual Scenario
     *
     * @param newLocation the new Location that have to be added
     */
    public void addLocation(final Location newLocation) {
        if (actualScenario != null) {
            actualScenario.getLocations().add(newLocation);
            refresh(LOCATIONS);
            setActualLocation(newLocation);

        }
    }

    /**
     * Adds an Image to the List of Images of the Actual Location
     *
     * @param file the path of the Imagefile
     */
    public void addImage(final File file) {
        if (actualLocation != null) {
            Image image = new Image();
            image.setName(file.getName());
            image.setPath(file.getPath());
            actualLocation.getImages().add(image);
            imageCount = actualLocation.getImages().indexOf(image);
            setActualImage(getActualLocation().getImages().get(imageCount));
        }
    }

    /**
     * Changes the actual image to the next in line
     */
    public void nextImage() {
        if (getActualLocation().getImages().size() > 0) {
            imageCount = (imageCount + 1) % getActualLocation().getImages().size();
            setActualImage(getActualLocation().getImages().get(imageCount));
        }
    }

    /**
     * Returns the actual image
     *
     * @return The actual image
     */
    public Image getActualImage() {
        return actualImage;
    }

    /**
     * Changes the actual image to the previous in line
     */
    public void previousImage() {
        if (getActualLocation().getImages().size() > 0) {
            imageCount = Math.abs(imageCount - 1) % getActualLocation().getImages().size();
            setActualImage(getActualLocation().getImages().get(imageCount));
        }
    }

    /**
     * Removes the Actual Image from the List of images of the actual location
     */
    public void deleteImage() {
        if (actualImage != null) {
            actualLocation.getImages().remove(actualImage);
            actualImage = null;
            nextImage();
            imageCount=0;
            refresh(IMAGE);
        }
    }

    /**
     * Sets an Image as the actual image
     *
     * @param actualImage the image that should be the actual image
     */
    public void setActualImage(final Image actualImage) {
        this.actualImage = actualImage;
        refresh(IMAGE);
    }

    /**
     * Creates a new PC and adds it to the list of pcs and sets it as actualCharacter
     */
    public void newPC(final String name) {
        PC pc = new PC();
        pc.setName(name);
        pcs.add(pc);
        refresh(Game.CHARACTERS);
        setActualCharacter(pc);
    }

    /**
     * Creates a new NPC and adds it to the list of npcs and sets it as actualCharacter
     */
    public void newNPC(final String name) {
        NPC npc = new NPC();
        npc.setName(name);
        npcs.add(npc);
        refresh(Game.CHARACTERS);
        setActualCharacter(npc);
    }

    /**
     * Returns the actual Character
     *
     * @return the Actual Character from typ ACharacter
     */
    public ACharacter getActualCharacter() {
        return actualCharacter;
    }

    /**
     * Sets the actual Character
     *
     * @param actualCharacter the new actual Character from typ ACharacter
     */
    public void setActualCharacter(final ACharacter actualCharacter) {
        this.actualCharacter = actualCharacter;
        refresh(ACHARACTER);
    }

    /**
     * generates for the actual Character random attribute values using the rules from edition 7
     */
    public void randomAttributes() {
        if (actualCharacter != null) {
            actualCharacter.getAttributes().get(0).setValue(Dice.roll(3, 6) * 5);
            actualCharacter.getAttributes().get(1).setValue(Dice.roll(3, 6) * 5);
            actualCharacter.getAttributes().get(2).setValue((Dice.roll(2, 6) + 6) * 5);

            actualCharacter.getAttributes().get(3).setValue(Dice.roll(3, 6) * 5);
            actualCharacter.getAttributes().get(4).setValue(Dice.roll(3, 6) * 5);
            actualCharacter.getAttributes().get(5).setValue((Dice.roll(2, 6) + 6) * 5);

            actualCharacter.getAttributes().get(6).setValue(Dice.roll(3, 6) * 5);
            actualCharacter.getAttributes().get(7).setValue((Dice.roll(2, 6) + 6) * 5);
            refresh(ACHARACTER);
        }
    }

    /**
     * Adds the actual Character to the actual Scenarios npc list if it is a NPC
     */
    public void addNPCToScenario() {
        if (actualCharacter != null && actualCharacter instanceof NPC && actualScenario != null) {
            if (!actualScenario.getNpcs().contains(actualCharacter))
                actualScenario.getNpcs().add((NPC) actualCharacter);
            refresh(ASCENARIO);
        }
    }

    /**
     * Adds the actual Character to the actual Scenarios pc list if it is a PC
     */
    public void addPCToScenario() {
        if (actualCharacter != null && actualCharacter instanceof PC && actualScenario != null) {
            if (!actualScenario.getPcs().contains(actualCharacter)) actualScenario.getPcs().add((PC) actualCharacter);
            refresh(ASCENARIO);
        }
    }

    /**
     * Adds to the actual Character a Image with the given file
     *
     * @param file The File where the image is stored
     */
    public void addCharacterImage(final File file) {
        if (actualCharacter != null) {
            Image image = new Image();

            image.setName(file.getName());
            image.setPath(file.getPath());
            actualCharacter.setImage(image);
            refresh(ACHARACTER);
            refresh(CHARACTERIMAGE);
        }
    }

    /**
     * Saves the actual Character with the given filepath
     *
     * @param file the given Filepath
     */
    public void saveCharacter(File file) {
        if (file == null) return;
        actualCharacter.setPath(file);
        if (actualCharacter != null) {
            saveObject(file, actualCharacter);
        }
    }

    /**
     * Deletes the actual Character from the List an the Filesystem
     */
    public void deleteCharacter() {
        boolean deleted = false;
        if (actualCharacter instanceof PC) {
            pcs.remove(actualCharacter);
            File file = new File("pc/" + actualCharacter.getName() + ".pc");
            deleted = file.delete();
        } else if (actualCharacter instanceof NPC) {
            npcs.remove(actualCharacter);
            File file = new File("npc/" + actualCharacter.getName() + ".npc");
            deleted = file.delete();
        }
        if (deleted) {
            refresh(CHARACTERS);
            setActualCharacter( null);
            refresh(ACHARACTER);
        }

    }

    /**
     * Creates a new Scenario with the given name
     *
     * @param name the name of the scenario
     */
    public void newScenario(final String name) {
        Scenario newScenario = new Scenario();
        newScenario.setName(name);
        addScenario(newScenario);
        setActualScenario(newScenario);
    }

    public Music getActualMusic() {
        return actualMusic;
    }

    /**
     * Creates a new Location with the given name
     *
     * @param name the name of the Location
     */
    public void newLocation(final String name) {
        Location newLocation = new Location();

        newLocation.setName(name);
        addLocation(newLocation);
        setActualLocation(newLocation);
    }

    public void setActualMusic(final Music actualMusic) {
        this.actualMusic = actualMusic;
    }

    public Ability addWeaponAbility(final String name) {
        Ability a = new Ability(name,0);
        a.setDeletable(true);
        getActualCharacter().getFightAbilities().add(a);
        refresh(ACHARACTER);
        return a;
    }

    public void removeWeaponAbility(final Ability a) {
        if(a.isDeletable() && actualCharacter != null){
            getActualCharacter().getFightAbilities().remove(a);
            refresh(ACHARACTER);
        }
    }
    public Ability addAbility(final String name) {
        Ability a = new Ability(name,0);
        a.setDeletable(true);
        getActualCharacter().getAbilities().add(a);
        refresh(ACHARACTER);
        return a;
    }

    public void removeAbility(final Ability a) {
        if(a.isDeletable() && actualCharacter != null){
            getActualCharacter().getAbilities().remove(a);
            refresh(ACHARACTER);
        }
    }

    public void setDamageBonus(final String damageBonus) {
        if(actualCharacter != null){
            actualCharacter.getDamageBonus().setBonus(damageBonus);
            refresh(ACHARACTER);
        }
    }

    public void setPropertieValue(final AProperty property, final String value) throws NumberFormatException {
            int i = Integer.parseInt(value);
            property.setValue(i);
            refresh(ACHARACTER);

    }

    public void setCommonProperty(final CommonProperty property, final String value) {
        property.setValue(value);
        refresh(ACHARACTER);
    }

    public void setCharacterText(final String id, final String text) {
        if (getActualCharacter() == null) return;
        if (id.equals(TXT_CC_DESCRIPTION)) getActualCharacter().setDescription(text);
        if (getActualCharacter() instanceof PC) {
            PC c = (PC) getActualCharacter();
            switch (id) {
                case TXT_CC_IDEOLOGY:
                    c.setIdeology(text);
                    break;
                case TXT_CC_PERSONS:
                    c.setImportantPersons(text);
                    break;
                case TXT_CC_PLACES:
                    c.setImportantPlaces(text);
                    break;
                case TXT_CC_TREASURES:
                    c.setTreasuredPossessions(text);
                    break;
                case TXT_CC_TRAITS:
                    c.setTraits(text);
                    break;
                case TXT_CC_INJURIES:
                    c.setInjuries(text);
                    break;
                case TXT_CC_PHOBICS:
                    c.setPhobias(text);
                    break;
                case TXT_CC_TOMES:
                    c.setTomes(text);
                    break;
                case TXT_CC_ENCOUNTERS:
                    c.setEncounters(text);
                    break;
                case TXT_CC_POSSESSIONS:
                    c.setPossessions(text);
                    break;
                case TXT_CC_CASH:
                    c.setCash(text);
                    break;
            }
        }
        refresh(ACHARACTER);
    }

    public void setLocationText(final String htmlText) {
        if(actualLocation!=null){
            actualLocation.setDescription(htmlText);
            refresh(ALOCATION);
        }
    }
}

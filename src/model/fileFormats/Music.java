package model.fileFormats;

import java.io.File;

/**
 * This class represents a Music-File
 */
public class Music extends DataFile {

    private static final String DEFAULT_PATH = "";

    /**
     * Returns the specific Music as File or if the Music File is not found null
     * @return the path of the musicfile
     */
    public String getMusic() {
        if(getPath()==null)return  DEFAULT_PATH;
        if (new File(getPath()).exists()) return getPath();
        return DEFAULT_PATH;
    }

}
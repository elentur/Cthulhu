package model.fileFormats;

import java.io.File;

/**
 * This class represents a Sound-File
 */
public class Sound extends DataFile {

    private static final String DEFAULT_PATH = "";


    /**
     * Returns the specific Sound as File or if the Sound File is not found null
     *
     * @return the path of the soundfile
     */
    public String getSound() {
        if(getPath()==null)return  DEFAULT_PATH;
        if (new File(getPath()).exists()) return getPath();
        return DEFAULT_PATH;
    }

}
package model.fileFormats;

import java.io.File;

/**
 * This class represents an Image-File
 */
public class Image extends DataFile  {


    private static final String DEFAULT_PATH = "file:images/default.jpg";

    /**
     * Returns the specific Image as File or if the Image is not found an default empty Image
     * @return the path of the image file
     */
    public String getImage() {
        if(getPath()==null)return  DEFAULT_PATH;
        if (new File(getPath()).exists()) return "file:"+getPath();
        return DEFAULT_PATH;
    }

}
package test.testDataFormats

import model.fileFormats.Image

/**
 * Created by Marcus Baetz on 05.08.2016.
 * @author Marcus Bätz
 */
class ImageTest extends GroovyTestCase {
    void testGetImage() {
        Image file = new Image();
        assertEquals(file.DEFAULT_PATH,file.getImage());
        String path = "src/model/fileFormats/DataFile.java";
        file.setPath(path)
        assertEquals(path,file.getImage());
        path = "src/model/fileFormats/DataFile1.java";
        file.setPath(path)
        assertEquals(file.DEFAULT_PATH,file.getImage());
    }
}

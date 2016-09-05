package test.testDataFormats

import model.fileFormats.Sound

/**
 * Created by Marcus Baetz on 05.08.2016.
 * @author Marcus Bätz
 */
class SoundTest extends GroovyTestCase {
    void testGetSound() {
        Sound file = new Sound();
        assertEquals(file.DEFAULT_PATH,file.getSound());
        String path = "src/model/fileFormats/DataFile.java";
        file.setPath(path)
        assertEquals(path,file.getSound());
        path = "src/model/fileFormats/DataFile1.java";
        file.setPath(path)
        assertEquals(file.DEFAULT_PATH,file.getSound());

    }
}

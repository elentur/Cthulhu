package test.testDataFormats

import model.fileFormats.Music

/**
 * Created by Marcus Baetz on 05.08.2016.
 * @author Marcus Bätz
 */
class MusicTest extends GroovyTestCase {
    void testGetMusic() {
        Music file = new Music();
        assertEquals(file.DEFAULT_PATH,file.getMusic());
        String path = "src/model/fileFormats/DataFile.java";
        file.setPath(path)
        assertEquals(path,file.getMusic());
        path = "src/model/fileFormats/DataFile1.java";
        file.setPath(path)
        assertEquals(file.DEFAULT_PATH,file.getMusic());
    }
}

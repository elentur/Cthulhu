package test.testDataFormats
import model.fileFormats.DataFile
/**
 * Created by Marcus Baetz on 05.08.2016.
 * @author Marcus Bätz
 */
class DataFileTest extends GroovyTestCase {




    void testGetPath() {
        DataFile file = new DataFile();
        assertEquals(file.getPath(),null);
        file.setPath("");
        assertEquals(file.getPath(),null);
        String s = "test/test.test";
        file.setPath(s)
        assertEquals(file.getPath(),s);
    }

    void testSetPath() {
        DataFile file = new DataFile();
        file.setPath("");
        assertEquals(file.getPath(),null);
        String s = "test/test.test";
        file.setPath(s)
        assertEquals(file.getPath(),s);
        shouldFail {
            file.setPath(null);
        }
    }

    void testGetName() {
        DataFile file = new DataFile();
        assertEquals(file.getPath(),null);
        file.setPath("");
        assertEquals(file.getPath(),null);
        String s = "name";
        file.setPath(s)
        assertEquals(file.getPath(),s);
    }


    void testSetName() {
        DataFile file = new DataFile();
        file.setPath("");
        assertEquals(file.getPath(),null);
        String s = "name";
        file.setPath(s)
        assertEquals(file.getPath(),s);
        shouldFail {
            file.setPath(null);
        }
    }
}

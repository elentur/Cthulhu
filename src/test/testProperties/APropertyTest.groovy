package test.testProperties

import model.properties.AProperty
/**
 * Created by Marcus Baetz on 05.08.2016.
 * @author Marcus Bätz
 */
class APropertyTest extends GroovyTestCase {


    void testObserver(){
        AProperty property1 = new AProperty() { };
        AProperty property2 = new AProperty() {
            @Override
            protected void refresh(final int value) {
                this.value=value;
            }
        };
        property1.setValue(10);
        property2.setValue(20);
        assertEquals(10,property1.getValue());
        assertEquals(20,property2.getValue());

        property1.addObserver(property2);
        property1.setValue(40)
        assertEquals(40,property1.getValue());
        assertEquals(40,property2.getValue());
        property2.setValue(10)
        assertEquals(40,property1.getValue());
        assertEquals(10,property2.getValue());
    }
}

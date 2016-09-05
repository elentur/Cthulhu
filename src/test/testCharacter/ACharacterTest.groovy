package test.testCharacter

import model.character.ACharacter
import model.properties.Attribute
import model.properties.MoveRate

/**
 * Created by Marcus Baetz on 06.08.2016.
 * @author Marcus Bätz
 */
class ACharacterTest extends GroovyTestCase {

    void testCharacter(){
        ACharacter character = new ACharacter(){};
        MoveRate moveRate;
       for(Attribute a : character.getAttributes()){
          if(!(a instanceof MoveRate))
              a.setValue(60);
          else
              moveRate = (MoveRate)a;
       }

        assertEquals(8,moveRate.getValue())
        assertEquals(0,character.getBuild().getValue())
        assertEquals("0",character.getDamageBonus().getBonus())
        assertEquals(12, character.getHitPoints().getValue())
        assertEquals(12, character.getMagicPoints().getValue())

    }
}

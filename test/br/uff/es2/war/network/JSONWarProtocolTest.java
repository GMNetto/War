package br.uff.es2.war.network;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.uff.es2.war.model.Color;


public class JSONWarProtocolTest {
    
    private WarProtocol protocol;
    
    @Before
    public void setupProtocol(){
	protocol = new JSONWarProtocol();
    }
    
    @Test
    public void SERVER_CHOOSE_COLOR(){
	final String expectedMessage = "CHOOSE_COLOR[{\"name\":\"black\"},{\"name\":\"blue\"}]";
	final Color[] colors =  new Color[]{new Color("black"), new Color("blue")};
	String message = protocol.chooseColor(colors);
	assertEqualsIgnoringSpaces(expectedMessage, message);
    }
    
    @Test
    public void CLIENT_CHOOSE_COLOR(){
	Color color = protocol.chooseColor("CHOOSE_COLOR{\"name\":\"blue\"}");
	assertEquals(new Color("blue"), color);
    }
    
    private void assertEqualsIgnoringSpaces(String expected, String actual){
	expected = expected.replace(" ", "");
	actual = actual.replace(" ", "");
	assertEquals(expected, actual);
    }
}

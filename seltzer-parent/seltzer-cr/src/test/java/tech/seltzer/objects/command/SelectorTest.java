package tech.seltzer.objects.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.Selector;

public class SelectorTest {
	@Test
	public void testConstructors() {
		String selectorPath = "//body[1]";
		String message1 = "Selector string should have been initialized to \"\"!";
		String message2 = "Selector type should have been initialized to SelectorType.NONE!";
		String message3 = "Selector string should have been initialized to \"" + selectorPath + "\"!";
		String message4 = "Selector type should have been initialized to SelectorType.XPATH!";
		
		Selector selector = new Selector();
		assertEquals(message1, "", selector.getPath());
		assertEquals(message2, SelectorType.NONE, selector.getType());
		
		selector = new Selector(SelectorType.XPATH, selectorPath);
		assertEquals(message3, selectorPath, selector.getPath());
		assertEquals(message4, SelectorType.XPATH, selector.getType());
	}
	
	@Test
	public void testSetSelector() {
		String selectorPath = "path";
		String message = "Selector path should have been set to \"path\"!";
		
		Selector selector = new Selector();
		selector.setPath(selectorPath);
		assertEquals(message, selectorPath, selector.getPath());
	}

	@Test
	public void testGetSelectorType() {
		String selectorPath = "hidden";
		String message = "Selector type should have been initialized to SelectorType.CLASS_NAME!";
		
		Selector selector = new Selector(SelectorType.CLASS_NAME, selectorPath);
		assertEquals(message, SelectorType.CLASS_NAME, selector.getType());
	}

	@Test
	public void testSetSelectorType() {
		String message = "Selector type should have been set to SelectorType.ID!";
		
		Selector selector = new Selector();
		selector.setType(SelectorType.ID);
		assertEquals(message, SelectorType.ID, selector.getType());
	}

	@Test
	public void testGetSelector() {
		String selectorPath = "path";
		String message = "Selector path should have been initialized to \"path\"!";
		
		Selector selector = new Selector(SelectorType.LINK_TEXT, selectorPath);
		assertEquals(message, selectorPath, selector.getPath());
	}

	@Test
	public void testSetSelectorString() {
		String selectorPath = "body";
		String message1 = "Selector string should have been initialized to \"" + selectorPath + "\"!";
		String message2 = "Selector type should have been initialized to SelectorType.TAG_NAME!";
		
		Selector selector = new Selector();
		selector.setSelector(SelectorType.TAG_NAME, selectorPath);
		assertEquals(message1, selectorPath, selector.getPath());
		assertEquals(message2, SelectorType.TAG_NAME, selector.getType());
	}

}

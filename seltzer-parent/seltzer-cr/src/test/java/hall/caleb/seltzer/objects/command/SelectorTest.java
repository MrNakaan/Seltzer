package hall.caleb.seltzer.objects.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hall.caleb.seltzer.enums.SelectorType;

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
	public void testHashCode() {
		int prime = Selector.HASH_PRIME;
		int baseHash = prime * prime;
		int hash;
		String message = "Hash code didn't match!";
		
		Selector selector = new Selector(null, null);
		assertEquals("Hash code should have been " + prime + "^2!", baseHash, selector.hashCode());
		
		selector = new Selector();
		hash = prime * 1 + "".hashCode();
		hash = prime * hash + SelectorType.NONE.hashCode();
		assertEquals(message, hash, selector.hashCode());
		
		selector = new Selector(SelectorType.XPATH, "foobar");
		hash = prime * 1 + "foobar".hashCode();
		hash = prime * hash + SelectorType.XPATH.hashCode();
		assertEquals(message, hash, selector.hashCode());
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
	public void testToString() {
		String selectorPath = "Unsubscribe";
		String expectedString = "Selector [selectorType=PARTIAL_LINK_TEXT, selector=Unsubscribe]";
		String message = "Selector.toString() expected to return \"" + expectedString + "\"!";
		
		Selector selector = new Selector(SelectorType.PARTIAL_LINK_TEXT, selectorPath);
		String value = selector.toString();
		assertEquals(message, expectedString, value);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsObject() {
		Selector baseSelector = new Selector(null, null);
		Selector otherSelector = new Selector(null, null);
		
		assertTrue("Object doesn't equal itself!", baseSelector.equals(baseSelector));
		assertFalse("Object equals null!", baseSelector.equals(null));
		assertFalse("Object equals object of a different class!", baseSelector.equals(new String()));
		
		assertTrue("Object inequal to equal object", baseSelector.equals(otherSelector));
		
		otherSelector.setPath("");
		assertFalse("Object equals inequal object!", baseSelector.equals(otherSelector));
		
		baseSelector.setPath("");
		otherSelector.setPath(null);
		assertFalse("Object equals inequal object!", baseSelector.equals(otherSelector));
		
		otherSelector.setPath("");
		assertTrue("Object inequal to equal object", baseSelector.equals(otherSelector));
		
		baseSelector.setType(SelectorType.NONE);
		assertFalse("Object equals inequal object!", baseSelector.equals(otherSelector));
		
		otherSelector.setSelector(baseSelector.getType(), baseSelector.getPath());
		assertTrue("Object inequal to equal object!", baseSelector.equals(otherSelector));
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

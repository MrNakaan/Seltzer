package hall.caleb.seltzer.objects.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import hall.caleb.seltzer.enums.CommandType;

public class CommandListTest {
	@Test
	public void testConstructors() {
		CommandList<?> list = new CommandList<>();
		assertEquals("Commands should be empty on creation!", 0, list.getSize());
		assertEquals("Serialized Commands should be empty on creation!", 0, list.getSerializedSize());
	}
	
	@Test
	public void testSerialization() {
		CommandList<CommandData> list1 = new CommandList<>();
		CommandList<CommandData> list2 = new CommandList<>();
		list1.serialize();
		list1.deserialize();
		list2.serialize();
		list2.deserialize();
		
		list1.addCommand(new CommandData());
		list2.addCommand(new CommandData());
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);
		
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.fromString(id1.toString());
		list1.addCommand(new CommandData(CommandType.START, id1));
		list2.addCommand(new CommandData(CommandType.START, id2));
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);

		ChainCommandData<CommandData> cr = new ChainCommandData<CommandData>(id1);
		cr.serialize();
		cr.deserialize();
		cr = new ChainCommandData<CommandData>(id2);
		cr.serialize();
		cr.deserialize();
		list1.addCommand(cr);
		list2.addCommand(cr);
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);
	}

	@Test
	public void testAddCommand() {
		CommandList<CommandData> list = new CommandList<>();
		assertEquals("Commands should be empty on creation!", 0, list.getSize());
		
		list.addCommand(new CommandData());
		assertEquals("Commands should contain a single element!", 1, list.getSize());
		
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		assertEquals("Commands should contain 8 elements!", 8, list.getSize());	
	}

	@Test
	public void testGetSize() {
		CommandList<CommandData> list = new CommandList<>();
		assertEquals("Commands should be empty on creation!", 0, list.getSize());
		
		list.addCommand(new CommandData());
		assertEquals("Commands should contain a single element!", 1, list.getSize());
		
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		assertEquals("Commands should contain 8 elements!", 8, list.getSize());
	}

	@Test
	public void testGetSerializedSize() {
		CommandList<CommandData> list = new CommandList<>();
		list.serialize();
		assertEquals("Commands should be empty on creation!", 0, list.getSerializedSize());
		
		list.addCommand(new CommandData());
		list.serialize();
		assertEquals("Serialized Commands should contain a single element!", 1, list.getSerializedSize());
		
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.serialize();
		assertEquals("Serialized Commands should contain 8 elements!", 8, list.getSerializedSize());
	}

	@Test
	public void testGetCommands() {
		CommandList<CommandData> list = new CommandList<>();
		List<CommandData> Commands = list.getCommands();
		assertEquals("Commands should be empty on creation!", 0, Commands.size());
		
		list.addCommand(new CommandData());
		Commands = list.getCommands();
		assertEquals("Commands should contain a single element!", 1, Commands.size());
		
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		list.addCommand(new CommandData());
		Commands = list.getCommands();
		assertEquals("Commands should contain 8 elements!", 8, Commands.size());
	}

	@Test
	public void testSetCommands() {
		CommandList<CommandData> list = new CommandList<>();
		List<CommandData> Commands = new ArrayList<>();
		list.setCommands(Commands);
		assertEquals("Commands should be empty on creation!", 0, Commands.size());
		
		Commands.add(new CommandData());
		list.setCommands(Commands);
		assertEquals("Commands should contain a single element!", 1, Commands.size());
		
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		list.setCommands(Commands);
		assertEquals("Commands should contain 8 elements!", 8, Commands.size());
		
		list.setCommands(null);
		assertEquals("Commands should contain 0 elements!", 0, Commands.size());
	}
	
	@Test
	public void testToString() {
		String expectedString = "CommandList [commands=null, serializedCommands=null]";
		String message = "CommandList.toString() expected to return \"" + expectedString + "\"!";
		
		CommandList<CommandData> list = new CommandList<>();
		String value = list.toString();
		assertEquals(message, expectedString, value);
		
		expectedString = "CommandList [commands=[" + new CommandData().toString() + "], serializedCommands=null]";
		message = "CommandList.toString() expected to return \"" + expectedString + "\"!";
		list.addCommand(new CommandData());
		value = list.toString();
		assertEquals(message, expectedString, value);
	}
	
	@Test
	public void testHashCode() {
		int prime = CommandList.HASH_PRIME;
		int baseHash = prime * prime;
		int hash;
		String message = "Hash code didn't match!";
		
		CommandList<CommandData> list = new CommandList<>();
		assertEquals("Hash code should have been " + prime + "^2!", baseHash, list.hashCode());
		
		List<CommandData> singleCommandList = new ArrayList<>();
		singleCommandList.add(new CommandData());
		list.addCommand(new CommandData());
		list.serialize();
		list.deserialize();
		hash = prime * 1 + singleCommandList.hashCode();
		hash = prime * hash + (new ArrayList<CommandData>()).hashCode();
		assertEquals(message, hash, list.hashCode());
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		CommandList<CommandData> baseList = new CommandList<>();
		CommandList<CommandData> otherList = new CommandList<>();
		
		assertTrue("Object doesn't equal itself!", baseList.equals(baseList));
		assertFalse("Object equals null!", baseList.equals(null));
		assertFalse("Object equals object of a different class!", baseList.equals(new String()));
		
		assertTrue("Object inequal to equal object", baseList.equals(otherList));
		
		otherList.setCommands(new ArrayList<CommandData>());
		assertFalse("Object equals inequal object!", baseList.equals(otherList));
		
		baseList.setCommands(new ArrayList<CommandData>());
		otherList.addCommand(new CommandData());
		assertFalse("Object equals inequal object!", baseList.equals(otherList));
		
		otherList.serialize();
		assertFalse("Object equals inequal object!", baseList.equals(otherList));
		
		baseList.serialize();
		assertFalse("Object equals inequal object!", baseList.equals(otherList));
	}
}

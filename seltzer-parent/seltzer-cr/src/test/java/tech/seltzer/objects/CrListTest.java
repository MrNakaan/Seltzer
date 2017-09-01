package tech.seltzer.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.CrList;
import tech.seltzer.objects.command.ChainCommandData;
import tech.seltzer.objects.command.CommandData;

public class CrListTest {
	@Test
	public void testConstructors() {
		CrList<?> list = new CrList<>();
		assertEquals("Commands should be empty on creation!", 0, list.getSize());
		assertEquals("Serialized Commands should be empty on creation!", 0, list.getSerializedSize());
	}
	
	@Test
	public void testSerialization() {
		CrList<CommandData> list1 = new CrList<>();
		CrList<CommandData> list2 = new CrList<>();
		list1.serialize();
		list1.deserialize();
		list2.serialize();
		list2.deserialize();
		
		list1.addCr(new CommandData());
		list2.addCr(new CommandData());
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);
		
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.fromString(id1.toString());
		list1.addCr(new CommandData(CommandType.START, id1));
		list2.addCr(new CommandData(CommandType.START, id2));
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);

		ChainCommandData<CommandData> cr = new ChainCommandData<CommandData>(id1);
		cr.serialize();
		cr.deserialize();
		cr = new ChainCommandData<CommandData>(id2);
		cr.serialize();
		cr.deserialize();
		list1.addCr(cr);
		list2.addCr(cr);
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);
	}

	@Test
	public void testAddCommand() {
		CrList<CommandData> list = new CrList<>();
		assertEquals("Commands should be empty on creation!", 0, list.getSize());
		
		list.addCr(new CommandData());
		assertEquals("Commands should contain a single element!", 1, list.getSize());
		
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		assertEquals("Commands should contain 8 elements!", 8, list.getSize());	
	}

	@Test
	public void testGetSize() {
		CrList<CommandData> list = new CrList<>();
		assertEquals("Commands should be empty on creation!", 0, list.getSize());
		
		list.addCr(new CommandData());
		assertEquals("Commands should contain a single element!", 1, list.getSize());
		
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		assertEquals("Commands should contain 8 elements!", 8, list.getSize());
	}

	@Test
	public void testGetSerializedSize() {
		CrList<CommandData> list = new CrList<>();
		list.serialize();
		assertEquals("Commands should be empty on creation!", 0, list.getSerializedSize());
		
		list.addCr(new CommandData());
		list.serialize();
		assertEquals("Serialized Commands should contain a single element!", 1, list.getSerializedSize());
		
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.serialize();
		assertEquals("Serialized Commands should contain 8 elements!", 8, list.getSerializedSize());
	}

	@Test
	public void testGetCommands() {
		CrList<CommandData> list = new CrList<>();
		List<CommandData> Commands = list.getCrs();
		assertEquals("Commands should be empty on creation!", 0, Commands.size());
		
		list.addCr(new CommandData());
		Commands = list.getCrs();
		assertEquals("Commands should contain a single element!", 1, Commands.size());
		
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		list.addCr(new CommandData());
		Commands = list.getCrs();
		assertEquals("Commands should contain 8 elements!", 8, Commands.size());
	}

	@Test
	public void testSetCommands() {
		CrList<CommandData> list = new CrList<>();
		List<CommandData> Commands = new ArrayList<>();
		list.setCrs(Commands);
		assertEquals("Commands should be empty on creation!", 0, Commands.size());
		
		Commands.add(new CommandData());
		list.setCrs(Commands);
		assertEquals("Commands should contain a single element!", 1, Commands.size());
		
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		Commands.add(new CommandData());
		list.setCrs(Commands);
		assertEquals("Commands should contain 8 elements!", 8, Commands.size());
		
		list.setCrs(null);
		assertEquals("Commands should contain 0 elements!", 0, Commands.size());
	}
}

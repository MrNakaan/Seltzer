package hall.caleb.seltzer.objects.response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;


public class ResponseListTest {
	@Test
	public void testConstructors() {
		ResponseList<?> list = new ResponseList<>();
		assertEquals("Responses should be empty on creation!", 0, list.getSize());
		assertEquals("Serialized responses should be empty on creation!", 0, list.getSerializedSize());
	}
	
	@Test
	public void testSerialization() {
		ResponseList<Response> list1 = new ResponseList<>();
		ResponseList<Response> list2 = new ResponseList<>();
		list1.serialize();
		list1.deserialize();
		list2.serialize();
		list2.deserialize();
		
		list1.addResponse(new Response());
		list2.addResponse(new Response());
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);
		
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.fromString(id1.toString());
		list1.addResponse(new Response(id1));
		list2.addResponse(new Response(id2));
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);

		ChainResponse<Response> cr = new ChainResponse<Response>(id1);
		cr.serialize();
		cr.deserialize();
		cr = new ChainResponse<Response>(id2);
		cr.serialize();
		cr.deserialize();
		list1.addResponse(cr);
		list2.addResponse(cr);
		list1.serialize();
		list1.deserialize();
		assertEquals("Lists are not equal!", list2, list1);
	}

	@Test
	public void testAddResponse() {
		ResponseList<Response> list = new ResponseList<>();
		assertEquals("Responses should be empty on creation!", 0, list.getSize());
		
		list.addResponse(new Response());
		assertEquals("Responses should contain a single element!", 1, list.getSize());
		
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		assertEquals("Responses should contain 8 elements!", 8, list.getSize());	
	}

	@Test
	public void testGetSize() {
		ResponseList<Response> list = new ResponseList<>();
		assertEquals("Responses should be empty on creation!", 0, list.getSize());
		
		list.addResponse(new Response());
		assertEquals("Responses should contain a single element!", 1, list.getSize());
		
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		assertEquals("Responses should contain 8 elements!", 8, list.getSize());
	}

	@Test
	public void testGetSerializedSize() {
		ResponseList<Response> list = new ResponseList<>();
		list.serialize();
		assertEquals("Responses should be empty on creation!", 0, list.getSerializedSize());
		
		list.addResponse(new Response());
		list.serialize();
		assertEquals("Serialized responses should contain a single element!", 1, list.getSerializedSize());
		
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.serialize();
		assertEquals("Serialized responses should contain 8 elements!", 8, list.getSerializedSize());
	}

	@Test
	public void testGetResponses() {
		ResponseList<Response> list = new ResponseList<>();
		List<Response> responses = list.getResponses();
		assertEquals("Responses should be empty on creation!", 0, responses.size());
		
		list.addResponse(new Response());
		responses = list.getResponses();
		assertEquals("Responses should contain a single element!", 1, responses.size());
		
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		list.addResponse(new Response());
		responses = list.getResponses();
		assertEquals("Responses should contain 8 elements!", 8, responses.size());
	}

	@Test
	public void testSetResponses() {
		ResponseList<Response> list = new ResponseList<>();
		List<Response> responses = new ArrayList<>();
		list.setResponses(responses);
		assertEquals("Responses should be empty on creation!", 0, responses.size());
		
		responses.add(new Response());
		list.setResponses(responses);
		assertEquals("Responses should contain a single element!", 1, responses.size());
		
		responses.add(new Response());
		responses.add(new Response());
		responses.add(new Response());
		responses.add(new Response());
		responses.add(new Response());
		responses.add(new Response());
		responses.add(new Response());
		list.setResponses(responses);
		assertEquals("Responses should contain 8 elements!", 8, responses.size());
		
		list.setResponses(null);
		assertEquals("Responses should contain 0 elements!", 0, responses.size());
	}
	
	@Test
	public void testToString() {
		String expectedString = "ResponseList [responses=null, serializedResponses=null]";
		String message = "ResponseList.toString() expected to return \"" + expectedString + "\"!";
		
		ResponseList<Response> list = new ResponseList<>();
		String value = list.toString();
		assertEquals(message, expectedString, value);
		
		expectedString = "ResponseList [responses=[" + new Response().toString() + "], serializedResponses=null]";
		message = "ResponseList.toString() expected to return \"" + expectedString + "\"!";
		list.addResponse(new Response());
		value = list.toString();
		assertEquals(message, expectedString, value);
	}
	
	@Test
	public void testHashCode() {
		int prime = ResponseList.HASH_PRIME;
		int baseHash = prime * prime;
		int hash;
		String message = "Hash code didn't match!";
		
		ResponseList<Response> list = new ResponseList<>();
		assertEquals("Hash code should have been " + prime + "^2!", baseHash, list.hashCode());
		
		List<Response> singleResponseList = new ArrayList<>();
		singleResponseList.add(new Response());
		list.addResponse(new Response());
		list.serialize();
		list.deserialize();
		hash = prime * 1 + singleResponseList.hashCode();
		hash = prime * hash + (new ArrayList<Response>()).hashCode();
		assertEquals(message, hash, list.hashCode());
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		ResponseList<Response> baseList = new ResponseList<>();
		ResponseList<Response> otherList = new ResponseList<>();
		
		assertTrue("Object doesn't equal itself!", baseList.equals(baseList));
		assertFalse("Object equals null!", baseList.equals(null));
		assertFalse("Object equals object of a different class!", baseList.equals(new String()));
		
		assertTrue("Object inequal to equal object", baseList.equals(otherList));
		
		otherList.setResponses(new ArrayList<Response>());
		assertFalse("Object equals inequal object!", baseList.equals(otherList));
		
		baseList.setResponses(new ArrayList<Response>());
		otherList.addResponse(new Response());
		assertFalse("Object equals inequal object!", baseList.equals(otherList));
		
		otherList.serialize();
		assertFalse("Object equals inequal object!", baseList.equals(otherList));
		
		baseList.serialize();
		assertFalse("Object equals inequal object!", baseList.equals(otherList));
	}
}

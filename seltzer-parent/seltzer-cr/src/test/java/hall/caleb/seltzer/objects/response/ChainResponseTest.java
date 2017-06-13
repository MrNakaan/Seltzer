package hall.caleb.seltzer.objects.response;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.annotation.Generated;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

@Generated(value = "org.junit-tools-1.0.5")
public class ChainResponseTest {
	private ChainResponse original;
	private ChainResponse clone;
	
	@Before
	public void createResponses() {
		UUID id = UUID.randomUUID();

		original = new ChainResponse(id, true);
		clone = new ChainResponse(id, true);
		
		Response response;
		SingleResultResponse srResponse;
		MultiResultResponse mrResponse;
		ChainResponse cResponse;
		
		response = new Response(id, false);
		original.addResponse(response);
		clone.addResponse(response);
		
		response = new Response(id, true);
		original.addResponse(response);
		clone.addResponse(response);
		
		response = new Response(id, false);
		original.addResponse(response);
		clone.addResponse(response);
		
		srResponse = new SingleResultResponse(id, true);
		srResponse.setResult("R 1");
		original.addResponse(srResponse);
		clone.addResponse(srResponse);
		
		mrResponse = new MultiResultResponse(id, false);
		mrResponse.getResults().add("MR 1");
		mrResponse.getResults().add("MR 2");
		mrResponse.getResults().add("MR 3");
		mrResponse.getResults().add("MR 4");
		mrResponse.getResults().add("MR 5");
		original.addResponse(mrResponse);
		clone.addResponse(mrResponse);
		
		cResponse = new ChainResponse(id, true);
		cResponse.addResponse(response);
		cResponse.addResponse(mrResponse);
		original.addResponse(cResponse);
		
		cResponse = new ChainResponse(id, true);
		cResponse.addResponse(response);
		cResponse.addResponse(mrResponse);
		clone.addResponse(cResponse);
	}
	
	@Test
	public void testSerialize() throws Exception {
		Gson gson = new Gson();
		
		original.serialize();
		String json = gson.toJson(original, ChainResponse.class);
		original = gson.fromJson(json, ChainResponse.class);
		original.deserialize();
		
		assertEquals("Verify that original matches clone", original, clone);
	}
}
package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public enum ResponseType {
	Basic(Response.class),
	SingleResult(SingleResultResponse.class),
	MultiResult(MultiResultResponse.class),
	Chain(ChainResponse.class);

	private Class<? extends Response> responseClass;
	
	private ResponseType(Class<? extends Response> responseClass) {
		this.responseClass = responseClass;
	}

	public Class<? extends Response> getResponseClass() {
		return responseClass;
	}
}

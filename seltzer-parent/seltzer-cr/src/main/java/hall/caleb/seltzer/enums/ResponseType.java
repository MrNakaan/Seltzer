package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.ExceptionResponse;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

public enum ResponseType implements CrType {
	BASIC(Response.class),
	SINGLE_RESULT(SingleResultResponse.class),
	MULTI_RESULT(MultiResultResponse.class),
	CHAIN(ChainResponse.class),
	EXCEPTION(ExceptionResponse.class);

	private Class<? extends Response> responseClass;
	
	private ResponseType(Class<? extends Response> responseClass) {
		this.responseClass = responseClass;
	}

	@Override
	public Class<? extends Response> getCrClass() {
		return responseClass;
	}
}

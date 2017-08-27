package tech.seltzer.enums;

import tech.seltzer.objects.response.ChainResponse;
import tech.seltzer.objects.response.ExceptionResponse;
import tech.seltzer.objects.response.MultiResultResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

/**
 * A list of response types that Seltzer can send back.
 */
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

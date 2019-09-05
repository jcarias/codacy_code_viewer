package com.jcarias.api.helpers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MalformedRequestSyntaxException extends Exception implements
		ExceptionMapper<MalformedRequestSyntaxException> {
	private static final long serialVersionUID = 1L;

	public MalformedRequestSyntaxException() {
		super("Malformed request syntax, invalid request message framing, or deceptive request routing.");
	}

	public MalformedRequestSyntaxException(String string) {
		super(string);
	}


	@Override
	public Response toResponse(MalformedRequestSyntaxException exception) {
		return Response.status(500).entity(exception.getMessage())
				.build();
	}
}
package com.jcarias.api.helpers;

import com.jcarias.api.entities.ErrorEntity;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

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
		ErrorEntity errorEntity = new ErrorEntity(exception.getMessage(), INTERNAL_SERVER_ERROR.getStatusCode());
		return Response.serverError().entity(errorEntity)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
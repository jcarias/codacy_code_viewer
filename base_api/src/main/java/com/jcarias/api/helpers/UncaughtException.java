package com.jcarias.api.helpers;


import com.jcarias.api.entities.ErrorEntity;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Provider
public class UncaughtException extends Throwable implements ExceptionMapper<Throwable> {
	private static final long serialVersionUID = 1L;

	@Override
	public Response toResponse(Throwable exception) {
		ErrorEntity errorEntity = new ErrorEntity(exception.getMessage(), INTERNAL_SERVER_ERROR.getStatusCode());
		return Response.serverError().entity(errorEntity)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
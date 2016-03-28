package com.guidentifier.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import javax.inject.Named;
import com.guidentifier.model.Type;

/** An endpoint class we are exposing */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "guidentifier.com", ownerName = "guidentifier.com", packagePath = ""))
public class GuidentifierApi {

	/** A simple endpoint method that takes a name and says Hi back */
	@ApiMethod(name = "sayHi")
	public Type sayHi(@Named("name") String name) {
		Type response = new Type("test-type");
		response.setId(1L);
		return response;
	}

}

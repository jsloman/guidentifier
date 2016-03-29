package com.guidentifier.api;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.guidentifier.dao.DAO;
import com.guidentifier.model.Type;

/** An endpoint class we are exposing */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "guidentifier.com",
		ownerName = "guidentifier.com", packagePath = ""))
public class GuidentifierApi {
	DAO dao;
	
	public GuidentifierApi()
	{
		dao = new DAO();
	}

	/** A simple endpoint method that takes a name and says Hi back */
	@ApiMethod(name = "sayHi")
	public Type sayHi(@Named("name") String name) {
		Type response = new Type("test-type" + name);
		response.setId(1L);
		return response;
	}
	
	public Type addType(Type type) {
		dao.add(type);
		return type;
	}
	
	public List<Type> getTypes() {
		return dao.getTypes();
	}

}

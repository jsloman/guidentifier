package com.guidentifier.api;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.guidentifier.dao.DAO;
import com.guidentifier.model.Type;

/** An endpoint class we are exposing */
@Api(name = "guidentifierApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "guidentifier.com",
		ownerName = "guidentifier.com", packagePath = ""))
public class GuidentifierApi {
	DAO dao;
	
	public GuidentifierApi()
	{
		dao = new DAO();
	}
	
	public Type addType(Type type) {
		dao.add(type);
		return type;
	}
	
	public List<Type> getTypes() {
		return dao.getTypes();
	}

}

package com.guidentifier.api;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.guidentifier.dao.DAO;
import com.guidentifier.model.Family;
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
	
	public List<Family> getFamilies(@Named("typeId") String typeId) {
		Type type = dao.getType(typeId);
		return dao.getFamiliesList(type);
	}
	
	public Family addFamily(@Named("typeId") String typeId, @Named("familyName") String familyName) {
		Type type = dao.getType(typeId);
		Family family = new Family(type, familyName);
		dao.add(family);
		return family;
	}

}

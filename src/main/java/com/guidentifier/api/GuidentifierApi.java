package com.guidentifier.api;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;
import com.guidentifier.dao.DAO;
import com.guidentifier.model.Group;
import com.guidentifier.model.Category;

/** An endpoint class we are exposing */
@Api(name = "guidentifierApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "guidentifier.com",
		ownerName = "guidentifier.com", packagePath = ""))
public class GuidentifierApi {
	DAO dao;
	
	public GuidentifierApi()
	{
		dao = new DAO();
	}
	
	public Category addType(Category type) {
		dao.add(type);
		return type;
	}
	
	public List<Category> getTypes() {
		return dao.getTypes();
	}
	
	public List<Group> getFamilies(@Named("typeId") String typeId) {
		Category type = dao.getType(typeId);
		return dao.getFamiliesList(type);
	}
	
	public Group addFamily(@Named("typeId") String typeId, @Named("familyName") String familyName) {
		Category type = dao.getType(typeId);
		Group family = new Group(type, familyName);
		dao.add(family);
		return family;
	}

}

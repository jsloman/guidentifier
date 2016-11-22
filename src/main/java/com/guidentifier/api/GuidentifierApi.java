package com.guidentifier.api;

import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
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
	
	@ApiMethod(name="addCategory")
	public Category addCategory(Category type) {
		dao.addCategory(type);
		return type;
	}
	
	@ApiMethod(name="getCategories")
	public List<Category> getCategories() {
		return dao.getCategories();
	}
	
	@ApiMethod(name="getGroups")
	public List<Group> getGroups(@Named("categoryName") String name) {
		Category category = dao.getCategory(name);
		return dao.getGroupList(category);
	}
	
	@ApiMethod(name="addGroup")
	public Group addGroup(@Named("categoryName") String categoryName, @Named("groupName") String groupName) {
		Category category = dao.getCategory(categoryName);
		Group group = new Group(category, groupName);
		dao.addGroup(group);
		return group;
	}

}

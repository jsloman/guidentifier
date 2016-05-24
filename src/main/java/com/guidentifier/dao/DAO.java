package com.guidentifier.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.guidentifier.model.Category;
import com.guidentifier.model.Group;
import com.guidentifier.model.Identifier;
import com.guidentifier.model.IdentifierOption;
import com.guidentifier.model.OptionScore;
import com.guidentifier.model.Region;
import com.guidentifier.model.Thing;

public class DAO {
	static {
		ObjectifyService.register(Category.class);
		ObjectifyService.register(Group.class);
		ObjectifyService.register(Thing.class);
		ObjectifyService.register(Region.class);
		ObjectifyService.register(Identifier.class);
		ObjectifyService.register(IdentifierOption.class);
		ObjectifyService.register(OptionScore.class);

	}
	
	public List<Category> getCategories() {
		return ofy().load().type(Category.class).list();
	}
	
	public void addCategory(Category category) {
		ofy().save().entity(category).now();
	}
	
	public Category getCategory(String name) {
		return ofy().load().type(Category.class).id(name).now();
	}
	
	public Category getCategory(Key<Category> key) {
		return ofy().load().key(key).now();
	}
	
	public Group getGroup(String name) {
		return ofy().load().type(Group.class).id(name).now();
	}
	
	public Iterable<Group> getGroups(Category category) {
		return ofy().load().type(Group.class).filter("category", category);
	}
	
	public List<Group> getGroupList(Category category) {
		return ofy().load().type(Group.class).filter("category", category).list();
	}
	
	public Iterable<Group> getChildGroups(Group group) {
		return ofy().load().type(Group.class).filter("parent =", group);
	}
	
	public Group getGroup(Key<Group> key) {
		return ofy().load().key(key).now();
	}
	
	public void addGroup(Group group) {
		ofy().save().entity(group).now();
	}
	
	public Iterable<Thing> getThing(Group group) {
		return ofy().load().type(Thing.class).filter("group", group);
	}
	
	public Thing getThing(String name) {
		return ofy().load().type(Thing.class).id(name).now();	
	}
	
	public void addThing(Thing thing) {
		ofy().save().entity(thing).now();
	}
	
	public List<Group> getParents(Group group) {
		List<Group> ret = new ArrayList<Group>();
		Group g = group;
		while (g.getParent() != null) {
			g = g.getParent();
			ret.add(0, g);
		}
		return ret;
	}
	
	public Region getRegion(String name) {
		return ofy().load().type(Region.class).id(name).now();
	}
	
	public void addRegion(Region region) {
		ofy().save().entity(region).now();
	}
	
	public List<Region> getRegionsList() {
		return ofy().load().type(Region.class).list();
	}
}

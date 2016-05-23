package com.guidentifier.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.guidentifier.model.FamilyInfo;
import com.guidentifier.model.Form;
import com.guidentifier.model.Region;
import com.guidentifier.model.Thing;
import com.guidentifier.model.SpeciesInfo;
import com.guidentifier.model.Category;
import com.guidentifier.model.Group;
import java.util.List;
import java.util.ArrayList;

public class DAO {
	static {
		ObjectifyService.register(Category.class);
		ObjectifyService.register(Group.class);
		ObjectifyService.register(Form.class);
		ObjectifyService.register(Thing.class);
		ObjectifyService.register(FamilyInfo.class);
		ObjectifyService.register(SpeciesInfo.class);
		ObjectifyService.register(Region.class);
	}
	
	public List<Category> getTypes() {
		return ofy().load().type(Category.class).list();
	}
	
	public void add(Category t) {
		ofy().save().entity(t).now();
	}
	
	public Category getType(String idStr) {
		try {
			Long id = Long.parseLong(idStr);
			return ofy().load().type(Category.class).id(id).now();
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public Category getType(Key<Category> key) {
		return ofy().load().key(key).now();
	}
	
	public Group getFamily(String idStr) {
		try {
			Long id = Long.parseLong(idStr);
			return ofy().load().type(Group.class).id(id).now();
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public Iterable<Group> getFamilies(Category t) {
		return ofy().load().type(Group.class).filter("type", t);
	}
	
	public List<Group> getFamiliesList(Category t) {
		return ofy().load().type(Group.class).filter("type", t).list();
	}
	
	public Iterable<Group> getFamilies(Group f) {
		return ofy().load().type(Group.class).filter("parent =", f);
	}
	
	public Group getFamily(Key<Group> key) {
		return ofy().load().key(key).now();
	}
	
	public void add(Group f) {
		ofy().save().entity(f).now();
	}
	
	public Iterable<Form> getForms(Category t) {
		return ofy().load().type(Form.class).filter("type", t);
	}
	
	public void add(Form f) {
		ofy().save().entity(f).now();
	}
	
	public Iterable<Thing> getSpecies(Group f) {
		return ofy().load().type(Thing.class).filter("family", f);
	}
	
	public Thing getSpecies(String idStr) {
		try {
			Long id = Long.parseLong(idStr);
			return ofy().load().type(Thing.class).id(id).now();
		} catch (NumberFormatException e) {
			return null;
		}	
	}
	
	public FamilyInfo getFamilyInfo(Group f) {
		return ofy().load().type(FamilyInfo.class).filter("family", f).first().now();
	}
	
	public SpeciesInfo getSpeciesInfo(Thing s) {
		return ofy().load().type(SpeciesInfo.class).filter("species", s).first().now();
	}
	
	public void add(Thing s) {
		ofy().save().entity(s).now();
	}
	
	public void add(FamilyInfo fi) {
		ofy().save().entity(fi).now();
	}
	
	public void add(SpeciesInfo si) {
		ofy().save().entity(si).now();
	}
	
	public List<Group> getParents(Group f) {
		List<Group> ret = new ArrayList<Group>();
		Group fam = f;
		while (fam.getParent() != null) {
			fam = fam.getParent();
			ret.add(0, fam);
		}
		return ret;
	}
	
	public Region getRegion(String idStr) {
		try {
			Long id = Long.parseLong(idStr);
			return ofy().load().type(Region.class).id(id).now();
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public void add(Region r) {
		ofy().save().entity(r).now();
	}
	
	public List<Region> getRegionsList() {
		return ofy().load().type(Region.class).list();
	}
}

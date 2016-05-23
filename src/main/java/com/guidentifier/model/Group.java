package com.guidentifier.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.Ref;

@Entity
public class Group {
	@Id String name;
	// TODO do we always want to load the category?
	@Index @Load Ref<Category> category;
	// TODO do we always want to load the parent?
	@Index @Load Ref<Group> parent;

	String description;
	String image;
	String wikipediaURL;
	
	@SuppressWarnings("unused")
	private Group() {
	}
	
	public Group(Category category, String name) {
		this.category = Ref.create(category);
		this.name = name;
	}
	
	public Group(Group parent, String name) {
		this.parent = Ref.create(parent);
		category = Ref.create(parent.getCategory());
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Category getCategory() {
		return category.get();
	}

	public void setCategory(Category category) {
		this.category = Ref.create(category);
	}

	public Group getParent() {
		return parent == null ? null : parent.get();
	}

	public void setParent(Group parent) {
		this.parent = Ref.create(parent);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getWikipediaURL() {
		return wikipediaURL;
	}

	public void setWikipediaURL(String wikipediaURL) {
		this.wikipediaURL = wikipediaURL;
	}
}

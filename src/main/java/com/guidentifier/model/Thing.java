package com.guidentifier.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.Ref;

@Entity
public class Thing {
	@Id String name;
	@Index @Load Ref<Category> category;
	@Index @Load Ref<Group> group;
	
	String description;
	String image;
	String wikipediaURL;
	
	@SuppressWarnings("unused")
	private Thing() {
	}
	
	public Thing(Group group, String name) {
		this.group = Ref.create(group);;
		category = Ref.create(group.getCategory());
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

	public Group getGroup() {
		return group.get();
	}

	public void setGroup(Group group) {
		this.group = Ref.create(group);
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

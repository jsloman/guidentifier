package com.guidentifier.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.Ref;

@Entity
public class Identifier {
	@Id String name;

	@Index @Load Ref<Category> category;
	@Index @Load Ref<Identifier> parent;

	String description;
	
	@SuppressWarnings("unused")
	private Identifier() {
	}
	
	public Identifier(Category category, String name) {
		this.category = Ref.create(category);
		this.name = name;
	}
	
	public Identifier(Identifier parent, String name) {
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

	public Identifier getParent() {
		return parent == null ? null : parent.get();
	}

	public void setParent(Identifier parent) {
		this.parent = Ref.create(parent);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

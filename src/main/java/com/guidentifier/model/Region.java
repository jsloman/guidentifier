package com.guidentifier.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.Ref;

@Entity
public class Region {
	@Id String name;
	@Index @Load Ref<Region> parent;
	
	/* TODO should a region possibly be relevant for a specific category only? */
	
	@SuppressWarnings("unused")
	private Region() {
	}
	
	public Region(String name) {
		this.name = name;
		parent = null;
	}
	
	public Region(Region region, String name) {
		parent = Ref.create(region);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Region getParent() {
		return parent == null ? null : parent.get();
	}

	public void setParent(Region parent) {
		this.parent = Ref.create(parent);
	}
}

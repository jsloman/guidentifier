package com.guidentifier.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.Ref;

@Entity
public class IdentifierOption {
	@Id String name;
	@Index @Load Ref<Identifier> identifier;
	
	@SuppressWarnings("unused")
	private IdentifierOption() {
	}
	
	public IdentifierOption(Identifier identifier, String name) {
		this.identifier = Ref.create(identifier);;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Identifier getIdentifier() {
		return identifier.get();
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = Ref.create(identifier);
	}
}

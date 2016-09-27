package com.guidentifier.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.Ref;

@Entity
public class OptionScore {
	@Id Long id;
	@Index @Load Ref<IdentifierOption> identifierOption;
	@Index @Load Ref<Thing> thing;
	@Index @Load Ref<Group> group;
	
	// TOOD define range?
	int value;
	
	@SuppressWarnings("unused")
	private OptionScore() {
	}
	
	public OptionScore(IdentifierOption identifierOption, Thing thing) {
		this.identifierOption = Ref.create(identifierOption);
		this.thing = Ref.create(thing);
		id = null;
	}
	
	public OptionScore(IdentifierOption identifierOption, Group group) {
		this.identifierOption = Ref.create(identifierOption);
		this.group = Ref.create(group);
		id = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public IdentifierOption getIdentifierOption() {
		return identifierOption.get();
	}

	public void setIdentifierOption(IdentifierOption identifierOption) {
		this.identifierOption = Ref.create(identifierOption);
	}

	public Thing getThing() {
		return thing.get();
	}

	public void setThing(Thing thing) {
		this.thing = Ref.create(thing);
	}
	
	public Group getGroup() {
		return group.get();
	}

	public void setGroup(Group group) {
		this.group = Ref.create(group);
	}
}

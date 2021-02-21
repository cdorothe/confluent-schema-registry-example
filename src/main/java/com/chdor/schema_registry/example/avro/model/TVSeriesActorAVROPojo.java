package com.chdor.schema_registry.example.avro.model;

import org.apache.avro.reflect.AvroDefault;

//@AvroAlias(space = "com.chdor.schema_registry.test.avro", alias = "People")

public class TVSeriesActorAVROPojo {
	private String lastName = null;
	private String firstName = null;
	@AvroDefault("\"Unknown\"")
	private String tvShow = null;
	private String actor = null;
	
	public String getLastName() {
		return lastName;
	}
	public TVSeriesActorAVROPojo setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public String getFirstName() {
		return firstName;
	}
	public TVSeriesActorAVROPojo setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public String getTvShow() {
		return tvShow;
	}
	public TVSeriesActorAVROPojo setTvShow(String tvShow) {
		this.tvShow = tvShow;
		return this;
	}

	@Override
	public String toString() {
		String json = "{".concat("\"firstName\":\"").concat(firstName).concat("\" , \"lastName\":\"").concat(lastName)
				.concat("\", ").concat("\"tvShow\":\"").concat(tvShow).concat("\", \"actor\":\"").concat(actor)
				.concat("\"}");
		return json;
	}
	
	public String getActor() {
		return actor;
	}
	
	public TVSeriesActorAVROPojo setActor(String actor) {
		this.actor = actor;
		return this;
	}

	
}

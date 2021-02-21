
package com.chdor.schema_registry.example.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;

/**
 * TVSeriesActorID
 * <p>
 * TV Show Actor ID
 * 
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id" })
@JsonSchemaTitle("TVSeriesActorID")
public class TVSeriesActorID {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	public TVSeriesActorID withId(Integer id) {
		this.id = id;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append(" \"TVSeriesActorID\" : { \"id\":");
		sb.append(id).append("}").append("\n");
		sb.append("}");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof TVSeriesActorID) == false) {
			return false;
		}
		TVSeriesActorID rhs = ((TVSeriesActorID) other);
		return ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id)));
	}

}

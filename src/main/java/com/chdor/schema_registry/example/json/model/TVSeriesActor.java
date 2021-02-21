
package com.chdor.schema_registry.example.json.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaTitle;

/**
 * TVSeriesActor
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "lastName", "firstName", "tvShow" })

@JsonSchemaTitle("TVSeriesActor")
public class TVSeriesActor {

	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("tvShow")
	private String tvShow;

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public TVSeriesActor withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public TVSeriesActor withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	@JsonProperty("tvShow")
	public String getTvShow() {
		return tvShow;
	}

	@JsonProperty("tvShow")
	public void setTvShow(String tvShow) {
		this.tvShow = tvShow;
	}

	public TVSeriesActor withTvShow(String tvShow) {
		this.tvShow = tvShow;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(TVSeriesActor.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
				.append('[');
		sb.append("lastName");
		sb.append('=');
		sb.append(((this.lastName == null) ? "<null>" : this.lastName));
		sb.append(',');
		sb.append("firstName");
		sb.append('=');
		sb.append(((this.firstName == null) ? "<null>" : this.firstName));
		sb.append(',');
		sb.append("tvShow");
		sb.append('=');
		sb.append(((this.tvShow == null) ? "<null>" : this.tvShow));
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',') {
			sb.setCharAt((sb.length() - 1), ']');
		} else {
			sb.append(']');
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = ((result * 31) + ((this.lastName == null) ? 0 : this.lastName.hashCode()));
		result = ((result * 31) + ((this.firstName == null) ? 0 : this.firstName.hashCode()));
		result = ((result * 31) + ((this.tvShow == null) ? 0 : this.tvShow.hashCode()));
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof TVSeriesActor) == false) {
			return false;
		}
		TVSeriesActor rhs = ((TVSeriesActor) other);
		return ((((this.lastName == rhs.lastName) || ((this.lastName != null) && this.lastName.equals(rhs.lastName)))
				&& ((this.firstName == rhs.firstName)
						|| ((this.firstName != null) && this.firstName.equals(rhs.firstName))))
				&& ((this.tvShow == rhs.tvShow) || ((this.tvShow != null) && this.tvShow.equals(rhs.tvShow))));
	}

}


package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity implements Comparable<Event> {

	private String	title;
	private String	description;
	private String	picture;
	private int		seatsOffered;
	private Date	moment;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@Min(1)
	public int getSeatsOffered() {
		return this.seatsOffered;
	}
	public void setSeatsOffered(final int seatsOffered) {
		this.seatsOffered = seatsOffered;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Override
	public int compareTo(final Event o) {
		if (this.seatsOffered < o.seatsOffered)
			return -1;
		if (this.seatsOffered > o.seatsOffered)
			return 1;
		return 0;

	}


	//Relationships

	private Manager				manager;
	private Collection<Chorbi>	chorbies;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}
	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@NotNull
	@ManyToMany()
	public Collection<Chorbi> getChorbies() {
		return this.chorbies;
	}
	public void setChorbies(final Collection<Chorbi> chorbies) {
		this.chorbies = chorbies;
	}

}

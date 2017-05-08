
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "birthDate")
})
public class Chorbi extends Actor {

	//Atributes----------------------------------------------------------------------

	private String		picture;
	private String		description;
	private String		kindRelationship;
	private String		genre;
	private Date		birthDate;
	private Coordinates	location;
	private CreditCard	creditCard;
	private Double		amountDue;


	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@Pattern(regexp = "^ACTIVITIES$|^FRIENDSHIP$|^LOVE$")
	public String getKindRelationship() {
		return this.kindRelationship;
	}
	public void setKindRelationship(final String kindRelationship) {
		this.kindRelationship = kindRelationship;
	}

	@Pattern(regexp = "^MAN$|^WOMAN$")
	public String getGenre() {
		return this.genre;
	}
	public void setGenre(final String genre) {
		this.genre = genre;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	public Coordinates getLocation() {
		return this.location;
	}
	public void setLocation(final Coordinates location) {
		this.location = location;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@NotNull
	public Double getAmountDue() {
		return this.amountDue;
	}
	public void setAmountDue(final Double amountDue) {
		this.amountDue = amountDue;
	}


	//Relationships----------------------------------------------------------------
	private Template			template;
	private Collection<Chirp>	sended;
	private Collection<Chirp>	received;
	private Collection<Love>	love;
	private Collection<Love>	lovedBy;
	private Collection<Event>	events;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "senderC")
	public Collection<Chirp> getSended() {
		return this.sended;
	}
	public void setSended(final Collection<Chirp> sended) {
		this.sended = sended;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "receiver")
	public Collection<Chirp> getReceived() {
		return this.received;
	}
	public void setReceived(final Collection<Chirp> received) {
		this.received = received;
	}

	@Valid
	@OneToOne(optional = true, mappedBy = "searcher")
	public Template getTemplate() {
		return this.template;
	}
	public void setTemplate(final Template template) {
		this.template = template;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "lover")
	public Collection<Love> getLove() {
		return this.love;
	}
	public void setLove(final Collection<Love> love) {
		this.love = love;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "loved")
	public Collection<Love> getLovedBy() {
		return this.lovedBy;
	}
	public void setLovedBy(final Collection<Love> lovedBy) {
		this.lovedBy = lovedBy;
	}

	@NotNull
	@ManyToMany(mappedBy = "chorbies")
	public Collection<Event> getEvents() {
		return this.events;
	}
	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

}

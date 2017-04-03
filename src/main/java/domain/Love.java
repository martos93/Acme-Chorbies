
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Love extends DomainEntity {

	private Date	moment;
	private String	comment;


	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	//Relationships--------------------------------------------------------


	private Chorbi	lover;
	private Chorbi	loved;


	@Valid
	@ManyToOne(optional = true)
	public Chorbi getLover() {
		return this.lover;
	}

	public void setLover(final Chorbi lover) {
		this.lover = lover;
	}

	@Valid
	@ManyToOne(optional = true)
	public Chorbi getLoved() {
		return this.loved;
	}

	public void setLoved(final Chorbi loved) {
		this.loved = loved;
	}
}

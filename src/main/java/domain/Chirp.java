
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chirp extends DomainEntity {

	private Date				moment;
	private String				text;
	private String				subject;
	private Collection<String>	attachments;

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
	
	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	//Relationships--------------------------------------------------------


	private Chorbi	sender;
	private Chorbi	receiver;


	@Valid
	@ManyToOne(optional = true)
	public Chorbi getSender() {
		return this.sender;
	}

	public void setSender(final Chorbi sender) {
		this.sender = sender;
	}

	@Valid
	@ManyToOne(optional = true)
	public Chorbi getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Chorbi receiver) {
		this.receiver = receiver;
	}

}

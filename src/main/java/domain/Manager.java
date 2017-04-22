
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	private String		company;
	private CreditCard	creditCard;
	private String		VAT;
	private Money		amountDue;


	@NotBlank
	public String getCompany() {
		return this.company;
	}
	public void setCompany(final String company) {
		this.company = company;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotBlank
	public String getVAT() {
		return this.VAT;
	}
	public void setVAT(final String vAT) {
		this.VAT = vAT;
	}

	@Valid
	@NotNull
	public Money getAmountDue() {
		return this.amountDue;
	}
	public void setAmountDue(final Money amountDue) {
		this.amountDue = amountDue;
	}


	//Relationships

	private Collection<Event>	events;
	private Collection<Chirp>	sended;


	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<Event> getEvents() {
		return this.events;
	}
	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

	@NotNull
	@OneToMany(mappedBy = "senderM")
	public Collection<Chirp> getSended() {
		return this.sended;
	}
	public void setSended(final Collection<Chirp> sended) {
		this.sended = sended;
	}

}

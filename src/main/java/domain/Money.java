
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Money {

	private double	amount;
	private String	currency;


	@Digits(integer = 6, fraction = 2)
	public double getAmount() {
		return this.amount;
	}
	public void setAmount(final double amount) {
		this.amount = amount;
	}

	@NotBlank
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(final String currency) {
		this.currency = currency;
	}

}

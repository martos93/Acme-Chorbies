
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Cache extends DomainEntity {

	private String	hours;
	private String	minutes;
	private String	seconds;


	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public String getHours() {
		return this.hours;
	}

	public void setHours(final String hours) {
		this.hours = hours;
	}

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public String getMinutes() {
		return this.minutes;
	}

	public void setMinutes(final String minutes) {
		this.minutes = minutes;
	}

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public String getSeconds() {
		return this.seconds;
	}

	public void setSeconds(final String seconds) {
		this.seconds = seconds;
	}

}

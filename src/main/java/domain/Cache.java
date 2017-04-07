
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Cache extends DomainEntity {

	private String	hours;
	private String	minutes;
	private String	seconds;


	public String getHours() {
		return this.hours;
	}

	public void setHours(final String hours) {
		this.hours = hours;
	}

	public String getMinutes() {
		return this.minutes;
	}

	public void setMinutes(final String minutes) {
		this.minutes = minutes;
	}

	public String getSeconds() {
		return this.seconds;
	}

	public void setSeconds(final String seconds) {
		this.seconds = seconds;
	}

}


package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Cache extends DomainEntity {

	private String time;


	public String getTime() {
		return this.time;
	}
	public void setTime(final String time) {
		this.time = time;
	}

}

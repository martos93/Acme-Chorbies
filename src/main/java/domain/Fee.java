
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	//Atributes
	private Money	managerAmount;
	private Money	chorbiAmount;


	public Money getManagerAmount() {
		return this.managerAmount;
	}

	public void setManagerAmount(final Money managerAmount) {
		this.managerAmount = managerAmount;
	}

	public Money getChorbiAmount() {
		return this.chorbiAmount;
	}

	public void setChorbiAmount(final Money chorbiAmount) {
		this.chorbiAmount = chorbiAmount;
	}

}

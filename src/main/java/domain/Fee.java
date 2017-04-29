
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	//Atributes
	private Double	managerAmount;
	private Double	chorbiAmount;


	public Double getManagerAmount() {
		return this.managerAmount;
	}

	public void setManagerAmount(final Double managerAmount) {
		this.managerAmount = managerAmount;
	}

	public Double getChorbiAmount() {
		return this.chorbiAmount;
	}

	public void setChorbiAmount(final Double chorbiAmount) {
		this.chorbiAmount = chorbiAmount;
	}

}

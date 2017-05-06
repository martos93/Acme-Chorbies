
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	//Atributes
	private Double	managerAmount;
	private Double	chorbiAmount;


	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public Double getManagerAmount() {
		return this.managerAmount;
	}

	public void setManagerAmount(final Double managerAmount) {
		this.managerAmount = managerAmount;
	}

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public Double getChorbiAmount() {
		return this.chorbiAmount;
	}

	public void setChorbiAmount(final Double chorbiAmount) {
		this.chorbiAmount = chorbiAmount;
	}

}

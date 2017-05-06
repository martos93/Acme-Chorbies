
package usecases2;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Fee;
import repositories.FeeRepository;
import services.FeeService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU9 extends AbstractTest {

	@Autowired
	private FeeService		feeService;

	@Autowired
	private FeeRepository	feeRepository;


	//Test CU9:
	//Un usuario logueado como admin cambia la tarifa (fee) que se les cobra a los chorbies y managers
	//RF: An actor who is authenticated as an administrator must be able to:
	//Change the fee that is charged to managers and chorbies. (Note that they need not be the same.)

	@Test
	public void changeFee() {
		final Object[][] testingData = {
			{
				null, 10.0, 50.0, IllegalArgumentException.class
			}, {
				"chorbi1", 1.0, 0.0, IllegalArgumentException.class
			}, {
				"admin", 10.0, 5.0, null
			}, {
				"admin", -5.0, 0.0, ConstraintViolationException.class
			}, {
				"admin", 6.0, -7.0, ConstraintViolationException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeFee((String) testingData[i][0], (Double) testingData[i][1], (Double) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void changeFee(final String username, final Double amountChorbies, final Double amountManagers, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Fee fee = this.feeService.selectFee();
			fee.setChorbiAmount(amountChorbies);
			fee.setManagerAmount(amountManagers);

			this.feeService.save(fee);

			this.feeRepository.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

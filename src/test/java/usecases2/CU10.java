
package usecases2;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chorbi;
import repositories.ChorbiRepository;
import services.ChorbiService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU10 extends AbstractTest {

	@Autowired
	private ChorbiService		chorbiService;

	@Autowired
	private ChorbiRepository	chorbiRepository;

	//Test CU10:
	//Un usuario logueado como admin ejecuta un proceso para actualizar las cuotas mensuales totales que los chorbies
	//tendrán que pagar. Recuerde que los chorbies no deben ser conscientes de la simulación.
	//RF: An actor who is authenticated as an administrator must be able to:
	//Run a process to update the total monthly fees that the chorbies would have to pay. Recall that chorbies must not be aware of the simulation.


	@Test
	public void updateFees() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class
			}, {
				"admin", null
			}, {
				"chorbi1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.updateFees((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void updateFees(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Chorbi> all = this.chorbiService.findAll();
			for (final Chorbi chorbi : all)
				this.chorbiService.updateFee(chorbi);
			this.chorbiRepository.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}


package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.ChorbiRepository;
import services.ChorbiService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU2 extends AbstractTest {

	@Autowired
	private ChorbiService		chorbiService;

	@Autowired
	private ChorbiRepository	chorbiRepository;


	//Tests-------------------------------------------------------------------

	//CU4: Un usuario logueado ve la lista de chorbis logueados en el sistema.

	//RF: 	An actor who is authenticated must be able to:
	//Browse the list of chorbies who have registered to the system.

	@Test
	public void listChorbies() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class
			}, {
				"admin", null
			}, {
				"chorbi1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listChorbies((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void listChorbies(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			this.chorbiService.listAll();

			this.chorbiRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

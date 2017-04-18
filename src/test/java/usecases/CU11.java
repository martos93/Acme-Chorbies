
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import services.AdministratorService;
import services.ChorbiService;
import utilities.AbstractTest;
import domain.Chorbi;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU11 extends AbstractTest {

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private ChorbiRepository		chorbiRepository;

	@Autowired
	private AdministratorService	administratorService;


	//Tests-------------------------------------------------------------------

	//CU11: Un administrador banea a un chorbi.

	//RF: 	An actor who is authenticated as an administrator must be able to:
	//Ban a chorbi, that is, to disable his or her account.

	@Test
	public void bannChorbi() {
		final Object[][] testingData = {
			{
				null, "chorbi", IllegalArgumentException.class
			}, {
				"admin", "chorbi1", null
			}, {
				"chorbi1", "chorbi2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.bannChorbi((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void bannChorbi(final String username, final String chorbi, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi c = this.chorbiService.findChorbiByUsername(chorbi);
			this.administratorService.banChorbi(c);

			Assert.isTrue(c.getUserAccount().isEnabled() == false);

			this.chorbiRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

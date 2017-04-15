
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Cache;
import repositories.CacheRepository;
import services.CacheService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU14 extends AbstractTest {

	@Autowired
	private CacheService	cacheService;

	@Autowired
	private CacheRepository	cacheRepository;


	//Test
	//CU14 - Un usuario logueado como admin modifica el tiempo que se quedan cacheados los templates de búsqueda.
	//RF: An actor who is authenticated as an administrator must be able to:
	//Change (...) the time that the results of search templates are cached. The time must be
	//expressed in hours, minutes, and seconds.

	@Test
	public void changeCache() {
		final Object[][] testingData = {
			{
				null, "10", "50", "30", IllegalArgumentException.class
			}, {
				"admin", "10", "50", "30", null
			}, {
				"chorbi1", "0", "0", "0", IllegalArgumentException.class
			}, {
				"chorbi2", "", "", "", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeCache((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void changeCache(final String username, final String hours, final String minutes, final String seconds, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Cache c = this.cacheService.selectCache();
			c.setHours(hours);
			c.setMinutes(minutes);
			c.setSeconds(seconds);
			this.cacheService.save(c);

			this.cacheRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

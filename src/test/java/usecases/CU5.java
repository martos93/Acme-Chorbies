
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Love;
import repositories.LoveRepository;
import services.ChorbiService;
import services.LoveService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU5 extends AbstractTest {

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private LoveService		loveService;

	//Repositories----------------------------------------------------------------
	@Autowired
	private LoveRepository	loveRepository;

	//Tests-----------------------------------------------------------------------


	//CU5: Un usuario logueado como chorbi da like a otro chorbi.
	//RF: Like another chorbi; a like may be cancelled at any time.

	@Test
	public void giveLove() {
		final Object[][] testingData = {
			{
				"chorbi1", "chorbi2", "hola", null
			}, {
				"admin", "chorbi1", "hola", NullPointerException.class
			}, {
				"chorbi1", "chorbi2", null, null
			}, {
				"chorbi1", "chorbi1", "hola", null
			}, {
				"chorbi3", null, "hola", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.giveLove((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void giveLove(final String username, final String string, final String string2, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Love love = this.loveService.create();
			love.setLoved(this.chorbiService.findChorbiByUsername(string));
			love.setComment(string2);
			this.loveService.addLove(love);
			this.loveRepository.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}


package usecases;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import domain.Love;
import repositories.LoveRepository;
import services.ChorbiService;
import services.LoveService;
import utilities.AbstractTest;

public class CU6 extends AbstractTest {

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private LoveService		loveService;

	//Repositories----------------------------------------------------------------
	@Autowired
	private LoveRepository	loveRepository;

	//Tests-----------------------------------------------------------------------


	//CU6:  Un usuario logueado como chorbi deshace el like que le dio anteriormente a otro chorbi.
	//RF: Like another chorbi; a like may be cancelled at any time.

	@Test
	public void searchChorbies() {
		final Object[][] testingData = {
			{
				"chorbi1", 32768, NullPointerException.class
			}, {
				"admin", 32768, NullPointerException.class
			}, {
				"chorbi1", 32769, NullPointerException.class
			}, {
				"chorbi1", 65536, NullPointerException.class
			}, {
				"chorbi1", 65536, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchChorbies((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void searchChorbies(final String username, final int id, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Love love = this.loveService.findOne(id);
			if (this.chorbiService.findByPrincipal().getLove().contains(love))
				this.loveService.delete(love);
			this.loveRepository.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		System.out.println(caught);
		this.checkExceptions(expected, caught);
	}

}

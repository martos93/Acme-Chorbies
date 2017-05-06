
package usecases;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chorbi;
import services.AdministratorService;
import services.ChorbiService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU15 extends AbstractTest {

	//Services
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ChorbiService			chorbiService;


	@Test
	public void dashboard() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class
			}, {
				"admin", null
			}, {
				"chorbi1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++) {
			this.template1((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template2((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template3((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template4((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template5((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template6((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template7((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template8((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template9((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template10((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template11((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template12((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template13((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void template1(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final List<String> chorbiesByCity = this.administratorService.chorbiesByCity();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template2(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final List<String> chorbiesByCountry = this.administratorService.chorbiesByCountry();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void template3(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Object[] minMaxAvgAgesChorbies = this.administratorService.minMaxAvgAgesChorbies();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template4(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Double ratioChorbiesInvalidCreditCard = this.administratorService.ratioChorbiesInvalidCreditCard();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template5(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Double ratioChorbiesSearchActivites = this.administratorService.ratioChorbiesSearchActivites();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template6(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Double ratioChorbiesSearchFriendShip = this.administratorService.ratioChorbiesSearchFriendShip();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template7(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Double ratioChorbiesSearchLove = this.administratorService.ratioChorbiesSearchLove();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void template8(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final List<Chorbi> chorbiesSortedByLikes = this.chorbiService.chorbiesSortedByLikes();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template9(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Object[] minMaxAvgLikesPerChorbi = this.administratorService.minMaxAvgLikesPerChorbi();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template10(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Object[] minMaxAvgChirpsRecieved = this.administratorService.minMaxAvgChirpsRecieved();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void template11(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Object[] minMaxAvgChirpsSended = this.administratorService.minMaxAvgChirpsSended();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template12(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Chorbi> chorbiMoreChirpsRecieved = this.chorbiService.chorbiMoreChirpsRecieved();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template13(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Chorbi> chorbiMoreChirpsSended = this.chorbiService.chorbiMoreChirpsSended();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

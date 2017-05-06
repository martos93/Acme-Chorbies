
package usecases2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chorbi;
import domain.Manager;
import services.ChorbiService;
import services.ManagerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU11 extends AbstractTest {

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ManagerService	managerService;


	//Test CU11
	//Un usuario logueado como admin muestra una vista dashboard con toda la información disponible.
	//RF: An actor who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information: (...)

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
		}

	}
	protected void template1(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final List<Manager> sortedManagersByEvents = this.managerService.sortedManagersByEvents();
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
			final ArrayList<Object> obj = this.managerService.managersAndFees();
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
			final Collection<Chorbi> sortedChorbiesByEvents = this.chorbiService.sortedChorbiesByEvents();
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
			final ArrayList<Object> obj2 = this.chorbiService.chorbiesAndFees();
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
			final Object[] minAvgMaxStarsPerChorbi = this.chorbiService.minAvgMaxStarsPerChorbi();
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
			final List<Chorbi> sortedChorbiesByStars = this.chorbiService.sortedChorbiesByStars();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}


package usecases2;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Event;
import domain.Manager;
import services.ManagerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU3 extends AbstractTest {

	@Autowired
	private repositories.EventRepository	eventRepository;
	@Autowired
	private ManagerService					managerService;


	//CU 3 -  Un usuario logueado como manager accede a una vista donde
	//se listan los eventos que él o ella ha organizado (es decir, ha creado).
	//Los eventos se deben poder ordenar por el número de plazas disponibles.
	//(Esto último es de un RNF)

	@Test
	public void listEventManager() {
		final Object[][] testingData = {
			{
				"manager1", null
			}, {
				"manager2", null
			}, {
				"manager14", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Manager manager = this.managerService.findByPrincipal();
			final Collection<Event> events = manager.getEvents();
			Assert.isTrue(manager.getEvents().equals(events));
			this.eventRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

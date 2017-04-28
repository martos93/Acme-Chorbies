
package usecases2;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chorbi;
import domain.Event;
import services.ChorbiService;
import services.EventService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU8 extends AbstractTest {

	@Autowired
	private EventService					eventService;

	@Autowired
	private repositories.EventRepository	eventRepository;
	@Autowired
	private ChorbiService					chorbiService;


	//CU 8 - Un usuario logueado como chorbi accede a una vista donde se listan los eventos a los que está apuntado. Los eventos se deben poder ordenar por el número de plazas disponibles. (Esto último es de un RNF). Selecciona uno de los eventos y se desapunta.
	//RF:   An actor who is authenticated as a chorbi must be able to:
	//      Register to an event as long as there are enough seats available.

	@Test
	public void unregisterEvent() {
		final Object[][] testingData = {
			{
				"chorbi1", IllegalArgumentException.class, 57
			}, {
				"chorbi2", IllegalArgumentException.class, 57
			}, {
				"chorbi3", null, 57
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
	}

	protected void template(final String username, final Class<?> expected, final int eventid) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
			final Event event = this.eventService.findOne(eventid);
			this.eventService.unregisterToEvent(event, chorbi);
			this.eventRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

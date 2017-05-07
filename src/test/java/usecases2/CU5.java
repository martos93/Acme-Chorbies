
package usecases2;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Event;
import services.ChirpService;
import services.EventService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU5 extends AbstractTest {

	@Autowired
	private EventService					eventService;

	@Autowired
	private repositories.EventRepository	eventRepository;
	@Autowired
	private ChirpService					chirpService;

	//CU 5 - Un usuario logueado como manager modifica un evento creado por él o ella.
	//Una vez se hayan guardado los cambios, inmediatamente el sistema enviará chirps
	//a todos los chorbies apuntados en el evento. El autor del chirp será el manager
	//que ha creado el evento.


	//RF:   An actor who is authenticated as a chorbi must be able to:
	//      Register to an event as long as there are enough seats available.

	@Test
	public void modifyEventManager() {
		final Object[][] testingData = {
			{
				"manager1", null, 63
			}, {
				"manager3", null, 64
			}, {
				"manager3", NullPointerException.class, 20000
			}, {
				"manager33", IllegalArgumentException.class, 65
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
	}

	protected void template(final String username, final Class<?> expected, final int eventid) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Event event = this.eventService.findOne(eventid);
			event.setSeatsOffered(500);
			this.eventService.save(event);
			this.chirpService.broadcastChirps(event, null, null);
			this.eventRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		System.out.println(caught);
		this.checkExceptions(expected, caught);
	}
}

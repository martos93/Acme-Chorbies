
package usecases2;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.EventRepository;
import services.EventService;
import utilities.AbstractTest;
import domain.Event;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU4 extends AbstractTest {

	@Autowired
	private EventService	eventService;

	@Autowired
	private EventRepository	eventRepository;


	@Test
	public void createEvent() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "title", "desc", 2, ""
			}, {
				"admin", IllegalArgumentException.class, "title", "desc", 2, ""
			}, {
				"chorbi1", IllegalArgumentException.class, "title", "desc", 2, ""
			}, {
				"manager1", null, "dsadadsa", "desc", 2, "http:\\google.com/123"
			}, {
				"manager1", null, "dsadadsa", "desc", 1, ""
			}, {
				"manager1", null, "dsadadsa", "desc", 200, "http:\\pict.com/dsad"
			}, {
				"manager1", ConstraintViolationException.class, "title", "desc", 0, ""
			}, {
				"manager1", ConstraintViolationException.class, "title", "", 1, ""
			}, {
				"manager1", ConstraintViolationException.class, "", "desc", 1, ""
			}, {
				"manager1", ConstraintViolationException.class, "title", "desc", 1, "ddd"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createEvent((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (int) testingData[i][4], (String) testingData[i][5]);
	}

	protected void createEvent(final String username, final Class<?> expected, final String title, final String description, final int seatsOffered, final String picture) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Event c = this.eventService.create();
			c.setTitle(title);
			c.setDescription(description);
			c.setSeatsOffered(seatsOffered);
			c.setPicture(picture);
			c.setMoment(new Date(System.currentTimeMillis() - 1000));
			this.eventService.newEvent(c);
			this.eventRepository.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

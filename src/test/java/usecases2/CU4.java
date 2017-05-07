
package usecases2;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.EventRepository;
import services.EventService;
import services.ManagerService;
import utilities.AbstractTest;
import domain.Event;
import domain.Manager;

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

	@Autowired
	private ManagerService	managerService;


	//CU4: Un usuario logueado como manager registra un nuevo evento. 
	//Teniendo una tarjeta de crédito válida que no debe expirar en menos de un día. 
	//Además, el sistem simulará que él o ella cobra una cuota de 1.00.

	//RF:An actor who is authenticated as a manager must be able to:
	//Manage the events that he or she organises, which includes listing, registering, modifying, and deleting them. In order to register a new event, 
	//he must have registered a valid credit card that must not expire in less than one day. Every time he or she registers an event, the system will 
	//simulate that he or she is charged a 1.00 fee.

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
				"manager2", null, "dsadadsa", "desc", 1, ""
			}, {
				"manager1", null, "dsadadsa", "desc", 200, "http:\\pict.com/dsad"
			}, {
				"manager1", ConstraintViolationException.class, "title", "desc", 0, ""
			}, {
				"manager2", ConstraintViolationException.class, "title", "", 1, ""
			}, {
				"manager1", ConstraintViolationException.class, "", "desc", 1, ""
			}, {
				"manager3", ConstraintViolationException.class, "title", "desc", 1, "ddd"
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
			Manager l = this.managerService.create();
			Double actualDue = 0.0;
			if (this.managerService.checkIsManager()) {
				l = this.managerService.getLoggedManager();
				actualDue = l.getAmountDue();
			}

			final Event c = this.eventService.create();
			c.setTitle(title);
			c.setDescription(description);
			c.setSeatsOffered(seatsOffered);
			c.setPicture(picture);
			c.setMoment(new Date(System.currentTimeMillis() - 1000));
			this.eventService.newEvent(c);

			this.eventRepository.flush();
			Assert.isTrue(actualDue + 1 == l.getAmountDue());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}

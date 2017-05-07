
package usecases2;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.EventService;
import utilities.AbstractTest;
import domain.Event;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU2 extends AbstractTest {

	@Autowired
	private EventService	eventService;


	@Test
	public void closeEventList() {
		final Object[][] testingData = {
			{
				null, null
			}, {
				"admin", null
			}, {
				"chorbi1", null
			}, {
				"manager1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.closeEventList((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void closeEventList(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Event> closeEvents = this.eventService.findByMonthToStartAndSeats();
			final Calendar t = Calendar.getInstance();
			t.add(Calendar.MONTH, 1);
			final Date next = t.getTime();

			for (final Event e : closeEvents) {
				Assert.isTrue(e.getMoment().before(next) && e.getMoment().after(Calendar.getInstance().getTime()));
				Assert.isTrue(e.getSeatsOffered() > 0);
			}

			final Collection<Event> pastEvents = this.eventService.findByPastsEvents();

			for (final Event e : pastEvents)
				Assert.isTrue(e.getMoment().before(Calendar.getInstance().getTime()));

			final Collection<Event> futureEvents = this.eventService.findFutureEvents();
			for (final Event e : futureEvents)
				Assert.isTrue(e.getMoment().after(next));

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}

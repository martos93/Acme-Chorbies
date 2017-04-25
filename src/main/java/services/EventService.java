
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EventRepository;
import domain.Event;

@Service
@Transactional
public class EventService {

	@Autowired
	private EventRepository	eventRepository;


	public Collection<Event> findAll() {
		return this.eventRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	public Collection<Event> findByMonthToStartAndSeats() {
		final Date now = new Date(System.currentTimeMillis() - 1000);
		if (now.getMonth() == 12) {
			now.setMonth(1);
			now.setYear(now.getYear() + 1);
		} else
			now.setMonth(now.getMonth() + 1);

		return this.eventRepository.findByMonthToStartAndSeats(now);
	}
	public Collection<Event> findByPastsEvents() {
		return this.eventRepository.findByPastEvents();
	}

	@SuppressWarnings("deprecation")
	public Collection<Event> findFutureEvents() {
		final Date now = new Date(System.currentTimeMillis() - 1000);
		if (now.getMonth() == 12) {
			now.setMonth(1);
			now.setYear(now.getYear() + 1);
		} else
			now.setMonth(now.getMonth() + 1);
		return this.eventRepository.findFutureEvents(now);
	}
}


package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Chirp;
import domain.Chorbi;
import domain.Event;
import domain.Manager;
import repositories.EventRepository;

@Service
@Transactional
public class EventService {

	//Repository--------------------------------------------------------------------
	@Autowired
	private EventRepository	eventRepository;

	//Service-----------------------------------------------------------------------
	@Autowired
	private ManagerService	managerService;

	@Autowired
	private ChirpService	chirpService;


	//CRUD Methods------------------------------------------------------------------

	public Event findOne(final int id) {
		return this.eventRepository.findOne(id);
	}

	public Collection<Event> findAll() {
		return this.eventRepository.findAll();
	}

	public void delete(final Event event) {
		final Manager manager = this.managerService.findByPrincipal();

		this.managerService.checkLoggedIsManager();

		final ArrayList<Chorbi> chorbis = new ArrayList<>(event.getChorbies());

		final Chirp chirp = this.chirpService.create();
		chirp.setSenderM(manager);
		chirp.setSubject("Deleted Event:" + event.getTitle());

		for (final Chorbi chorbi : chorbis) {
			chirp.setText("Sorry " + chorbi.getName() + ", but this event has been deleted!");
			chirp.setReceiver(chorbi);
			chorbi.getEvents().remove(event);
			this.chirpService.sendChirp(chirp);
		}

		manager.getEvents().remove(event);

		this.eventRepository.delete(event);

	}

	//Other Methods------------------------------------------------------------
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

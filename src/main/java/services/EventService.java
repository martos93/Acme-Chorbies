
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Event;
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

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private Validator		validator;


	//CRUD Methods------------------------------------------------------------------

	public Event findOne(final int id) {
		return this.eventRepository.findOne(id);
	}

	public Collection<Event> findAll() {
		return this.eventRepository.findAll();
	}

	public Event save(final Event event) {
		Assert.notNull(event);
		final Event eventSaved = this.eventRepository.save(event);
		return eventSaved;
	}

	public void delete(final Event event) {

		this.managerService.checkLoggedIsManager();

		//		final ArrayList<Chorbi> chorbis = new ArrayList<>(event.getChorbies());
		//
		//		final Chirp chirp = this.chirpService.create();
		//		chirp.setSubject("Deleted Event:" + event.getTitle());
		//
		//		for (final Chorbi chorbi : chorbis) {
		//			chirp.setText("Sorry " + chorbi.getName() + ", but this event has been deleted!");
		//			chirp.setReceiver(chorbi);
		//			chorbi.getEvents().remove(event);
		//			chorbi.getReceived().add(chirp);
		//			chirp.setSenderC(chorbi);
		//			this.chorbiService.save(chorbi);
		//			this.chirpService.save(chirp);
		//		}

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

	public Event reconstruct(final Event event, final BindingResult binding) {
		Event res;

		if (event.getId() == 0)
			res = event;
		else {
			res = this.eventRepository.findOne(event.getId());
			res.setTitle(event.getTitle());
			res.setDescription(event.getDescription());
			res.setPicture(event.getPicture());
			res.setSeatsOffered(event.getSeatsOffered());

			this.validator.validate(res, binding);
		}

		return res;

	}

}

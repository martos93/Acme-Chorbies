
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import domain.Chorbi;
import domain.Event;

@Service
@Transactional
public class EventService {

	//Repository--------------------------------------------------------------------
	@Autowired
	private EventRepository	eventRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private Validator		validator;


	public Event create() {
		final Event res = new Event();
		res.setMoment(Calendar.getInstance().getTime());
		res.setManager(this.managerService.getLoggedManager());
		res.setChorbies(new ArrayList<Chorbi>());

		return res;

	}

	public Event save(final Event event) {
		return this.eventRepository.save(event);
	}

	//CRUD Methods------------------------------------------------------------------

	public Event findOne(final int id) {
		return this.eventRepository.findOne(id);
	}

	public Collection<Event> findAll() {
		return this.eventRepository.findAll();
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
	public Collection<Event> findByMonthToStartAndSeats() {
		final Calendar t = Calendar.getInstance();
		t.add(Calendar.MONTH, 1);
		final Date now = t.getTime();

		return this.eventRepository.findByMonthToStartAndSeats(now);
	}
	public Collection<Event> findByPastsEvents() {
		return this.eventRepository.findByPastEvents();
	}

	public Collection<Event> findFutureEvents() {
		final Calendar t = Calendar.getInstance();
		t.add(Calendar.MONTH, 1);
		final Date now = t.getTime();

		return this.eventRepository.findFutureEvents(now);
	}

	public Event reconstruct(final Event event, final BindingResult binding) {

		Event res = new Event();

		if (event.getId() == 0)
			res = event;
		else {
			res = this.eventRepository.findOne(event.getId());
			res.setChorbies(event.getChorbies());
			res.setDescription(event.getDescription());
			res.setMoment(event.getMoment());
			res.setPicture(event.getPicture());
			res.setSeatsOffered(event.getSeatsOffered());
			res.setTitle(event.getTitle());
		}
		this.validator.validate(res, binding);

		return res;
	}

}

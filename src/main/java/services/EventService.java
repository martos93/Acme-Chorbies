
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import domain.Chorbi;
import domain.CreditCard;
import domain.Event;
import domain.Manager;

@Service
@Transactional
public class EventService {

	//Repository--------------------------------------------------------------------
	@Autowired
	private EventRepository	eventRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private Validator		validator;


	public Event create() {
		final Event res = new Event();
		res.setManager(this.managerService.getLoggedManager());
		res.setChorbies(new ArrayList<Chorbi>());

		return res;

	}

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

	public Event newEvent(Event event) {

		final Manager logged = this.managerService.getLoggedManager();
		Assert.isTrue(event.getManager().equals(logged));
		final CreditCard c = event.getManager().getCreditCard();
		Assert.isTrue(this.actorService.checkCreditCard(c));

		event = this.save(event);
		logged.setAmountDue(logged.getAmountDue() + 1.00);
		this.managerService.save(logged);

		return event;
	}

	public Event editEvent(Event event) {
		final Manager logged = this.managerService.getLoggedManager();
		Assert.isTrue(event.getManager().equals(logged));
		event = this.save(event);
		return event;
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

	public Collection<Event> findByManager() {
		this.managerService.checkLoggedIsManager();
		final Manager r = this.managerService.getLoggedManager();
		final Collection<Event> res = this.eventRepository.findByManager(r.getId());

		return res;
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
			res.setTitle(event.getTitle());
			res.setDescription(event.getDescription());
			res.setPicture(event.getPicture());
			res.setSeatsOffered(event.getSeatsOffered());
			res.setMoment(event.getMoment());
		}
		this.validator.validate(res, binding);

		return res;

	}

	public void registerToEvent(final Event event, final Chorbi chorbi) {
		Assert.isTrue(this.chorbiService.getLoggedChorbi().getId() == chorbi.getId());
		Assert.isTrue(event.getSeatsOffered() - event.getChorbies().size() != 0);
		Assert.isTrue(chorbi.getEvents().contains(event) == false);
		final Date date = new Date(System.currentTimeMillis());
		Assert.isTrue(event.getMoment().getTime() >= date.getTime());
		event.getChorbies().add(chorbi);
		this.save(event);
	}

	public void unregisterToEvent(final Event event, final Chorbi chorbi) {
		Assert.isTrue(this.chorbiService.getLoggedChorbi().getId() == chorbi.getId());
		Assert.isTrue(chorbi.getEvents().contains(event) == true);
		final Date date = new Date(System.currentTimeMillis());
		Assert.isTrue(event.getMoment().getTime() >= date.getTime(), "This is a closed event");

		event.getChorbies().remove(chorbi);
		this.save(event);
	}

}

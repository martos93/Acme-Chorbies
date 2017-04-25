
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import domain.Event;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	@Autowired
	private EventService	eventService;


	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView();

		final Collection<Event> events = this.eventService.findByMonthToStartAndSeats();
		final Collection<Event> eventsP = this.eventService.findByPastsEvents();
		final Collection<Event> eventsF = this.eventService.findFutureEvents();

		res.addObject("event", events);
		res.addObject("eventP", eventsP);
		res.addObject("eventF", eventsF);

		return res;
	}

}

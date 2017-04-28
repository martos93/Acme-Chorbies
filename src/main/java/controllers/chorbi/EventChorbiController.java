
package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Chorbi;
import domain.Event;
import services.ChorbiService;
import services.EventService;

@Controller
@RequestMapping("/event/chorbi")
public class EventChorbiController extends AbstractController {

	@Autowired
	private EventService	eventService;

	@Autowired
	private ChorbiService	chorbiService;


	@RequestMapping("/listMyEvents")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("event/listMyEvents");

		final Collection<Event> events = this.chorbiService.findByPrincipal().getEvents();

		res.addObject("events", events);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int id) {
		final Event event = this.eventService.findOne(id);
		final Chorbi chorbi = this.chorbiService.findByPrincipal();

		try {
			this.eventService.registerToEvent(event, chorbi);
			final ModelAndView result = new ModelAndView("redirect:../chorbi/listMyEvents.do");
			return result;
		} catch (final Throwable oops) {
			final ModelAndView result = new ModelAndView("redirect:../list.do");
			return result;
		}

	}

	@RequestMapping(value = "/unregister", method = RequestMethod.GET)
	public ModelAndView unregister(@RequestParam final int id) {
		final Event event = this.eventService.findOne(id);
		final Chorbi chorbi = this.chorbiService.findByPrincipal();

		this.eventService.unregisterToEvent(event, chorbi);
		final ModelAndView result = new ModelAndView("redirect:../chorbi/listMyEvents.do");

		return result;

	}
}

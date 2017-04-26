
package controllers.manager;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Event;
import domain.Manager;
import services.EventService;
import services.ManagerService;

@Controller
@RequestMapping("event/manager")
public class EventManagerController extends AbstractController {

	//Service---------------------------------------------------
	@Autowired
	private ManagerService	managerService;

	@Autowired
	private EventService	eventService;


	//Constructor-----------------------------------------------

	public EventManagerController() {
		super();
	}

	//Methods---------------------------------------------------

	@RequestMapping("/listevents")
	public ModelAndView listEvents() {
		final ModelAndView modelAndView;

		final Manager manager = this.managerService.findByPrincipal();

		final ArrayList<Event> events = new ArrayList<>(manager.getEvents());
		Collections.sort(events);
		Collections.reverse(events);

		modelAndView = new ModelAndView("event/listm");
		modelAndView.addObject("events", events);
		modelAndView.addObject("requestURI", "/listevents.do");

		return modelAndView;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView result;
		final Event event = this.eventService.findOne(id);
		try {
			this.eventService.delete(event);
			result = new ModelAndView("redirect:listevents.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:listevents.do");
		}

		return result;
	}

}

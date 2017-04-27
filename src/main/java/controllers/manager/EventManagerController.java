
package controllers.manager;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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

	public ModelAndView createEditModelAndView(final Event event) {
		ModelAndView modelAndView;
		modelAndView = this.createEditModelAndView(event, null);
		return modelAndView;
	}

	public ModelAndView createEditModelAndView(final Event event, final String string) {
		ModelAndView modelAndView;

		modelAndView = new ModelAndView("event/edit");
		modelAndView.addObject("requestURI", "event/manager/edit.do");
		modelAndView.addObject("event", event);
		modelAndView.addObject("message", string);
		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		ModelAndView result;
		final Event event = this.eventService.findOne(id);

		Assert.notNull(event);

		result = this.createEditModelAndView(event);
		result.addObject("requestURI", "event/manager/edit.do?id=" + event.getId());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Event event, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		event = this.eventService.reconstruct(event, bindingResult);
		if (bindingResult.hasErrors()){
			modelAndView = this.createEditModelAndView(event);}
		else{
			try {
				this.eventService.save(event);
				modelAndView = this.listEvents();

			} catch (final Throwable oops) {
				modelAndView = this.createEditModelAndView(event, "event.commit.error");
			}}
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

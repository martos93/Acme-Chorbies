
package controllers.manager;

import java.util.ArrayList;

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
import forms.ChirpManagerForm;
import services.ChirpService;
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

	@Autowired
	private ChirpService	chirpService;


	//Constructor-----------------------------------------------

	public EventManagerController() {
		super();
	}

	//Methods---------------------------------------------------

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
		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(event);
		else
			try {

				if (event.getId() != 0) {
					final ChirpManagerForm chirpManagerForm = new ChirpManagerForm();
					chirpManagerForm.setSubject(event.getTitle().toUpperCase() + " was modified!");
					chirpManagerForm.setText("The " + event.getTitle().toUpperCase() + " has been modified by " + event.getManager().getName() + "!");
					chirpManagerForm.setAttachments(new ArrayList<String>());

					this.chirpService.broadcastChirps(event, chirpManagerForm, bindingResult);
				}

				this.eventService.save(event);
				modelAndView = this.listMyEvents();

				this.eventService.editEvent(event);
				modelAndView = this.listMyEvents();

			} catch (final Throwable oops) {
				modelAndView = this.createEditModelAndView(event, "event.commit.error");
			}
		return modelAndView;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView result;
		final Event event = this.eventService.findOne(id);
		try {

			final ChirpManagerForm chirpManagerForm = new ChirpManagerForm();
			chirpManagerForm.setSubject(event.getTitle().toUpperCase() + " was deleted!");
			chirpManagerForm.setText("Sorry but " + event.getTitle().toUpperCase() + " has been deleted by " + event.getManager().getName() + "!");
			chirpManagerForm.setAttachments(new ArrayList<String>());

			this.chirpService.broadcastChirps(event, chirpManagerForm, null);
			this.eventService.delete(event);
			result = new ModelAndView("redirect:listMyEvents.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:listMyEvents.do");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("event/create");
		final Event event = this.eventService.create();
		res.addObject("event", event);
		res.addObject("requestUri", "event/manager/create.do");
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(Event event, final BindingResult binding) {

		ModelAndView res = new ModelAndView();

		event = this.eventService.reconstruct(event, binding);

		if (binding.hasErrors()) {
			res = new ModelAndView("event/create");
			res.addObject("requestUri", "event/manager/create.do");
			res.addObject("event", event);

		} else {

			this.eventService.newEvent(event);
			res = this.listMyEvents();
		}
		return res;

	}

	@RequestMapping("/listMyEvents")
	public ModelAndView listMyEvents() {
		final ModelAndView modelAndView;

		final ArrayList<Event> events = new ArrayList<>(this.eventService.findByManager());

		modelAndView = new ModelAndView("event/listMyEvents");
		modelAndView.addObject("events", events);
		modelAndView.addObject("requestURI", "/listMyEvents.do");

		return modelAndView;
	}

}

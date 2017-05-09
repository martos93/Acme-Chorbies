
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

import services.ChirpService;
import services.EventService;
import controllers.AbstractController;
import domain.Event;
import forms.ChirpManagerForm;

@Controller
@RequestMapping("/chirp/manager")
public class ChirpManagerController extends AbstractController {

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private EventService	eventService;


	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView broadcast(@RequestParam final int eventId) {
		ModelAndView result;
		final ChirpManagerForm chirpManagerForm = new ChirpManagerForm();

		Assert.notNull(chirpManagerForm);

		result = this.createModelAndView(chirpManagerForm, eventId);

		result.addObject("chirpManagerForm", chirpManagerForm);
		result.addObject("eventId", eventId);
		return result;
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@RequestParam final int eventId, final ChirpManagerForm chirpManagerForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createModelAndView(chirpManagerForm, eventId);
		else
			try {
				final Event event = this.eventService.findOne(eventId);
				this.chirpService.broadcastChirps(event, chirpManagerForm, binding);
				result = new ModelAndView("event/listMyEvents");
				final ArrayList<Event> events = new ArrayList<>(this.eventService.findByManager());
				result.addObject("events", events);
				result.addObject("requestURI", "event/manager/listMyEvents.do");

			} catch (final Throwable oops) {
				result = this.createModelAndView(chirpManagerForm, eventId, "chirp.commit.error");
			}
		return result;
	}

	protected ModelAndView createModelAndView(final ChirpManagerForm chirpManagerForm, final int eventId) {
		ModelAndView result;

		result = this.createModelAndView(chirpManagerForm, eventId, null);

		return result;
	}

	protected ModelAndView createModelAndView(final ChirpManagerForm chirpManagerForm, final int eventId, final String message) {
		final ModelAndView result = new ModelAndView("chirp/broadcast");

		result.addObject("chirpManagerForm", chirpManagerForm);
		result.addObject("message", message);
		result.addObject("requestURI", "chirp/manager/broadcast.do?eventId=" + eventId);
		return result;
	}
}

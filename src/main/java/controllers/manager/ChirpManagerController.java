package controllers.manager;

import controllers.AbstractController;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;
import forms.ChirpForm;
import forms.ChirpManagerForm;
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

import java.util.Collection;

@Controller
@RequestMapping("/chirp/manager")
public class ChirpManagerController extends AbstractController {
    @Autowired
    private ChirpService chirpService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/broadcast", method = RequestMethod.GET)
    public ModelAndView broadcast(@RequestParam int eventId) {
        ModelAndView result;
        final ChirpManagerForm chirpManagerForm = new ChirpManagerForm();

        Assert.notNull(chirpManagerForm);

        result = this.createModelAndView(chirpManagerForm, eventId);

        result.addObject("chirpManagerForm", chirpManagerForm);
        result.addObject("eventId", eventId);
        return result;
    }

    @RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "save")
    public ModelAndView send(@RequestParam int eventId, final ChirpManagerForm chirpManagerForm, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createModelAndView(chirpManagerForm, eventId);
        else
            try {
                Event event = this.eventService.findOne(eventId);
                this.chirpService.broadcastChirps(event, chirpManagerForm, binding);
                result = new ModelAndView("welcome/index");

            } catch (final Throwable oops) {
                result = this.createModelAndView(chirpManagerForm, eventId, "chirp.commit.error");
            }
        return result;
    }

    protected ModelAndView createModelAndView(final ChirpManagerForm chirpManagerForm, int eventId) {
        ModelAndView result;

        result = this.createModelAndView(chirpManagerForm, eventId, null);

        return result;
    }

    protected ModelAndView createModelAndView(final ChirpManagerForm chirpManagerForm,int eventId, final String message ){
        final ModelAndView result = new ModelAndView("chirp/broadcast");

        result.addObject("chirpManagerForm", chirpManagerForm);
        result.addObject("message", message);
        result.addObject("requestURI", "chirp/manager/broadcast.do?eventId="+eventId);
        return result;
    }
}

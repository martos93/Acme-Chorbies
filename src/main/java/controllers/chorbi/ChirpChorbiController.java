
package controllers.chorbi;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.ChorbiService;
import controllers.AbstractController;
import domain.Chirp;
import domain.Chorbi;
import forms.ChirpForm;

@Controller
@RequestMapping("/chirp/chorbi")
public class ChirpChorbiController extends AbstractController {

	@Autowired
	private ChirpService	chirpService;

	@Autowired
	private ChorbiService	chorbiService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("chirp/list");

		result.addObject("sended", this.chorbiService.findByPrincipal().getSended());
		result.addObject("received", this.chorbiService.findByPrincipal().getReceived());

		result.addObject("requestUri", "chirp/chorbi/list.do");

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send() {
		ModelAndView result;
		final ChirpForm chirpForm = new ChirpForm();

		Assert.notNull(chirpForm);

		result = this.createSendModelAndView(chirpForm);

		result.addObject("chirpForm", chirpForm);
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Collection<Chorbi> chorbies = this.chorbiService.findAll();
		chorbies.remove(chorbi);
		result.addObject("chorbies", chorbies);
		result.addObject("requestURI", "chirp/chorbi/send.do");
		result.addObject("forward", false);
		result.addObject("reply", false);
		return result;
	}

	// Send --------------------------------------------------------
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView send(final ChirpForm chirpForm, final BindingResult binding) {
		ModelAndView result;

		final Chirp chirp = this.chirpService.reconstruct(chirpForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(chirpForm);
		else
			try {
				this.chirpService.sendChirp(chirp);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirpForm, "chirp.commit.error");

				final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
				final Collection<Chorbi> chorbies = this.chorbiService.findAll();
				chorbies.remove(chorbi);

				result.addObject("chorbies", chorbies);
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final ChirpForm chirpForm = new ChirpForm();

		Assert.notNull(chirpForm);

		result = this.createEditModelAndView(chirpForm);
		result.addObject("chirpForm", chirpForm);
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Collection<Chorbi> chorbies = this.chorbiService.findAll();
		chorbies.remove(chorbi);
		result.addObject("users", chorbies);
		result.addObject("requestURI", "chirp/chorbi/edit.do");
		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final String senderName) {
		ModelAndView result;
		final ChirpForm chirpForm = new ChirpForm();

		Assert.notNull(chirpForm);
		final Chorbi chorbi = this.chorbiService.findChorbiByUsername(senderName);
		result = this.replyModelAndView(chirpForm);
		result.addObject("chirpForm", chirpForm);
		chirpForm.setReceiver(chorbi);
		final Collection<Chorbi> chorbis = new ArrayList<Chorbi>();
		chorbis.add(chorbi);

		result.addObject("requestURI", "chirp/chorbi/reply.do");

		result.addObject("chorbi", chorbis);
		result.addObject("nameChorbi", chorbi.getUserAccount().getUsername());

		result.addObject("forward", false);
		result.addObject("reply", true);
		return result;
	}

	protected ModelAndView replyModelAndView(final ChirpForm chirpForm) {
		ModelAndView result;

		result = this.replyModelAndView(chirpForm, null);
		result.addObject("reply", true);

		result.addObject("forward", false);
		result.addObject("chorbi", chirpForm.getReceiver());
		return result;
	}

	protected ModelAndView replyModelAndView(final ChirpForm chirpForm, final String chirp) {
		ModelAndView result;

		result = new ModelAndView("chirp/reply");
		result.addObject("reply", true);

		result.addObject("forward", false);
		result.addObject("chorbi", chirpForm.getReceiver());
		result.addObject("chirp", chirp);

		return result;

	}

	protected ModelAndView createEditModelAndView(final ChirpForm chirpForm) {
		ModelAndView result;

		result = this.createEditModelAndView(chirpForm, null);

		final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
		final Collection<Chorbi> chorbis = this.chorbiService.findAll();
		chorbis.remove(chorbi);
		result.addObject("reply", false);

		result.addObject("forward", false);
		result.addObject("users", chorbis);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ChirpForm chirpForm, final String chirp) {
		ModelAndView result;

		result = new ModelAndView("chirp/edit");
		result.addObject("chirpForm", chirpForm);

		final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
		final Collection<Chorbi> chorbis = this.chorbiService.findAll();
		chorbis.remove(chorbi);
		result.addObject("users", chorbis);
		result.addObject("chirp", chirp);

		result.addObject("forward", false);
		result.addObject("reply", false);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChirpForm chirpForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(chirpForm);
		else

			try {
				final Chirp chirp = this.chirpService.reconstruct(chirpForm, binding);
				this.chirpService.forwardChirp(chirp);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirpForm, "comment.commit.error");

				final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
				final Collection<Chorbi> chorbis = this.chorbiService.findAll();
				chorbis.remove(chorbi);

				result.addObject("users", chorbis);
			}
		return result;
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST, params = "save")
	public ModelAndView reply(@Valid final ChirpForm chirpForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.replyModelAndView(chirpForm);
		else

			try {
				final Chirp chirp = this.chirpService.reconstruct(chirpForm, binding);
				this.chirpService.replyChirp(chirp);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.replyModelAndView(chirpForm, "comment.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int chirpId) {
		ModelAndView result;

		try {
			this.chirpService.deleteChirp(chirpId);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	// Forward
	@RequestMapping(value = "/forward", method = RequestMethod.GET)
	public ModelAndView forward(@RequestParam final int chirpId) {
		ModelAndView result;
		final ChirpForm chirpForm = new ChirpForm();

		Assert.notNull(chirpForm);
		final Chirp chirp = this.chirpService.findOne(chirpId);
		chirpForm.setSubject(chirp.getSubject());
		chirpForm.setText(chirp.getText());
		chirpForm.setAttachments(chirp.getAttachments());
		result = this.forwardModelAndView(chirpForm);

		result.addObject("chirpForm", chirpForm);
		final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
		final Collection<Chorbi> chorbis = this.chorbiService.findAll();
		chorbis.remove(chorbi);
		result.addObject("users", chorbis);

		result.addObject("requestURI", "chirp/chorbi/edit.do");
		return result;
	}

	protected ModelAndView forwardModelAndView(final ChirpForm chirpForm) {
		ModelAndView result;

		result = this.forwardModelAndView(chirpForm, null);

		final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
		final Collection<Chorbi> chorbis = this.chorbiService.findAll();
		chorbis.remove(chorbi);
		result.addObject("reply", false);
		result.addObject("forward", true);
		result.addObject("users", chorbis);
		return result;
	}

	protected ModelAndView forwardModelAndView(final ChirpForm chirpForm, final String chirp) {
		ModelAndView result;

		result = new ModelAndView("chirp/forward");
		result.addObject("chirpForm", chirpForm);

		final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
		final Collection<Chorbi> chorbis = this.chorbiService.findAll();
		chorbis.remove(chorbi);
		result.addObject("reply", false);

		result.addObject("forward", true);
		result.addObject("users", chorbis);
		result.addObject("chirp", chirp);

		return result;

	}

	protected ModelAndView createSendModelAndView(final ChirpForm chirpForm) {
		ModelAndView result;

		result = this.forwardModelAndView(chirpForm, null);

		final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
		final Collection<Chorbi> chorbies = this.chorbiService.findAll();
		chorbies.remove(chorbi);

		result.addObject("chorbies", chorbies);
		result.addObject("forward", false);
		result.addObject("reply", false);

		return result;
	}

	protected ModelAndView createSendModelAndView(final ChirpForm chirpForm, final String chirp) {
		final ModelAndView result = new ModelAndView("chirp/send");
		result.addObject("chirpForm", chirpForm);

		final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
		final Collection<Chorbi> chorbies = this.chorbiService.findAll();
		chorbies.remove(chorbi);

		result.addObject("chorbies", chorbies);
		result.addObject("chirp", chirp);
		result.addObject("forward", false);
		result.addObject("reply", false);

		return result;
	}

}

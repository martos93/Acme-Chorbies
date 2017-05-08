
package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Chorbi;
import domain.Love;
import forms.LoveForm;
import services.ActorService;
import services.ChorbiService;
import services.LoveService;

@Component
@RequestMapping("like/chorbi")
public class LoveChorbiController extends AbstractController {

	//Services-------------------------------------------------
	@Autowired
	private LoveService		loveService;

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private ActorService	actorService;


	//Constructor----------------------------------------------

	public LoveChorbiController() {
		super();
	}

	//Methods--------------------------------------------------
	@RequestMapping("/listLikes")
	public ModelAndView listLikes() {
		final ModelAndView modelAndView;

		final Chorbi logged = this.chorbiService.getLoggedChorbi();
		final Collection<Love> loves = this.loveService.findAllLove();
		final boolean canShow = this.actorService.checkCreditCard(logged.getCreditCard());

		modelAndView = new ModelAndView("love/list");
		modelAndView.addObject("loves", loves);
		modelAndView.addObject("actor", logged);
		modelAndView.addObject("canShow", canShow);
		modelAndView.addObject("requestURI", "/listLikes.do");

		return modelAndView;
	}

	@RequestMapping("/listLikedBy")
	public ModelAndView listLikedBy() {
		final ModelAndView modelAndView;

		final Collection<Love> loves = this.loveService.findAllLoveBy();
		final Chorbi logged = this.chorbiService.getLoggedChorbi();
		final boolean canShow = this.actorService.checkCreditCard(logged.getCreditCard());

		modelAndView = new ModelAndView("love/list");
		modelAndView.addObject("loves", loves);
		modelAndView.addObject("actor", logged);
		modelAndView.addObject("canShow", canShow);
		modelAndView.addObject("requestURI", "/listLikedBy.do");

		return modelAndView;
	}

	public ModelAndView createEditModelAndView(final LoveForm loveForm) {
		ModelAndView modelAndView;
		modelAndView = this.createEditModelAndView(loveForm, null);
		return modelAndView;
	}

	public ModelAndView createEditModelAndView(final LoveForm loveForm, final String string) {
		ModelAndView modelAndView;

		modelAndView = new ModelAndView("love/edit");
		modelAndView.addObject("requestURI", "like/chorbi/create.do?id=" + this.chorbiService.findChorbiByUsername(loveForm.getLoved()).getId());
		modelAndView.addObject("loveForm", loveForm);
		modelAndView.addObject("message", string);
		return modelAndView;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int id) {
		ModelAndView modelAndView;

		final LoveForm loveForm = new LoveForm();
		loveForm.setLoved(this.chorbiService.findOne(id).getUserAccount().getUsername());

		Assert.notNull(loveForm);
		modelAndView = this.createEditModelAndView(loveForm);
		return modelAndView;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/like/chorbi/list.do");

		try {
			final Love love = this.loveService.findOne(id);
			final Chorbi chorbi = this.chorbiService.findByPrincipal();

			if (chorbi.getLovedBy().contains(love)) {
				this.loveService.removeLove(id);
				modelAndView = new ModelAndView("redirect:/like/chorbi/listLikedBy.do");
			}
			if (chorbi.getLove().contains(love)) {
				this.loveService.removeLove(id);
				modelAndView = new ModelAndView("redirect:/like/chorbi/listLikes.do");
			}
		} catch (final Throwable oops) {
			modelAndView = new ModelAndView("redirect:/chorbi/list.do");
		}
		return modelAndView;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final LoveForm loveForm, final BindingResult bindingResult) {
		ModelAndView modelAndView;
		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(loveForm);
		else
			try {
				final Love love = this.loveService.reconstruct(loveForm, bindingResult);
				final Chorbi principal = this.chorbiService.findByPrincipal();
				boolean repeat = false;
				love.setLover(principal);
				if (!love.getLoved().equals(love.getLover())) {
					for (final Love l : principal.getLove())
						if (l.getLoved().equals(love.getLoved())) {
							repeat = true;
							break;
						}
					if (!repeat) {
						this.loveService.addLove(love);
						modelAndView = new ModelAndView("redirect:/like/chorbi/listLikes.do");

					} else
						modelAndView = this.createEditModelAndView(loveForm, "love.commit.error");
				} else
					modelAndView = this.createEditModelAndView(loveForm, "love.commit.error");

			} catch (final Throwable oops) {
				modelAndView = this.createEditModelAndView(loveForm, "love.commit.error");
			}
		return modelAndView;

	}

}

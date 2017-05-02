
package controllers.chorbi;

import java.util.ArrayList;
import java.util.Collection;

import forms.LoveForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChorbiService;
import services.LoveService;
import controllers.AbstractController;
import domain.Chorbi;
import domain.Love;

@Component
@RequestMapping("like/chorbi")
public class LoveChorbiController extends AbstractController {

	//Services-------------------------------------------------
	@Autowired
	private LoveService		loveService;

	@Autowired
	private ChorbiService	chorbiService;
	
	@Autowired
	private ActorService 	actorService;


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
		boolean canShow=actorService.checkCreditCard(logged.getCreditCard());

		modelAndView = new ModelAndView("love/list");
		modelAndView.addObject("loves", loves);
		modelAndView.addObject("actor", logged);
		modelAndView.addObject("canShow",canShow);
		modelAndView.addObject("requestURI", "/listLikes.do");

		return modelAndView;
	}

	@RequestMapping("/listLikedBy")
	public ModelAndView listLikedBy() {
		final ModelAndView modelAndView;

		final Collection<Love> loves = this.loveService.findAllLoveBy();

		modelAndView = new ModelAndView("love/list");
		modelAndView.addObject("loves", loves);
		modelAndView.addObject("actor", this.chorbiService.findByPrincipal());
		modelAndView.addObject("canShow",true);
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
		modelAndView.addObject("requestURI", "like/chorbi/create.do?id="+this.chorbiService.findChorbiByUsername(loveForm.getLoved()).getId());
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
		ModelAndView modelAndView;

		try {
			this.loveService.removeLove(id);
			modelAndView = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			modelAndView = new ModelAndView("redirect:/chorbi/list.do");
		}
		return modelAndView;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(LoveForm loveForm, final BindingResult bindingResult) {
		ModelAndView modelAndView;
		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(loveForm);
		else
			try {
				Love love = this.loveService.reconstruct(loveForm, bindingResult);
				Chorbi principal = this.chorbiService.findByPrincipal();
				boolean repeat = false;
				love.setLover(principal);
				if (!love.getLoved().equals(love.getLover())) {
					for(Love l: principal.getLove()){
						if(l.getLoved().equals(love.getLoved())){
							repeat = true;
							break;
						}
					}
					if(!repeat){
						this.loveService.addLove(love);
						modelAndView = new ModelAndView("redirect:/welcome/index.do");

					}else{
						modelAndView = this.createEditModelAndView(loveForm, "love.commit.error");
					}
				}
				else
					modelAndView = this.createEditModelAndView(loveForm, "love.commit.error");

			} catch (final Throwable oops) {
				modelAndView = this.createEditModelAndView(loveForm, "love.commit.error");
			}
		return modelAndView;

	}

}

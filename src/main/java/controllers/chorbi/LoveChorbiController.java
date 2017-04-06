
package controllers.chorbi;

import java.util.Collection;

import javax.validation.Valid;

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


	//Constructor----------------------------------------------

	public LoveChorbiController() {
		super();
	}

	//Methods--------------------------------------------------
	@RequestMapping("/listLikes")
	public ModelAndView listLikes() {
		final ModelAndView modelAndView;

		final Collection<Love> loves = this.loveService.findAllLove();

		modelAndView = new ModelAndView("love/list");
		modelAndView.addObject("loves", loves);
		modelAndView.addObject("actor", this.chorbiService.findByPrincipal());
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
		modelAndView.addObject("requestURI", "/listLikedBy.do");

		return modelAndView;
	}

	public ModelAndView createEditModelAndView(final Love love) {
		ModelAndView modelAndView;
		modelAndView = this.createEditModelAndView(love, null);
		return modelAndView;
	}

	public ModelAndView createEditModelAndView(final Love love, final String string) {
		ModelAndView modelAndView;

		modelAndView = new ModelAndView("love/edit");
		modelAndView.addObject("requestURI", "like/chorbi/create.do");
		modelAndView.addObject("love", love);
		modelAndView.addObject("message", string);
		return modelAndView;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int id) {
		ModelAndView modelAndView;
		final Love love = this.loveService.create();
		final Chorbi loved = this.chorbiService.findOne(id);
		love.setLoved(loved);

		Assert.notNull(love);
		modelAndView = this.createEditModelAndView(love);

		return modelAndView;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView modelAndView;

		try {
			this.loveService.removeLove(id);
			modelAndView = new ModelAndView("redirect:welcome/index.do");
		} catch (final Throwable oops) {
			modelAndView = this.createEditModelAndView(this.loveService.findOne(id), "message.commit.error");
		}
		return modelAndView;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Love love, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		try {
			this.loveService.addLove(love);
			modelAndView = new ModelAndView("redirect:listLikes.do");
		} catch (final Throwable oops) {
			modelAndView = this.createEditModelAndView(love, "love.commit.error");
		}
		return modelAndView;

	}

}


package controllers.chorbi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import controllers.AbstractController;
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

	//	public ModelAndView listLove() {
	//		final ModelAndView modelAndView;
	//
	//		final Collection<Love> loves = this.loveService.findAllLove();
	//
	//		modelAndView = new ModelAndView("love/list");
	//		modelAndView.addObject("loves", loves);
	//		modelAndView.addObject("requestURI", "/listLike.do");
	//
	//		return modelAndView;
	//	}

	//	public ModelAndView listLovedBy() {
	//		final ModelAndView modelAndView;
	//
	//		final Collection<Love> loves = this.loveService.findAllLoveBy();
	//
	//		modelAndView = new ModelAndView("love/list");
	//		modelAndView.addObject("loves", loves);
	//		modelAndView.addObject("requestURI", "/listLikedBy.do");
	//
	//		return modelAndView;
	//	}

}

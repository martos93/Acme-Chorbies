
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import domain.Chorbi;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	@Autowired
	private ChorbiService	chorbiService;


	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView();

		final Collection<Chorbi> all = this.chorbiService.findAll();

		res.addObject("chorbi", all);
		res.addObject("requestUri", "/chorbi/list.do");

		return res;

	}

	@RequestMapping("/listByLikes")
	public ModelAndView listByLikes(@RequestParam final int chorbi) {
		final ModelAndView res = new ModelAndView();

		final Collection<Chorbi> all = this.chorbiService.getLikersByChorbiId(chorbi);

		res.addObject("chorbi", all);
		res.addObject("requestUri", "/chorbi/listByLikes.do");

		return res;

	}

}

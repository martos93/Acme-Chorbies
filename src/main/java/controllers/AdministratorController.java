/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ChorbiService;
import domain.Chorbi;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping("/listChorbiNotBanned")
	public ModelAndView listChorbiNotBanned() {
		ModelAndView res;

		res = new ModelAndView("chorbi/listChorbiNotBanned");
		final Collection<Chorbi> all = this.chorbiService.findAllNotBanned();
		res.addObject("chorbi", all);
		res.addObject("requestUri", "administrator/listChorbiBanned.do");
		res.addObject("ban", true);
		return res;
	}

	@RequestMapping("/listChorbiBanned")
	public ModelAndView listChorbiBanned() {
		ModelAndView res;

		res = new ModelAndView("chorbi/listChorbiBanned");
		final Collection<Chorbi> all = this.chorbiService.findAllBanned();
		res.addObject("chorbi", all);
		res.addObject("requestUri", "administrator/listChorbiBanned.do");
		res.addObject("unban", true);

		return res;
	}

	@RequestMapping("/banChorbi")
	public ModelAndView banChorbi(@RequestParam final int chorbi) {
		final ModelAndView result;
		result = new ModelAndView("chorbi/listChorbiBanned");

		try {
			final Chorbi c = this.chorbiService.findOne(chorbi);
			this.administratorService.banChorbi(c);
			result.addObject("message", "master.page.bannSuccess");
		} catch (final Exception oops) {
			result.addObject("message", "master.page.bannUnSuccess");
		}

		final Collection<Chorbi> all = this.chorbiService.findAllBanned();
		result.addObject("chorbi", all);

		return result;
	}

	@RequestMapping("/unBanChorbi")
	public ModelAndView unBanChorbi(@RequestParam final int chorbi) {
		final ModelAndView result;
		result = new ModelAndView("chorbi/listChorbiNotBanned");

		try {
			final Chorbi c = this.chorbiService.findOne(chorbi);
			this.administratorService.unBanChorbi(c);
			result.addObject("message", "master.page.unBannSuccess");
		} catch (final Exception oops) {
			result.addObject("message", "master.page.unBannUnSuccess");
		}

		final Collection<Chorbi> all = this.chorbiService.findAllNotBanned();
		result.addObject("chorbi", all);

		return result;
	}
}

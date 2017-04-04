/*
 * WelcomeController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private BannerService bannerService;

	// Constructors -----------------------------------------------------------


	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		//		final ArrayList<Banner> banners = (ArrayList<Banner>) this.bannerService.findAll();
		//		final Banner banner = banners.get(0);
		//		final int valorEntero = (int) Math.floor(Math.random() * ((banner.getBanners().size()) - 1 + 1) + 1);
		//		final ArrayList<String> bans = (ArrayList<String>) banner.getBanners();
		//		final String url = bans.get(valorEntero);

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		//		result.addObject("url", url);

		return result;
	}
}

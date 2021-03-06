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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Banner;
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
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name, @CookieValue(value = "JSESSIONID", defaultValue = "default") final String sessionCookie,
		@CookieValue(value = "language", defaultValue = "default") final String languageCookie) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		Banner banner = new Banner();
		final Collection<Banner> banners = this.bannerService.findAll();

		for (final Banner b : banners)
			banner = b;

		final int valorEntero = (int) Math.floor(Math.random() * (banner.getBanners().size()));
		final Collection<String> bans = banner.getBanners();
		final ArrayList<String> finalbanners = new ArrayList<String>();
		for (final String s : bans)
			finalbanners.add(s);
		final String url = finalbanners.get(valorEntero);

		//Mira si ya existe cookie
		boolean existCookie = false;
		if (!sessionCookie.equals("default") || !languageCookie.equals("default"))
			existCookie = true;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("url", url);
		result.addObject("existCookie", existCookie);
		return result;
	}

	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
	public ModelAndView privacy() {
		final ModelAndView res = new ModelAndView("privacy/privacy");
		return res;
	}

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public ModelAndView terms() {
		final ModelAndView res = new ModelAndView("terms/terms");
		return res;
	}
}

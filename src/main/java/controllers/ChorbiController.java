
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Chorbi;
import forms.ChorbiForm;
import services.ChorbiService;

@Controller
@RequestMapping("/chorbi")
public class ChorbiController extends AbstractController {

	@Autowired
	private ChorbiService chorbiService;


	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView();

		final Collection<Chorbi> all = this.chorbiService.listAll();
		try {
			final Chorbi logged = this.chorbiService.getLoggedChorbi();
			final Collection<Chorbi> c = this.chorbiService.findAllLovedByChorbiId(logged.getId());

			res.addObject("chorbi", all);
			res.addObject("logged", logged);
			res.addObject("chorbiesLoved", c);
			res.addObject("requestUri", "chorbi/list.do");

		} catch (final Exception e) {
			res.addObject("chorbi", all);
			res.addObject("requestUri", "/chorbi/listByLikes.do");
		}

		return res;

	}

	@RequestMapping("/listByLikes")
	public ModelAndView listByLikes(@RequestParam final int chorbi) {
		final ModelAndView res = new ModelAndView();

		final Collection<Chorbi> all = this.chorbiService.getLikersByChorbiId(chorbi);
		try {
			final Chorbi logged = this.chorbiService.getLoggedChorbi();
			final Collection<Chorbi> c = this.chorbiService.findAllLovedByChorbiId(logged.getId());

			res.addObject("chorbi", all);
			res.addObject("logged", logged);
			res.addObject("chorbiesLoved", c);
			res.addObject("requestUri", "/chorbi/listByLikes.do");

		} catch (final Exception e) {
			res.addObject("chorbi", all);
			res.addObject("requestUri", "/chorbi/listByLikes.do");
		}
		return res;

	}

	//Edition
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		final ModelAndView res = new ModelAndView("chorbi/register");

		final ChorbiForm c = new ChorbiForm();

		res.addObject("requestUri", "chorbi/register.do");
		res.addObject("chorbiForm", c);
		res.addObject("edit", false);
		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView res = new ModelAndView("chorbi/edit");
		ChorbiForm c = new ChorbiForm();
		final Chorbi chorbi = this.chorbiService.getLoggedChorbi();

		c = this.chorbiService.reconstructForm(chorbi);

		res.addObject("requestUri", "chorbi/edit.do");
		res.addObject("chorbiForm", c);
		res.addObject("edit", true);
		return res;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final ChorbiForm chorbiForm, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("chorbi/register");

		if (chorbiForm.getPassword().equals(chorbiForm.getConfirmPassword())) {
			final Chorbi chorbi = this.chorbiService.reconstruct(chorbiForm, bindingResult);
			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.getAllErrors());
				res.addObject("requestUri", "chorbi/register.do");
				res.addObject("edit", false);
				res.addObject("chorbiForm", chorbiForm);
			} else if (chorbiForm.isAcceptTerms() != true) {
				res.addObject("chorbiForm", chorbiForm);
				res.addObject("edit", false);
				res.addObject("requestUri", "chorbi/register.do");
				res.addObject("message", "chorbi.acceptTerms.error");
			} else
				try {
					Assert.isTrue(this.chorbiService.edad(chorbi) >= 18);
					this.chorbiService.register(chorbi);
					res = new ModelAndView("redirect:../welcome/index.do");

				} catch (final DataIntegrityViolationException oops) {

					res = new ModelAndView("chorbi/register");
					res.addObject("chorbiForm", chorbiForm);
					res.addObject("edit", false);
					res.addObject("message", "chorbi.error.exists");

				} catch (final Throwable e) {

					System.out.println(e.getMessage());
					res.addObject("edit", false);
					res.addObject("message", "chorbi.age.error");

				}

		} else {
			res.addObject("chorbiForm", chorbiForm);
			res.addObject("edit", false);
			res.addObject("message", "chorbi.password.error");
		}
		return res;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final ChorbiForm chorbiForm, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("chorbi/edit");
		Chorbi chorbi = this.chorbiService.findOne(chorbiForm.getChorbiId());
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (chorbiForm.getPassword().isEmpty() == false) {
			System.out.println(chorbiForm.getChorbiId());
			Assert.isTrue(encoder.encodePassword(chorbiForm.getPassword(), null).equals(chorbi.getUserAccount().getPassword()));

			try {
				if (chorbiForm.getNewpassword().length() > 0 && chorbiForm.getRepeatnewpassword().length() > 0 || chorbiForm.getNewpassword().length() > 0 || chorbiForm.getRepeatnewpassword().length() > 0)
					Assert.isTrue(chorbiForm.getNewpassword().equals(chorbiForm.getRepeatnewpassword()));
				chorbi = this.chorbiService.reconstructEdit(chorbiForm, chorbi);

				if (bindingResult.hasErrors()) {
					System.out.println(bindingResult.getAllErrors());
					res.addObject("requestUri", "chorbi/edit.do");
					res.addObject("chorbiForm", chorbiForm);
					res.addObject("edit", true);
				} else {
					Assert.isTrue(this.chorbiService.edad(chorbi) >= 18);

					this.chorbiService.modify(chorbi);

					res = new ModelAndView("redirect:../welcome/index.do");
				}
			} catch (final DataIntegrityViolationException oops) {

				res = new ModelAndView("chorbi/edit");
				res.addObject("chorbiForm", chorbiForm);
				res.addObject("edit", true);
				res.addObject("message", "chorbi.error.exists");

			} catch (final Throwable e) {

				System.out.println(e.getMessage());
				res.addObject("edit", true);
				res.addObject("message", "chorbi.password.new");

			}
		} else {
			res.addObject("chorbiForm", chorbiForm);
			res.addObject("edit", true);
			res.addObject("message", "chorbi.password.error");
		}
		return res;

	}

}

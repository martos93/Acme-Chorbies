package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Manager;
import forms.ManagerForm;
import services.ManagerService;

@Controller
@RequestMapping("/manager_/administrator")
public class ManagerAdministratorController extends AbstractController{
	

	@Autowired
	private ManagerService managerService;
	
	//Edition
		@RequestMapping(value = "/register", method = RequestMethod.GET)
		public ModelAndView register() {
			final ModelAndView res = new ModelAndView("manager/register");

			final ManagerForm c = new ManagerForm();

			res.addObject("requestUri", "manager_/administrator/register.do");
			res.addObject("managerForm", c);
			res.addObject("edit", false);
			return res;

		}

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit() {
			final ModelAndView res = new ModelAndView("manager/edit");
			ManagerForm c = new ManagerForm();
			final Manager manager = this.managerService.findByPrincipal();

			c = this.managerService.reconstructForm(manager);

			res.addObject("requestUri", "manager_/administrator/edit.do");
			res.addObject("managerForm", c);
			res.addObject("edit", true);
			return res;

		}

		@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
		public ModelAndView register(@Valid final ManagerForm managerForm, final BindingResult bindingResult) {
			ModelAndView res = new ModelAndView("manager/register");

			if (managerForm.getPassword().equals(managerForm.getConfirmPassword())) {

				final Manager manager = this.managerService.reconstruct(managerForm);

				if (bindingResult.hasErrors()) {
					System.out.println(bindingResult.getAllErrors());
					res.addObject("requestUri", "manager_/administrator/register.do");
					res.addObject("edit", false);
					res.addObject("managerForm", managerForm);
				} else if (managerForm.isAcceptTerms() != true) {
					res.addObject("managerForm", managerForm);
					res.addObject("edit", false);
					res.addObject("requestUri", "manager_/administrator/register.do");
					res.addObject("message", "manager.acceptTerms.error");
				} else
					try {
						this.managerService.register(manager);
						res = new ModelAndView("redirect:/welcome/index.do");

					} catch (final DataIntegrityViolationException oops) {

						res = new ModelAndView("manager/register");
						res.addObject("managerForm", managerForm);
						res.addObject("edit", false);
						res.addObject("message", "manager.error.exists");

					} catch (final Throwable e) {

						System.out.println(e.getMessage());
						res.addObject("edit", false);
						res.addObject("message", "manager.commit.error");

					}

			} else {
				res.addObject("managerForm", managerForm);
				res.addObject("edit", false);
				res.addObject("message", "manager.password.error");
			}
			return res;

		}
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView edit(@Valid final ManagerForm managerForm, final BindingResult bindingResult) {
			ModelAndView res = new ModelAndView("manager/edit");
			Manager manager = this.managerService.findOne(managerForm.getManagerId());
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			if (managerForm.getPassword().isEmpty() == false) {
				System.out.println(managerForm.getManagerId());
				Assert.isTrue(encoder.encodePassword(managerForm.getPassword(), null).equals(manager.getUserAccount().getPassword()));

				try {
					if (managerForm.getNewpassword().length() > 0 && managerForm.getRepeatnewpassword().length() > 0 || managerForm.getNewpassword().length() > 0 || managerForm.getRepeatnewpassword().length() > 0)
						Assert.isTrue(managerForm.getNewpassword().equals(managerForm.getRepeatnewpassword()));
					manager = this.managerService.reconstructEdit(managerForm, manager);

					if (bindingResult.hasErrors()) {
						System.out.println(bindingResult.getAllErrors());
						res.addObject("requestUri", "manager_/administrator/edit.do");
						res.addObject("managerForm", managerForm);
						res.addObject("edit", true);
					} else {
						this.managerService.modify(manager);
						res = new ModelAndView("redirect:/welcome/index.do");
					}
				} catch (final DataIntegrityViolationException oops) {

					res = new ModelAndView("manager/edit");
					res.addObject("managerForm", managerForm);
					res.addObject("edit", true);
					res.addObject("message", "manager.error.exists");

				} catch (final Throwable e) {

					System.out.println(e.getMessage());
					res.addObject("edit", true);
					res.addObject("message", "manager.password.new");

				}
			} else {
				res.addObject("managerForm", managerForm);
				res.addObject("edit", true);
				res.addObject("message", "manager.password.error");
			}
			return res;

		}

}

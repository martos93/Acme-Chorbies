
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Fee;
import services.FeeService;

@Controller
@RequestMapping("/fee/administrator")
public class FeeAdministratorController {

	//Services----------------------------------------------------------
	@Autowired
	private FeeService feeService;


	protected ModelAndView createEditModelAndView(final Fee fee) {
		ModelAndView result;
		result = this.createEditModelAndView(fee, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Fee fee, final String message) {
		ModelAndView result;

		result = new ModelAndView("fee/edit");
		result.addObject("fee", fee);
		result.addObject("message", message);
		result.addObject("requestURI", "fee/administrator/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Fee fee;
		fee = this.feeService.selectFee();

		Assert.notNull(fee);
		result = this.createEditModelAndView(fee);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Fee fee, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(fee);
		else
			try {
				fee = this.feeService.reconstruct(fee, binding);
				this.feeService.save(fee);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(fee, "fee.commit.error");
				System.out.println(oops.getMessage());
			}
		return result;
	}

}


package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Chorbi;
import services.ChorbiService;

@Controller
@RequestMapping("/chorbi/administrator")
public class ChorbiAdministratorController extends AbstractController {

	@Autowired
	private ChorbiService chorbiService;


	protected ModelAndView createEditModelAndView() {
		ModelAndView result;
		result = this.createEditModelAndView(null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/updateFees");
		result.addObject("message", message);
		result.addObject("requestURI", "chorbi/administrator/updateFees.do");

		return result;
	}

	@RequestMapping(value = "/updateFees", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		result = this.createEditModelAndView();

		return result;
	}

	@RequestMapping(value = "/updateFees", method = RequestMethod.POST, params = "save")
	public ModelAndView updateFees() {

		try {
			final Collection<Chorbi> all = this.chorbiService.findAll();
			for (final Chorbi chorbi : all) {
				System.out.println("Primero " + chorbi.getAmountDue());
				this.chorbiService.updateFee(chorbi);
				System.out.println("Luego " + chorbi.getAmountDue());
			}
			final ModelAndView result = new ModelAndView("redirect:/");
			return result;
		} catch (final Throwable oops) {
			final ModelAndView result = new ModelAndView("redirect:/");
			return result;
		}

	}

}

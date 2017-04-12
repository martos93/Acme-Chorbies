
package controllers.chorbi;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Chorbi;
import domain.Template;
import services.ChorbiService;
import services.TemplateService;

@Component
@RequestMapping("/template/chorbi")
public class TemplateChorbiController {

	//Service----------------------------------------------------------------
	@Autowired
	private TemplateService	templateService;

	@Autowired
	private ChorbiService	chorbiService;


	protected ModelAndView createEditModelAndView(final Template template) {
		ModelAndView result;

		result = this.createEditModelAndView(template, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Template template, final String message) {
		ModelAndView result;

		result = new ModelAndView("template/search");
		result.addObject("template", template);
		result.addObject("message", message);
		result.addObject("requestURI", "template/chorbi/search.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		final Template template = chorbi.getTemplate();

		Assert.notNull(template);

		result = this.createEditModelAndView(template);

		return result;

	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView save(final Template template, final BindingResult binding) {

		ModelAndView result;
		try {
			//final Template t = this.templateService.reconstruct(template, binding);
			final Template lastSearch = this.chorbiService.getLoggedChorbi().getTemplate();

			if (binding.hasErrors()) {
				result = this.createEditModelAndView(template);
				System.out.println(binding.getAllErrors());
			} else if (this.templateService.sameTemplate(template, lastSearch) == true && this.templateService.isCached(template) == true) {

				Assert.isTrue(this.chorbiService.hasValidCreditCard(this.chorbiService.getLoggedChorbi()));
				final Collection<Chorbi> res = template.getResults();
				System.out.println("devolvemos guardado");
				result = new ModelAndView("chorbi/list");
				result.addObject("chorbi", res);
				result.addObject("requestURI", "chorbi/list.do");
			} else {
				Assert.isTrue(this.chorbiService.hasValidCreditCard(this.chorbiService.getLoggedChorbi()));
				final Collection<Chorbi> res = this.chorbiService.getChorbiesByTemplate(template);
				System.out.println("devolvemos nuevo");
				Template temp = this.templateService.findOne(template.getId());
				temp.setResults(res);
				temp.setMoment(new Date(System.currentTimeMillis() - 10000));
				temp.setAproxAge(template.getAproxAge());
				temp.setGenre(template.getGenre());
				temp.setKeyword(template.getKeyword());
				temp.setKindRelationship(template.getKindRelationship());
				temp.setLocation(template.getLocation());
				temp = this.templateService.save(temp);

				result = new ModelAndView("chorbi/list");
				result.addObject("chorbi", res);
				result.addObject("requestUri", "chorbi/list.do");
			}
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			result = this.createEditModelAndView(template, "template.commit.error");

		}
		return result;
	}

}


package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Cache;
import services.CacheService;

@Controller
@RequestMapping("/cache/administrator")
public class CacheAdministratorController {

	//Services----------------------------------------------------------
	@Autowired
	private CacheService cacheService;


	protected ModelAndView createEditModelAndView(final Cache cache) {
		ModelAndView result;

		result = this.createEditModelAndView(cache, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Cache cache, final String message) {
		ModelAndView result;

		result = new ModelAndView("cache/edit");
		result.addObject("cache", cache);
		result.addObject("message", message);
		result.addObject("requestURI", "cache/administrator/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Cache cache;
		cache = this.cacheService.selectCache();

		Assert.notNull(cache);
		result = this.createEditModelAndView(cache);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Cache cache, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(cache);
		else
			try {
				cache = this.cacheService.reconstruct(cache, binding);
				this.cacheService.save(cache);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(cache, "cache.commit.error");
				System.out.println(oops.getMessage());
			}
		return result;
	}

}

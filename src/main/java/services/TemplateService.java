
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Cache;
import domain.Chorbi;
import domain.Template;
import repositories.TemplateRepository;

@Service
@Transactional
public class TemplateService {

	@Autowired
	private Validator			validator;

	//Repository---------------------------------------------------------
	@Autowired
	private TemplateRepository	templateRepository;

	//Services-----------------------------------------------------------

	@Autowired
	private CacheService		cacheService;


	//Constructor--------------------------------------------------------

	public TemplateService() {
		super();
	}

	// Simple CRUD methods
	public Collection<Template> findAll() {
		return this.templateRepository.findAll();
	}
	public Template findOne(final int id) {
		final Template res = this.templateRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}
	public Template create(final Chorbi chorbi) {
		final Template template = new Template();

		template.setSearcher(chorbi);
		//Initially, every search field must be null, which means that every chorbi satisfies the corresponding search criteria;
		template.setAproxAge(null);
		template.setGenre(null);
		template.setKeyword(null);
		template.setKindRelationship(null);
		template.setLocation(null);
		template.setResults(new ArrayList<Chorbi>());
		template.setMoment(new Date(System.currentTimeMillis() - 100));

		return template;
	}

	public Template save(final Template template) {

		Assert.notNull(template);
		//		Assert.isTrue(this.chorbiService.findByPrincipal().getId() == (template.getSearcher().getId()));
		Template res;
		res = this.templateRepository.saveAndFlush(template);
		return res;
	}

	public Template create(final Template template) {

		Assert.notNull(template);
		Template res;
		res = this.templateRepository.save(template);
		return res;
	}

	// Other business methods

	public boolean sameTemplate(final Template template1, final Template template2) {
		boolean res = false;

		int age1;
		int age2;
		if (template1.getAproxAge() == null)
			age1 = 0;
		else
			age1 = 1;
		if (template2.getAproxAge() == null)
			age2 = 0;
		else
			age2 = 1;
		final boolean age = age1 == age2;
		final boolean genre = template1.getGenre().equals(template2.getGenre());
		final boolean keyword = template1.getKeyword().equals(template2.getKeyword());
		final boolean relation = template1.getKindRelationship().equals(template2.getKindRelationship());
		final boolean city = template1.getLocation().getCity().equals(template2.getLocation().getCity());
		final boolean country = template1.getLocation().getCountry().equals(template2.getLocation().getCountry());
		final boolean province = template1.getLocation().getProvince().equals(template2.getLocation().getProvince());
		final boolean state = template1.getLocation().getState().equals(template2.getLocation().getState());

		if (age && genre && keyword && relation && city && country && province && state)
			res = true;

		return res;
	}
	public Date timeToLive(final Template template) {

		final Cache cache = this.cacheService.selectCache();
		final int hours = Integer.parseInt(cache.getHours());
		final int minutes = Integer.parseInt(cache.getMinutes());
		final int seconds = Integer.parseInt(cache.getSeconds());
		final long hoursToMil = hours * 3600 * 1000;
		final long minutesToMil = minutes * 60 * 1000;
		final long secondsToMil = seconds * 1000;
		final long millisecondsToAdd = hoursToMil + minutesToMil + secondsToMil + 10000;
		final Date res = new Date(template.getMoment().getTime() + millisecondsToAdd);
		return res;
	}

	public boolean isCached(final Template template) {
		boolean res = false;
		if (this.timeToLive(template).getTime() > new Date(System.currentTimeMillis()).getTime())
			res = true;
		return res;
	}

	public boolean locationEmpty(final Template template) {
		boolean res = false;
		if (template.getLocation().getCity().length() == 0 && template.getLocation().getProvince().length() == 0 && template.getLocation().getCountry().length() == 0 && template.getLocation().getState().length() == 0)
			res = true;
		return res;
	}

	public Template reconstruct(final Template template, final BindingResult binding) {

		Template res;

		if (template.getId() == 0)
			res = template;
		else {

			res = this.findOne(template.getId());

			res.setLocation(template.getLocation());
			res.setAproxAge(template.getAproxAge());
			res.setGenre(template.getGenre());
			res.setKeyword(template.getKeyword());
			res.setKindRelationship(template.getKindRelationship());

			this.validator.validate(res, binding);
		}
		return res;
	}

}

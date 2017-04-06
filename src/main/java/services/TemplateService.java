
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
	private ChorbiService		chorbiService;


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
	public Template create() {
		final Template template = new Template();
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
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
		Template res;
		res = this.templateRepository.save(template);
		return res;
	}

	// Other business methods

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

package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import forms.LoveForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LoveRepository;
import domain.Chorbi;
import domain.Love;

@Service
@Transactional
public class LoveService {

	//Repository---------------------------------------------------------
	@Autowired
	private LoveRepository	loveRepository;

	//Services-----------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private Validator		validator;


	//Constructor--------------------------------------------------------

	public LoveService() {
		super();
	}

	//CRUD-Methods-------------------------------------------------------

	public Love create() {
		Love love;
		love = new Love();

		love.setMoment(new Date(System.currentTimeMillis() - 10000));
		love.setLover(this.chorbiService.findByPrincipal());
		love.setComment(new String());
		return love;

	}

	public Love findOne(final int id) {
		Love love;
		love = this.loveRepository.findOne(id);
		Assert.notNull(love);
		return love;
	}

	public Collection<Love> findAllLove() {
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		return chorbi.getLove();
	}

	public Collection<Love> findAllLoveBy() {
		final Chorbi chorbi = this.chorbiService.findByPrincipal();
		return chorbi.getLovedBy();
	}

	public Love save(final Love love) {
		Assert.notNull(love);
		Assert.isTrue(love.getLover().getUserAccount().equals(this.chorbiService.findByPrincipal().getUserAccount()));
		return this.loveRepository.save(love);
	}

	public void delete(final Love love) {
		Assert.isTrue(love.getLover().getUserAccount().equals(this.chorbiService.findByPrincipal().getUserAccount()));
		this.loveRepository.delete(love);

	}

	//Other-Methods------------------------------------------------------------------
	public void addLove(final Love love) {
		Chorbi loved;
		Chorbi lover;

		loved = love.getLoved();
		Assert.notNull(loved);
		lover = love.getLover();
		lover.getLove().add(love);
		loved.getLovedBy().add(love);

		love.setLoved(loved);
		this.loveRepository.save(love);

		this.chorbiService.save(lover);
		this.chorbiService.save(loved);
	}

	public void removeLove(final int loveId) {

		final Love love = this.findOne(loveId);
		Assert.isTrue(this.chorbiService.isAuthenticated());
		Assert.isTrue(this.chorbiService.findByPrincipal().equals(love.getLover()));

		this.loveRepository.delete(love);

	}

	public Love reconstruct(final LoveForm loveForm, final BindingResult binding) {
		Love res = create();
		res.setComment(loveForm.getComment());
		res.setStars(loveForm.getStars());
		res.setLoved(this.chorbiService.findChorbiByUsername(loveForm.getLoved()));

		this.validator.validate(res, binding);
		return res;
	}

}

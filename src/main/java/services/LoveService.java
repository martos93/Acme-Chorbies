
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.LoveRepository;

@Service
@Transactional
public class LoveService {

	//Repository---------------------------------------------------------
	@Autowired
	private LoveRepository	loveRepository;

	//Services-----------------------------------------------------------
	@Autowired
	private ChorbiService	chorbiService;


	//Constructor--------------------------------------------------------

	public LoveService() {
		super();
	}

	//CRUD-Methods-------------------------------------------------------

	//	public Love create() {
	//		Love love;
	//		love = new Love();
	//
	//		love.setLover(this.chorbiService.findByPrincipal());
	//		love.setMoment(new Date(System.currentTimeMillis() - 100));
	//		return love;
	//
	//	}

	//	public Love findOne(final int id) {
	//		Love love;
	//		love = this.loveRepository.findOne(id);
	//		Assert.notNull(love);
	//		return love;
	//	}

	//	public Collection<Love> findAllLove() {
	//		final Chorbi chorbi = this.chorbiService.findByPrincipal();
	//		return chorbi.getLove();
	//	}

	//	public Collection<Love> findAllLoveBy() {
	//		final Chorbi chorbi = this.chorbiService.findByPrincipal();
	//		return chorbi.getLovedBy();
	//	}

	//	public Love save(final Love love) {
	//		Assert.notNull(love);
	//		Assert.isTrue(love.getLover().getUserAccount().equals(this.chorbiService.getLoggedChorbi().getUserAccount()));
	//		return this.loveRepository.save(love);
	//	}

	//	public void delete(final Love love) {
	//		Assert.isTrue(love.getLover().getUserAccount().equals(this.chorbiService.getLoggedChorbi().getUserAccount()));
	//		this.loveRepository.delete(love);
	//
	//	}

	//Other-Methods------------------------------------------------------------------
	//	public void addLove(final int chorbiId) {
	//		Chorbi loved;
	//		Chorbi lover;
	//
	//		final Love love = this.create();
	//
	//		loved = this.chorbiService.findOne(chorbiId);
	//		Assert.notNull(loved);
	//		lover = love.getLover();
	//		lover.getLove().add(love);
	//		loved.getLovedBy().add(love);
	//
	//		love.setLoved(loved);
	//		this.loveRepository.save(love);
	//
	//		this.chorbiService.save(lover);
	//		this.chorbiService.save(loved);
	//	}

}

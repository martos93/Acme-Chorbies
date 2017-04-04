
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;
import domain.Love;

@Service
@Transactional
public class ChorbiService {

	@Autowired
	private ChorbiRepository	chorbiRepository;

	@Autowired
	private ActorService		actorService;


	public Chorbi create() {

		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.CHORBI);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);

		final Chorbi res = new Chorbi();
		res.setUserAccount(userAccount);
		res.setBanned(false);
		res.setSended(new ArrayList<Chirp>());
		res.setReceived(new ArrayList<Chirp>());
		res.setLove(new ArrayList<Love>());
		res.setLovedBy(new ArrayList<Love>());

		return res;
	}

	public Collection<Chorbi> findAll() {
		Collection<Chorbi> result;
		result = this.chorbiRepository.findAll();
		return result;
	}

	public Chorbi findOne(final int id) {
		Chorbi result;
		result = this.chorbiRepository.findOne(id);
		return result;
	}

	public Chorbi save(final Chorbi chorbi) {
		return this.chorbiRepository.saveAndFlush(chorbi);
	}

	/*
	 * public Chorbi getLoggedChorbi() {
	 * Chorbi result;
	 * UserAccount user;
	 * if (this.isAuthenticated() == true) {
	 * user = LoginService.getPrincipal();
	 * result = this.chorbiRepository.findChorbiByUsername(user.getUsername());
	 * } else
	 * result = null;
	 * return result;
	 * }
	 */

	public Boolean isAuthenticated() {
		Boolean res = true;
		try {
			LoginService.getPrincipal();
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	public Boolean isChorbi() {
		Boolean res = true;
		try {
			LoginService.getPrincipal().getAuthorities().contains(Authority.CHORBI);
		} catch (final Exception e) {
			res = false;
		}

		return res;
	}

	/*
	 * public Chorbi findByPrincipal() {
	 * final UserAccount userAccount = LoginService.getPrincipal();
	 * final Chorbi chorbi = this.chorbiRepository.findByPrincipal(userAccount.getId());
	 * 
	 * Assert.isTrue(chorbi.getUserAccount().equals(userAccount));
	 * return chorbi;
	 * }
	 */

	public Collection<Chorbi> getLikersByChorbiId(final int id) {

		final Collection<Chorbi> res = this.chorbiRepository.LikersByChorbyId(id);

		return res;
	}

}

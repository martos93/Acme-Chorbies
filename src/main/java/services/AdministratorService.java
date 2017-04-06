
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import domain.Administrator;
import domain.Chorbi;

@Service
@Transactional
public class AdministratorService {

	//Repository---------------------------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	//Services-----------------------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private ChorbiService			chorbiService;


	//Constructor--------------------------------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods

	public Administrator findOne(final int id) {
		Administrator result;
		result = this.administratorRepository.findOne(id);
		return result;
	}

	public void checkLoggedIsAdmin() {
		final Authority aut = new Authority();
		aut.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(aut));
	}

	public void banChorbi(final Chorbi c) {
		Assert.isTrue(this.actorService.isAuthenticated());
		this.checkLoggedIsAdmin();
		c.getUserAccount().setIsBanned(true);
		this.chorbiService.save(c);

	}

	public void unBanChorbi(final Chorbi c) {
		Assert.isTrue(this.actorService.isAuthenticated());
		this.checkLoggedIsAdmin();
		c.getUserAccount().setIsBanned(false);
		this.chorbiService.save(c);

	}

}

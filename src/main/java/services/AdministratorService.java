
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrator;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class AdministratorService {

	//Repository---------------------------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;


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

}

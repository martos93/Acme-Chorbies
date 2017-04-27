
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Manager;
import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ManagerService {

	//Repository------------------------------------------------------------
	@Autowired
	private ManagerRepository managerRepository;


	//CRUD Methods----------------------------------------------------------

	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		final Manager managerSaved = this.managerRepository.save(manager);
		return managerSaved;
	}

	//Other Methods---------------------------------------------------------

	public Manager findByPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerRepository.findByPrincipal(userAccount.getId());
		System.out.println();
		Assert.isTrue(manager.getUserAccount().equals(userAccount));
		return manager;
	}

	public void checkLoggedIsManager() {
		final Authority aut = new Authority();
		aut.setAuthority(Authority.MANAGER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(aut));
	}
}

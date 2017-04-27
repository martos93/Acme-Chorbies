
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ManagerRepository;
import security.LoginService;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerRepository	managerRepository;

	@Autowired
	private ActorService		actorService;


	public Manager getLoggedManager() {
		Manager res = null;
		if (this.actorService.isAuthenticated())
			res = this.managerRepository.managerByUsername(LoginService.getPrincipal().getUsername());

		return res;
	}
}

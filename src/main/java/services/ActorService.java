
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;

	@Autowired
	private Validator		validator;


	// Constructor
	public ActorService() {
		super();
	}

	// Simple CRUD methods

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		return result;
	}

	public Actor findOne(final int id) {
		Actor result;
		result = this.actorRepository.findOne(id);
		return result;
	}

	// Other business methods
	/*
	 * public Actor getLoggedActor() {
	 * Actor result;
	 * UserAccount user;
	 * if (this.isAuthenticated() == true) {
	 * user = LoginService.getPrincipal();
	 * result = this.actorRepository.findActorByUsername(user.getUsername());
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

	/*
	 * public Actor findActorByUsername(final String username) {
	 * return this.actorRepository.findActorByUsername(username);
	 * }
	 */

	public Actor save(final Actor a) {
		return this.actorRepository.save(a);
	}

	/*
	 * public Actor edit(final Actor actor) {
	 * Assert.notNull(actor);
	 * final Actor logged = this.getLoggedActor();
	 * Assert.isTrue(actor.equals(logged));
	 * return this.save(actor);
	 * }
	 */

	public void checkPrincipal(final Actor actor1, final Actor actor2) {
		Assert.isTrue(actor1.getId() == actor2.getId());
	}

	public void checkIsLogged() {
		final Collection<Authority> auts = LoginService.getPrincipal().getAuthorities();
		final Collection<Authority> auts2 = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final Authority b = new Authority();
		b.setAuthority(Authority.CHORBI);

		auts2.add(a);
		auts2.add(b);

		Assert.isTrue(!auts.contains(auts2));
	}

	public Actor reconstruct(final Actor actor, final BindingResult bindingResult) {

		Actor res;

		if (actor.getId() == 0)
			res = actor;
		else {

			res = this.findOne(actor.getId());

			res.setName(actor.getName());
			res.setSurname(actor.getSurname());
			res.setEmail(actor.getEmail());
			res.setPhoneNumber(actor.getPhoneNumber());

			this.validator.validate(res, bindingResult);

		}

		return res;

	}

}

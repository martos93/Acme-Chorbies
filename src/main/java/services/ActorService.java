
package services;

import java.util.ArrayList;
import java.util.Calendar;
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
import domain.CreditCard;

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
		final Authority c = new Authority();
		c.setAuthority(Authority.MANAGER);

		auts2.add(a);
		auts2.add(b);
		auts2.add(c);

		Assert.isTrue(!auts.contains(auts2));
	}

	public boolean checkCreditCard(final CreditCard c) {
		boolean res = true;
		this.luhnTest(c.getNumber());
		final String b = c.getBrandName();
		if (b.equals("VISA") || b.equals("MASTERCARD") || b.equals("DISCOVER") || b.equals("DINNER") || b.equals("AMEX")) {

			final Calendar now = Calendar.getInstance();
			final Calendar x = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, 1);
			x.set(c.getExpirationYear(), c.getExpirationMonth(), 1);
			if (x.compareTo(now) < 0)
				res = false;
			else
				res = this.luhnTest(c.getNumber());
		} else
			res = false;
		return res;
	}

	public boolean luhnTest(final String s) {
		int i1 = 0, i2 = 0;
		final String aux = new StringBuffer(s).reverse().toString();
		for (int i = 0; i < aux.length(); i++) {
			final int digit = Character.digit(aux.charAt(i), 10);
			if (i % 2 == 0)
				i1 += digit;
			else {
				i2 += 2 * digit;
				if (digit >= 5)
					i2 -= 9;
			}
		}
		return (i1 + i2) % 10 == 0;
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

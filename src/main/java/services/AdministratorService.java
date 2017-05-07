
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	
	public Boolean isAdministrator() {
		Boolean res = false;
		try {
			final Authority aut = new Authority();
			aut.setAuthority(Authority.ADMIN);

			res = LoginService.getPrincipal().getAuthorities().contains(aut);
		} catch (final Exception e) {
			res = false;
		}

		return res;
	}

	public void banChorbi(final Chorbi c) {
		Assert.isTrue(this.actorService.isAuthenticated());
		this.checkLoggedIsAdmin();

		c.getUserAccount().setEnabled(false);
		this.chorbiService.save(c);

	}

	public void unBanChorbi(final Chorbi c) {
		Assert.isTrue(this.actorService.isAuthenticated());
		this.checkLoggedIsAdmin();
		c.getUserAccount().setEnabled(true);
		this.chorbiService.save(c);

	}

	public Set<String> cities() {
		return this.administratorRepository.cities();
	}

	public Set<String> countries() {
		return this.administratorRepository.countries();
	}

	//Dashboard
	public List<String> chorbiesByCity() {
		this.checkLoggedIsAdmin();
		final Set<String> cities = this.cities();
		final List<String> result = new ArrayList<String>();

		for (final String c : cities)
			result.add(this.administratorRepository.chorbiesByCity(c)[0] + ": " + this.administratorRepository.chorbiesByCity(c)[1]);

		return result;
	}

	public List<String> chorbiesByCountry() {
		this.checkLoggedIsAdmin();
		final Set<String> countries = this.countries();
		final List<String> result = new ArrayList<String>();

		for (final String c : countries)
			result.add(this.administratorRepository.chorbiesByCountry(c)[0] + ": " + this.administratorRepository.chorbiesByCountry(c)[1]);
		return result;
	}

	public Object[] minMaxAvgAgesChorbies() {
		this.checkLoggedIsAdmin();
		return this.administratorRepository.minMaxAvgAgesChorbies();
	}

	public Double ratioChorbiesInvalidCreditCard() {
		this.checkLoggedIsAdmin();
		return this.administratorRepository.ratioChorbiesInvalidCreditCard();
	}

	public Double ratioChorbiesSearchActivites() {
		this.checkLoggedIsAdmin();
		return this.administratorRepository.ratioChorbiesSearchActivites();
	}

	public Double ratioChorbiesSearchFriendShip() {
		this.checkLoggedIsAdmin();
		return this.administratorRepository.ratioChorbiesSearchFriendShip();
	}

	public Double ratioChorbiesSearchLove() {
		this.checkLoggedIsAdmin();
		return this.administratorRepository.ratioChorbiesSearchLove();
	}

	public Object[] minMaxAvgLikesPerChorbi() {
		this.checkLoggedIsAdmin();
		return this.administratorRepository.minMaxAvgLikesPerChorbi();
	}

	public Object[] minMaxAvgChirpsRecieved() {
		this.checkLoggedIsAdmin();
		return this.administratorRepository.minMaxAvgChirpsRecieved();
	}

	public Object[] minMaxAvgChirpsSended() {
		this.checkLoggedIsAdmin();
		return this.administratorRepository.minMaxAvgChirpsSended();
	}

}

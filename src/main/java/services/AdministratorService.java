
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

import java.util.*;

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

    public Set<String> cities(){return administratorRepository.cities();}

    public Set<String> countries(){
        return administratorRepository.countries();
    }

	//Dashboard
    public List<String> chorbiesByCity(){
        checkLoggedIsAdmin();
        Set<String> cities = cities();
        List<String> result = new ArrayList<String>();

        for(String c:cities){
            result.add(administratorRepository.chorbiesByCity(c)[0]+": "+administratorRepository.chorbiesByCity(c)[1]);
        }

	    return result;
    }

    public List<String> chorbiesByCountry(){
        checkLoggedIsAdmin();
        Set<String> countries = countries();
        List<String> result = new ArrayList<String>();

        for(String c:countries){
            result.add(administratorRepository.chorbiesByCountry(c)[0]+": "+administratorRepository.chorbiesByCountry(c)[1]);
        }
        return result;
    }

    public Object[] minMaxAvgAgesChorbies(){
        checkLoggedIsAdmin();
        return administratorRepository.minMaxAvgAgesChorbies();
    }

    public Double ratioChorbiesInvalidCreditCard(){
        checkLoggedIsAdmin();
        return administratorRepository.ratioChorbiesInvalidCreditCard();
    }

    public Double ratioChorbiesSearchActivites(){
        checkLoggedIsAdmin();
        return administratorRepository.ratioChorbiesSearchActivites();
    }

    public Double ratioChorbiesSearchFriendShip(){
        checkLoggedIsAdmin();
        return administratorRepository.ratioChorbiesSearchFriendShip();
    }

    public Double ratioChorbiesSearchLove(){
        checkLoggedIsAdmin();
        return administratorRepository.ratioChorbiesSearchLove();
    }

    public  Object[] minMaxAvgLikesPerChorbi(){
        checkLoggedIsAdmin();
        return administratorRepository.minMaxAvgLikesPerChorbi();
    }

    public  Object[] minMaxAvgChirpsRecieved(){
        checkLoggedIsAdmin();
        return administratorRepository.minMaxAvgChirpsRecieved();
    }

    public  Object[] minMaxAvgChirpsSended(){
        checkLoggedIsAdmin();
        return administratorRepository.minMaxAvgChirpsSended();
    }

}

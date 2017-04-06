
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;
import domain.Coordinates;
import domain.Love;
import domain.Template;

@Service
@Transactional
public class ChorbiService {

	@Autowired
	private ChorbiRepository	chorbiRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private LoginService		loginService;


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

	public Chorbi findByPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Chorbi chorbi = this.chorbiRepository.findByPrincipal(userAccount.getId());
		System.out.println();
		Assert.isTrue(chorbi.getUserAccount().equals(userAccount));
		return chorbi;
	}

	public Collection<Chorbi> getLikersByChorbiId(final int id) {

		final Collection<Chorbi> res = this.chorbiRepository.LikersByChorbyId(id);

		return res;
	}

	public boolean hasAnyCoordinates(final Coordinates coor, final Chorbi chorbi) {
		boolean res = false;
		if (chorbi.getLocation().getCity().equals(coor.getCity()))
			res = true;
		else if (chorbi.getLocation().getCountry().equals(coor.getCountry()))
			res = true;
		else if (chorbi.getLocation().getProvince().equals(coor.getProvince()))
			res = true;
		else if (chorbi.getLocation().getState().equals(coor.getState()))
			res = true;
		return res;
	}

	public static int getAge(final Date dateOfBirth) {
		//code from: http://memorynotfound.com/calculate-age-from-date-of-birth-in-java/
		final Calendar today = Calendar.getInstance();
		final Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(dateOfBirth);
		if (birthDate.after(today))
			throw new IllegalArgumentException("You don't exist yet");
		final int todayYear = today.get(Calendar.YEAR);
		final int birthDateYear = birthDate.get(Calendar.YEAR);
		final int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
		final int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
		final int todayMonth = today.get(Calendar.MONTH);
		final int birthDateMonth = birthDate.get(Calendar.MONTH);
		final int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
		final int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
		int age = todayYear - birthDateYear;

		// If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
		if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth))
			age--;
		else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth))
			age--;
		return age;
	}

	public boolean ageEqualsUnderOverFive(final int toCompare, final int sample) {
		boolean res = false;
		if (toCompare == sample || toCompare == sample - 5 || toCompare == sample - 4 || toCompare == sample - 3 || toCompare == sample - 2 || toCompare == sample - 1 || toCompare == sample + 1 || toCompare == sample + 2 || toCompare == sample + 3
			|| toCompare == sample + 4 || toCompare == sample + 5)
			res = true;
		return res;
	}

	public Collection<Chorbi> getChorbiesByTemplate(final Template template) {

		final Collection<Chorbi> res = new ArrayList<Chorbi>();

		final Integer aproxAge = template.getAproxAge();
		final String genre = template.getGenre();
		final String kind = template.getKindRelationship();
		final String key = template.getKeyword();
		final Coordinates coor = template.getLocation();
		for (final Chorbi chorbi : this.findAll())
			if (genre == null || chorbi.getGenre().equals(genre))
				res.add(chorbi);
			else if (kind == null || chorbi.getKindRelationship().equals(kind))
				res.add(chorbi);
			else if (key == null || chorbi.getDescription().contains(key))
				res.add(chorbi);
			else if (coor == null || this.hasAnyCoordinates(coor, chorbi))
				res.add(chorbi);
			else if (aproxAge == null || this.ageEqualsUnderOverFive(aproxAge, ChorbiService.getAge(chorbi.getBirthDate())))
				res.add(chorbi);
		return res;

	}

	public Collection<Chorbi> findAllNotBanned() {
		return this.chorbiRepository.findAllNotBanned();
	}

	public Collection<Chorbi> findAllBanned() {
		return this.chorbiRepository.findAllBanned();
	}
}

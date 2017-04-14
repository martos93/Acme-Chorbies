
package services;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Chirp;
import domain.Chorbi;
import domain.Coordinates;
import domain.CreditCard;
import domain.Love;
import domain.Template;
import forms.ChorbiForm;
import repositories.ChorbiRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ChorbiService {

	@Autowired
	private ChorbiRepository	chorbiRepository;

	@Autowired
	private TemplateService		templateService;


	public Chorbi create() {

		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.CHORBI);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);
		userAccount.setIsBanned(false);

		final Chorbi res = new Chorbi();
		res.setUserAccount(userAccount);
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
		Boolean res = false;
		try {
			res = LoginService.getPrincipal().getAuthorities().contains(Authority.CHORBI);
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
		if (chorbi.getLocation().getCountry().equals(coor.getCountry()))
			res = true;
		if (chorbi.getLocation().getProvince().equals(coor.getProvince()))
			res = true;
		if (chorbi.getLocation().getState().equals(coor.getState()))
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

		Assert.isTrue(this.hasValidCreditCard(this.getLoggedChorbi()));

		final Collection<Chorbi> res = this.findAll();
		res.remove(this.findOne(this.findByPrincipal().getId()));

		final Integer aproxAge = template.getAproxAge();
		final String genre = template.getGenre();
		final String kind = template.getKindRelationship();
		final String key = template.getKeyword();
		final Coordinates coor = template.getLocation();

		final Collection<Chorbi> allChorbies = this.findAll();
		allChorbies.remove(this.findOne(this.findByPrincipal().getId()));

		for (final Chorbi chorbi : allChorbies) {
			if (genre.length() != 0 && !chorbi.getGenre().equals(genre))
				if (res.contains(chorbi))
					res.remove(chorbi);
			if (kind.length() != 0 && !chorbi.getKindRelationship().equals(kind))
				if (res.contains(chorbi))
					res.remove(chorbi);
			if (key.length() != 0 && !chorbi.getDescription().equals(key))
				if (res.contains(chorbi))
					res.remove(chorbi);
			if (aproxAge != null && !this.ageEqualsUnderOverFive(aproxAge, ChorbiService.getAge(chorbi.getBirthDate())))
				if (res.contains(chorbi))
					res.remove(chorbi);
			if (this.templateService.locationEmpty(template) == false && !this.hasAnyCoordinates(coor, chorbi))
				if (res.contains(chorbi))
					res.remove(chorbi);
		}
		return res;

	}

	public Collection<Chorbi> findAllNotBanned() {
		return this.chorbiRepository.findAllNotBanned();
	}

	public Collection<Chorbi> findAllBanned() {
		return this.chorbiRepository.findAllBanned();
	}

	// Other business methods

	public Chorbi getLoggedChorbi() {
		Chorbi result;
		UserAccount user;
		if (this.isAuthenticated() == true) {
			user = LoginService.getPrincipal();
			result = this.chorbiRepository.findChorbiByUsername(user.getUsername());
		} else
			result = null;
		return result;
	}

	public ChorbiForm reconstructForm(final Chorbi chorbi) {
		final ChorbiForm chorbiForm = new ChorbiForm();
		chorbiForm.setAcceptTerms(true);
		chorbiForm.setChorbiId(chorbi.getId());
		chorbiForm.setUsername(chorbi.getUserAccount().getUsername());
		chorbiForm.setName(chorbi.getName());
		chorbiForm.setSurname(chorbi.getSurname());
		chorbiForm.setEmail(chorbi.getEmail());
		chorbiForm.setPhoneNumber(chorbi.getPhoneNumber());
		chorbiForm.setPicture(chorbi.getPicture());
		chorbiForm.setDescription(chorbi.getDescription());
		chorbiForm.setKindRelationship(chorbi.getKindRelationship());
		chorbiForm.setGenre(chorbi.getGenre());
		chorbiForm.setBirthDate(chorbi.getBirthDate());

		chorbiForm.setCity(chorbi.getLocation().getCity());
		chorbiForm.setCountry(chorbi.getLocation().getCountry());
		chorbiForm.setProvince(chorbi.getLocation().getProvince());
		chorbiForm.setState(chorbi.getLocation().getState());

		chorbiForm.setBrandName(chorbi.getCreditCard().getBrandName());
		chorbiForm.setCvvCode(chorbi.getCreditCard().getCvvCode());
		chorbiForm.setExpirationMonth(chorbi.getCreditCard().getExpirationMonth());
		chorbiForm.setExpirationYear(chorbi.getCreditCard().getExpirationYear());
		chorbiForm.setHolderName(chorbi.getCreditCard().getHolderName());
		chorbiForm.setNumber(chorbi.getCreditCard().getNumber());

		return chorbiForm;
	}

	public Chorbi reconstructEdit(final ChorbiForm chorbiForm, final Chorbi chorbi) {

		chorbi.getUserAccount().setUsername(chorbiForm.getUsername());

		if ((chorbiForm.getNewpassword().length() > 0 && chorbiForm.getRepeatnewpassword().length() > 0 && chorbiForm.getNewpassword().equals(chorbiForm.getRepeatnewpassword()))) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			chorbi.getUserAccount().setPassword(encoder.encodePassword(chorbiForm.getNewpassword(), null));
		}
		chorbi.setName(chorbiForm.getName());
		chorbi.setSurname(chorbiForm.getSurname());
		chorbi.setEmail(chorbiForm.getEmail());
		chorbi.setPhoneNumber(chorbiForm.getPhoneNumber());
		chorbi.setPicture(chorbiForm.getPicture());
		chorbi.setDescription(chorbiForm.getDescription());
		chorbi.setKindRelationship(chorbiForm.getKindRelationship());
		chorbi.setGenre(chorbiForm.getGenre());
		chorbi.setBirthDate(chorbiForm.getBirthDate());

		final Coordinates location = new Coordinates();
		location.setCity(chorbiForm.getCity());
		location.setCountry(chorbiForm.getCountry());
		location.setProvince(chorbiForm.getProvince());
		location.setState(chorbiForm.getState());

		chorbi.setLocation(location);

		final CreditCard creditcard = new CreditCard();
		creditcard.setBrandName(chorbiForm.getBrandName());
		creditcard.setCvvCode(chorbiForm.getCvvCode());
		creditcard.setExpirationMonth(chorbiForm.getExpirationMonth());
		creditcard.setExpirationYear(chorbiForm.getExpirationYear());
		creditcard.setHolderName(chorbiForm.getHolderName());
		creditcard.setNumber(chorbiForm.getNumber());

		chorbi.setCreditCard(creditcard);
		return chorbi;
	}

	public Chorbi reconstruct(final ChorbiForm chorbiForm) {
		final Chorbi chorbi = this.create();
		chorbi.getUserAccount().setUsername(chorbiForm.getUsername());
		chorbi.getUserAccount().setPassword(chorbiForm.getPassword());
		chorbi.setName(chorbiForm.getName());
		chorbi.setSurname(chorbiForm.getSurname());
		chorbi.setEmail(chorbiForm.getEmail());
		chorbi.setPhoneNumber(chorbiForm.getPhoneNumber());
		chorbi.setPicture(chorbiForm.getPicture());
		chorbi.setDescription(chorbiForm.getDescription());
		chorbi.setKindRelationship(chorbiForm.getKindRelationship());
		chorbi.setGenre(chorbiForm.getGenre());
		chorbi.setBirthDate(chorbiForm.getBirthDate());

		final Coordinates location = new Coordinates();
		location.setCity(chorbiForm.getCity());
		location.setCountry(chorbiForm.getCountry());
		location.setProvince(chorbiForm.getProvince());
		location.setState(chorbiForm.getState());

		chorbi.setLocation(location);

		final CreditCard creditcard = new CreditCard();
		creditcard.setBrandName(chorbiForm.getBrandName());
		creditcard.setCvvCode(chorbiForm.getCvvCode());
		creditcard.setExpirationMonth(chorbiForm.getExpirationMonth());
		creditcard.setExpirationYear(chorbiForm.getExpirationYear());
		creditcard.setHolderName(chorbiForm.getHolderName());
		creditcard.setNumber(chorbiForm.getNumber());

		chorbi.setCreditCard(creditcard);
		return chorbi;
	}

	public void register(Chorbi chorbi) {
		Assert.notNull(chorbi);
		UserAccount userAccount;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount = chorbi.getUserAccount();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));
		chorbi.setUserAccount(userAccount);
		chorbi = this.save(chorbi);

		Template template = this.templateService.create(chorbi);
		template = this.templateService.create(template);
		chorbi.setTemplate(template);
		this.save(chorbi);
	}

	public Chorbi findChorbiByUsername(final String username) {
		return this.chorbiRepository.findChorbiByUsername(username);
	}

	public int edad(final Chorbi chorbi) {
		final String fecha_nac = chorbi.getBirthDate().getYear() + 1900 + "/" + chorbi.getBirthDate().getMonth() + "/" + chorbi.getBirthDate().getDate();
		final Date fechaActual = new Date();
		final SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
		final String hoy = formato.format(fechaActual);
		final String[] dat1 = fecha_nac.split("/");
		final String[] dat2 = hoy.split("/");
		int anos = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
		final int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
		if (mes < 2)
			anos = anos - 1;
		else if (mes == 0) {
			final int dia = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
			if (dia > 0)
				anos = anos - 1;
		}
		return anos;
	}

	public boolean hasValidCreditCard(final Chorbi chorbi) {
		final CreditCard c = chorbi.getCreditCard();

		final Calendar calendar = new GregorianCalendar(c.getExpirationYear(), c.getExpirationMonth(), 1);
		final Date expiration = calendar.getTime();

		return expiration.getTime() > (System.currentTimeMillis() + 86400000);
	}

	//Dashboard:
    public List<Chorbi> chorbiesSortedByLikes(){
	    return chorbiRepository.chorbiesSortedByLikes();
    }

    public Collection<Chorbi> chorbiMoreChirpsRecieved(){
        return chorbiRepository.chorbiMoreChirpsRecieved();
    }

    public Collection<Chorbi> chorbiMoreChirpsSended(){
        return chorbiRepository.chorbiMoreChirpsSended();
    }
}

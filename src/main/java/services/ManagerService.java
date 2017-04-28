
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Manager;
import domain.Money;
import domain.Chirp;
import domain.Chorbi;
import domain.CreditCard;
import domain.Event;
import forms.ManagerForm;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerRepository	managerRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	public Manager getLoggedManager() {
		Manager res = null;
		if (this.actorService.isAuthenticated())
			res = this.managerRepository.managerByUsername(LoginService.getPrincipal().getUsername());

		return res;
	}

	//CRUD Methods----------------------------------------------------------
	public Manager create() {

		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.MANAGER);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);

		final Manager res = new Manager();
		res.setUserAccount(userAccount);
		res.setSended(new ArrayList<Chirp>());
		res.setEvents(new ArrayList<Event>());
		
		Money m= new Money();
		m.setAmount(0.0);
		m.setCurrency("€");
		res.setAmountDue(m);
		
		return res;
	}
	
	public Manager findOne(final int id) {
		Manager result;
		result = this.managerRepository.findOne(id);
		return result;
	}
	
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
	
	public ManagerForm reconstructForm(final Manager manager) {
		final ManagerForm managerForm = new ManagerForm();
		
		managerForm.setName(manager.getName());
		managerForm.setSurname(manager.getSurname());
		managerForm.setEmail(manager.getEmail());
		managerForm.setPhoneNumber(manager.getPhoneNumber());

		managerForm.setCompany(manager.getCompany());
		managerForm.setBrandName(manager.getCreditCard().getBrandName());
		managerForm.setCvvCode(manager.getCreditCard().getCvvCode());
		managerForm.setExpirationMonth(manager.getCreditCard().getExpirationMonth());
		managerForm.setExpirationYear(manager.getCreditCard().getExpirationYear());
		managerForm.setHolderName(manager.getCreditCard().getHolderName());
		managerForm.setNumber(manager.getCreditCard().getNumber());
		managerForm.setVAT(manager.getVAT());

		managerForm.setAcceptTerms(true);
		managerForm.setUsername(manager.getUserAccount().getUsername());
		managerForm.setManagerId(manager.getId());
		return managerForm;
	}

	public Manager reconstructEdit(final ManagerForm managerForm, final Manager manager) {

		manager.getUserAccount().setUsername(managerForm.getUsername());

		if ((managerForm.getNewpassword().length() > 0 && managerForm.getRepeatnewpassword().length() > 0 && managerForm.getNewpassword().equals(managerForm.getRepeatnewpassword()))) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			manager.getUserAccount().setPassword(encoder.encodePassword(managerForm.getNewpassword(), null));
		}
		manager.setName(managerForm.getName());
		manager.setSurname(managerForm.getSurname());
		manager.setEmail(managerForm.getEmail());
		manager.setPhoneNumber(managerForm.getPhoneNumber());
	
		manager.setCompany(managerForm.getCompany());
		final CreditCard creditcard = new CreditCard();
		creditcard.setBrandName(managerForm.getBrandName());
		creditcard.setCvvCode(managerForm.getCvvCode());
		creditcard.setExpirationMonth(managerForm.getExpirationMonth());
		creditcard.setExpirationYear(managerForm.getExpirationYear());
		creditcard.setHolderName(managerForm.getHolderName());
		creditcard.setNumber(managerForm.getNumber());

		manager.setCreditCard(creditcard);
		manager.setVAT(managerForm.getVAT());
		return manager;
	}

	public Manager reconstruct(final ManagerForm managerForm, final BindingResult binding) {
		final Manager manager = this.create();
		manager.getUserAccount().setUsername(managerForm.getUsername());
		manager.getUserAccount().setPassword(managerForm.getPassword());
		manager.setName(managerForm.getName());
		manager.setSurname(managerForm.getSurname());
		manager.setEmail(managerForm.getEmail());
		manager.setPhoneNumber(managerForm.getPhoneNumber());

		manager.setCompany(managerForm.getCompany());
		final CreditCard creditcard = new CreditCard();
		creditcard.setBrandName(managerForm.getBrandName());
		creditcard.setCvvCode(managerForm.getCvvCode());
		creditcard.setExpirationMonth(managerForm.getExpirationMonth());
		creditcard.setExpirationYear(managerForm.getExpirationYear());
		creditcard.setHolderName(managerForm.getHolderName());
		creditcard.setNumber(managerForm.getNumber());
		manager.setCreditCard(creditcard);
		manager.setVAT(managerForm.getVAT());

		this.validator.validate(manager, binding);
		return manager;
	}

	public void register(Manager manager) {
		Assert.notNull(manager);
		UserAccount userAccount;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount = manager.getUserAccount();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));
		manager.setUserAccount(userAccount);
		manager = this.save(manager);
		this.save(manager);
	}
	
	public Manager modify(final Manager manager) {
		Assert.isTrue(manager.getId() == this.findByPrincipal().getId());
		return this.managerRepository.saveAndFlush(manager);
	}
	
}

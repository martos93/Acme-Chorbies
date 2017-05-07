
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChirpRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chirp;
import domain.Chorbi;
import domain.Event;
import domain.Manager;
import forms.ChirpForm;
import forms.ChirpManagerForm;

@Service
@Transactional
public class ChirpService {

	@Autowired
	private ChirpRepository	chirpRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ChorbiService	chorbiService;

	@Autowired
	private Validator		validator;

	@Autowired
	private ManagerService	managerService;


	public ChirpService() {
		super();
	}

	public Collection<Chirp> findAll() {
		return this.chirpRepository.findAll();
	}

	public Chirp findOne(final int id) {
		return this.chirpRepository.findOne(id);
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);
		this.actorService.checkIsLogged();
		return this.chirpRepository.save(chirp);
	}
	public void delete(final Chirp chirp) {
		this.chirpRepository.delete(chirp);
	}

	public Chirp create() {
		this.actorService.checkIsLogged();
		final Chirp res = new Chirp();
		final Collection<String> atch = new ArrayList<String>();
		res.setAttachments(atch);
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		final UserAccount user = LoginService.getPrincipal();
		for (final Authority a : user.getAuthorities())
			if (a.getAuthority().equals(Authority.CHORBI)) {
				res.setSenderC(this.chorbiService.findByPrincipal());
				break;
			} else if (a.getAuthority().equals(Authority.MANAGER)) {
				res.setSenderM(this.managerService.findByPrincipal());
				break;
			}
		return res;

	}

	public Chirp reconstruct(final ChirpForm chirpForm, final BindingResult binding) {
		final Chirp chirp = this.create();
		chirp.setAttachments(chirpForm.getAttachments());
		chirp.setReceiver(chirpForm.getReceiver());
		chirp.setText(chirpForm.getText());
		chirp.setSubject(chirpForm.getSubject());

		this.validator.validate(chirp, binding);
		return chirp;
	}

	public Chirp reconstruct(final ChirpManagerForm chirpManagerForm, final BindingResult binding, final Chorbi reciever) {
		final Chirp chirp = this.create();
		chirp.setAttachments(chirpManagerForm.getAttachments());
		chirp.setReceiver(reciever);
		chirp.setText(chirpManagerForm.getText());
		chirp.setSubject(chirpManagerForm.getSubject());

		this.validator.validate(chirp, binding);
		return chirp;
	}

	public void deleteChirp(final int chirpId) {
		Assert.isTrue(this.chorbiService.isAuthenticated());
		Assert.isTrue(this.chorbiService.isChorbi());
		final Chorbi c = this.chorbiService.findByPrincipal();

		final Chirp chirp = this.findOne(chirpId);
		if (chirp.getReceiver().getId() == c.getId() || chirp.getSenderC().getId() == c.getId())
			this.delete(chirp);

	}

	public void sendChirp(final Chirp chirp) {
		final Authority aut = new Authority();
		aut.setAuthority(Authority.CHORBI);

		final Chorbi receiverChorbi = chirp.getReceiver();

		try {
			Assert.isTrue(receiverChorbi.getUserAccount().getAuthorities().contains(aut) && LoginService.getPrincipal().getAuthorities().contains(aut));
		} catch (final Exception e) {

		}

		receiverChorbi.getReceived().add(chirp);
		this.chorbiService.save(receiverChorbi);

		final Chorbi senderChorbi = this.chorbiService.findByPrincipal();
		senderChorbi.getSended().add(chirp);
		this.chorbiService.save(senderChorbi);

		this.save(chirp);

	}

	public void replyChirp(final Chirp chirp) {
		final Authority aut = new Authority();
		aut.setAuthority(Authority.CHORBI);

		final Chorbi receiverChorbi = chirp.getReceiver();

		try {
			Assert.isTrue(receiverChorbi.getUserAccount().getAuthorities().contains(aut) && LoginService.getPrincipal().getAuthorities().contains(aut));
			Assert.isTrue(chirp.getReceiver().getId() != this.chorbiService.getLoggedChorbi().getId());
		} catch (final Exception e) {

		}

		receiverChorbi.getReceived().add(chirp);
		this.chorbiService.save(receiverChorbi);

		final Chorbi senderChorbi = this.chorbiService.findByPrincipal();
		senderChorbi.getSended().add(chirp);
		this.chorbiService.save(senderChorbi);

		this.save(chirp);

	}

	public void forwardChirp(final Chirp chirp) {
		final Authority aut = new Authority();
		aut.setAuthority(Authority.CHORBI);

		final Chorbi receiverChorbi = chirp.getReceiver();

		try {
			Assert.isTrue(receiverChorbi.getUserAccount().getAuthorities().contains(aut) && LoginService.getPrincipal().getAuthorities().contains(aut));
			Assert.isTrue(chirp.getReceiver().getId() != this.chorbiService.getLoggedChorbi().getId());
		} catch (final Exception e) {

		}

		receiverChorbi.getReceived().add(chirp);
		this.chorbiService.save(receiverChorbi);

		final Chorbi senderChorbi = this.chorbiService.findByPrincipal();
		senderChorbi.getSended().add(chirp);
		this.chorbiService.save(senderChorbi);

		this.save(chirp);

	}

	public void broadcastChirps(final Event event, final ChirpManagerForm chirpManagerForm, final BindingResult binding) {
		final Authority aut = new Authority();
		aut.setAuthority(Authority.MANAGER);

		final Manager manager = this.managerService.getLoggedManager();
		Assert.isTrue(manager.getUserAccount().getAuthorities().contains(aut));
		Assert.isTrue(event.getManager().equals(manager));

		try {

			for (final Chorbi c : event.getChorbies()) {
				final Chirp aux = this.reconstruct(chirpManagerForm, binding, c);
				final Chorbi receiverChorbi = aux.getReceiver();
				receiverChorbi.getReceived().add(aux);
				this.chorbiService.save(receiverChorbi);

				manager.getSended().add(aux);
				this.managerService.save(manager);

				this.save(aux);
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}
}


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

import domain.Chirp;
import domain.Chorbi;
import forms.ChirpForm;
import repositories.ChirpRepository;
import security.Authority;
import security.LoginService;

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
		res.setSenderC(this.chorbiService.findByPrincipal());
		res.setSenderM(this.managerService.findByPrincipal());
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

	public void deleteChirp(final int chirpId) {
		final Chirp chirp = this.findOne(chirpId);
		if (chirp.getReceiver() != null && chirp.getReceiver().getId() == this.chorbiService.findByPrincipal().getId())
			chirp.setReceiver(null);
		if (chirp.getSenderC() != null && chirp.getSenderC().getId() == this.chorbiService.findByPrincipal().getId())
			chirp.setSenderC(null);

		final Authority aut = new Authority();
		aut.setAuthority(Authority.CHORBI);

		try {
			Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(aut));
		} catch (final Exception e) {

		}

		if (chirp.getReceiver() == null)
			if (chirp.getSenderC() == null)
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
}

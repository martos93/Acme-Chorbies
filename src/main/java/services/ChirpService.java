
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
import domain.Chirp;
import forms.ChirpForm;

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
		res.setSender(this.chorbiService.findByPrincipal());
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
		//Assert.isTrue(chirp.getReceiver().getUserAccount().getUsername().equals(LoginService.getPrincipal().getUsername()) || chirp.getSender().getUserAccount().getUsername().equals(LoginService.getPrincipal().getUsername()));

		//		if (chirp.getSender().getId() == this.actorService.getLoggedActor().getId())
		//			chirp.setSender(null);
		//		if (chirp.getSender() == null && chirp.getReceiver() == null)
		//			this.delete(chirp);
		//		else
		//			chirp = this.save(chirp);
		//
		//		if (chirp.getReceiver() == null || chirp.getReceiver().getId() == this.actorService.getLoggedActor().getId())
		//			chirp.setReceiver(null);
		//		if (chirp.getReceiver() == null && chirp.getSender() == null)
		//			this.delete(chirp);
		//		else
		//			chirp = this.save(chirp);

		
		if (chirp.getReceiver() != null && chirp.getReceiver().getId() == this.chorbiService.findByPrincipal().getId())
			chirp.setReceiver(null);
		if (chirp.getSender() != null && chirp.getSender().getId() == this.chorbiService.findByPrincipal().getId())
			chirp.setSender(null);

		if (chirp.getReceiver() == null)
			if (chirp.getSender() == null)
				this.delete(chirp);

	}
}

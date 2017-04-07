
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import domain.Banner;
import repositories.BannerRepository;

@Service
@Transactional
public class BannerService {

	@Autowired
	private BannerRepository	bannerRepository;

	@Autowired
	private Validator			validator;


	// Constructor
	public BannerService() {
		super();
	}

	// Simple CRUD methods

	public Collection<Banner> findAll() {
		return this.bannerRepository.findAll();
	}

	public Banner findOne(final int id) {
		Banner result;
		result = this.bannerRepository.findOne(id);
		return result;
	}

	public Banner save(final Banner banner) {

		return this.bannerRepository.save(banner);
	}

}

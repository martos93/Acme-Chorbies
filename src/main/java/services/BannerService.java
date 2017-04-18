
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.BannerRepository;
import domain.Banner;

@Service
@Transactional
public class BannerService {

	@Autowired
	private BannerRepository		bannerRepository;

	@Autowired
	private AdministratorService	administratorService;


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

	public void addBanner(final String url) {
		this.administratorService.checkLoggedIsAdmin();
		final Banner banner = this.bannerRepository.findAll().get(0);
		banner.getBanners().add(url);
	}

	public void deleteBanner(final String url) {
		this.administratorService.checkLoggedIsAdmin();
		final Banner banner = this.bannerRepository.findAll().get(0);
		banner.getBanners().remove(url);
	}

}

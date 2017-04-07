
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Cache;
import repositories.CacheRepository;

@Service
@Transactional
public class CacheService {

	//Repository---------------------------------------------------------
	@Autowired
	private CacheRepository			cacheRepository;

	@Autowired
	private AdministratorService	administratorService;


	//Constructor--------------------------------------------------------

	public CacheService() {
		super();
	}

	// Simple CRUD methods

	public Cache create() {

		this.administratorService.checkLoggedIsAdmin();
		final Cache res = new Cache();
		//HAY QUE CAMBIAR EL PATRON
		res.setHours("12");
		res.setMinutes("0");
		res.setSeconds("0");
		return res;
	}

	public Cache save(final Cache cache) {
		this.administratorService.checkLoggedIsAdmin();
		return this.cacheRepository.save(cache);
	}

	public Cache findOne(final int id) {
		Assert.notNull(id);
		final Cache res = this.cacheRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Collection<Cache> findAll() {
		return this.cacheRepository.findAll();
	}

	//other methods
	public Cache selectCache() {
		Cache cache;
		cache = this.cacheRepository.findAll().get(0);
		Assert.notNull(cache);
		return cache;
	}

}

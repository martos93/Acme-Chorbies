
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Fee;
import domain.Money;
import repositories.FeeRepository;

@Service
@Transactional
public class FeeService {

	@Autowired
	private FeeRepository			feeRepository;

	@Autowired
	private AdministratorService	administratorService;


	public FeeService() {
		super();
	}

	public Fee create() {
		this.administratorService.checkLoggedIsAdmin();

		final Fee res = new Fee();
		final Money money = new Money();
		money.setAmount(1.0);
		money.setCurrency("EUROS");
		res.setManagerAmount(money);
		return res;

	}
	public void save(final Fee fee) {
		this.administratorService.checkLoggedIsAdmin();
		Assert.notNull(fee);
		this.feeRepository.save(fee);
	}

	public Fee findOne(final int id) {
		Fee result;
		Assert.notNull(id);
		result = this.feeRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Fee> findAll() {
		Collection<Fee> result;
		result = this.feeRepository.findAll();
		Assert.notNull(result);
		return result;

	}

}

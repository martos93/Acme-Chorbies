
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Fee;
import repositories.FeeRepository;

@Service
@Transactional
public class FeeService {

	@Autowired
	private FeeRepository			feeRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	public FeeService() {
		super();
	}

	public Fee create() {
		this.administratorService.checkLoggedIsAdmin();

		final Fee res = new Fee();

		res.setManagerAmount(1.0);
		res.setChorbiAmount(1.0);
		return res;

	}
	public Fee save(final Fee fee) {
		Assert.notNull(fee);
		final Fee saved;
		this.administratorService.checkLoggedIsAdmin();
		saved = this.feeRepository.save(fee);
		this.feeRepository.flush();
		return saved;
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

	//other methods
	public Fee selectFee() {
		Fee fee;
		fee = this.feeRepository.findAll().get(0);
		Assert.notNull(fee);
		return fee;
	}

	public Fee reconstruct(final Fee fee, final BindingResult binding) {

		Fee res = new Fee();

		if (fee.getId() == 0)
			res = fee;
		else {
			res = this.feeRepository.findOne(fee.getId());
			res.setChorbiAmount(fee.getChorbiAmount());
			res.setManagerAmount(fee.getManagerAmount());
		}
		this.validator.validate(res, binding);

		return res;

	}

}

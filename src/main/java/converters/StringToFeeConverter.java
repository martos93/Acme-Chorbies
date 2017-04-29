
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import domain.Fee;
import repositories.FeeRepository;

public class StringToFeeConverter implements Converter<String, Fee> {

	@Autowired
	private FeeRepository feeRepository;


	@Override
	public Fee convert(final String string) {
		Fee fee;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				fee = null;
			else {
				id = Integer.valueOf(string);
				fee = this.feeRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return fee;

	}

}

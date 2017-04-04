
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Administrator;
import repositories.AdministratorRepository;

@Component
@Transactional
public class StringToAdministratorConverter implements Converter<String, Administrator> {

	@Autowired
	private AdministratorRepository administratorRepository;


	@Override
	public Administrator convert(final String string) {
		Administrator administrator;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				administrator = null;
			else {
				id = Integer.valueOf(string);
				administrator = this.administratorRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return administrator;
	}

}


package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ManagerRepository;
import domain.Manager;

@Component
@Transactional
public class StringToManagerConverter implements Converter<String, Manager> {

	@Autowired
	private ManagerRepository	managerRepository;


	@Override
	public Manager convert(final String string) {
		Manager manager;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				manager = null;
			else {
				id = Integer.valueOf(string);
				manager = this.managerRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return manager;
	}

}

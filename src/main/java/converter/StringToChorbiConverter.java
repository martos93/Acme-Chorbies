
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Chorbi;
import repositories.ChorbiRepository;

@Component
@Transactional
public class StringToChorbiConverter implements Converter<String, Chorbi> {

	@Autowired
	private ChorbiRepository chorbiRepository;


	@Override
	public Chorbi convert(final String string) {
		Chorbi chorbi;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				chorbi = null;
			else {
				id = Integer.valueOf(string);
				chorbi = this.chorbiRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return chorbi;
	}

}

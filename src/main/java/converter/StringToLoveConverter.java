
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Love;
import repositories.LoveRepository;

@Component
@Transactional
public class StringToLoveConverter implements Converter<String, Love> {

	@Autowired
	private LoveRepository loveRepository;


	@Override
	public Love convert(final String string) {
		Love love;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				love = null;
			else {
				id = Integer.valueOf(string);
				love = this.loveRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return love;
	}

}


package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Cache;
import repositories.CacheRepository;

@Component
@Transactional
public class StringToCacheConverter implements Converter<String, Cache> {

	@Autowired
	private CacheRepository cacheRepository;


	@Override
	public Cache convert(final String string) {
		Cache cache;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				cache = null;
			else {
				id = Integer.valueOf(string);
				cache = this.cacheRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return cache;
	}

}

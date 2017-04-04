
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Banner;
import repositories.BannerRepository;

@Component
@Transactional
public class StringToBannerConverter implements Converter<String, Banner> {

	@Autowired
	private BannerRepository bannerRepository;


	@Override
	public Banner convert(final String string) {
		final Banner banner;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				banner = null;
			else {
				id = Integer.valueOf(string);
				banner = this.bannerRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return banner;
	}

}


package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Chirp;
import repositories.ChirpRepository;

@Component
@Transactional
public class StringToChirpConverter implements Converter<String, Chirp> {

	@Autowired
	private ChirpRepository chirpRepository;


	@Override
	public Chirp convert(final String string) {
		Chirp chirp;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				chirp = null;
			else {
				id = Integer.valueOf(string);
				chirp = this.chirpRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return chirp;
	}

}

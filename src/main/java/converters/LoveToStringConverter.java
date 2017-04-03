
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import domain.Love;

@Component
@Transactional
public class LoveToStringConverter implements Converter<Love, String> {

	@Override
	public String convert(final Love arg0) {
		Assert.notNull(arg0);

		String string;
		string = String.valueOf(arg0.getId());

		return string;
	}

}

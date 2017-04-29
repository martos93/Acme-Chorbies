
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import domain.Manager;

public class ManagerToStringConverter implements Converter<Manager, String> {

	@Override
	public String convert(final Manager arg0) {
		Assert.notNull(arg0);

		String string;
		string = String.valueOf(arg0.getId());

		return string;
	}

}


package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import domain.Event;

public class EventToStringConverter implements Converter<Event, String> {

	@Override
	public String convert(final Event arg0) {
		Assert.notNull(arg0);

		String string;
		string = String.valueOf(arg0.getId());

		return string;
	}

}

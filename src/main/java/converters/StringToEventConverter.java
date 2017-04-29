
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.EventRepository;
import domain.Event;

@Component
@Transactional
public class StringToEventConverter implements Converter<String, Event> {

	@Autowired
	private EventRepository	eventRepository;


	@Override
	public Event convert(final String string) {
		Event event;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				event = null;
			else {
				id = Integer.valueOf(string);
				event = this.eventRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return event;
	}
}

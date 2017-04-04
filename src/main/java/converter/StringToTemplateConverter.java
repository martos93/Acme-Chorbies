
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Template;
import repositories.TemplateRepository;

@Component
@Transactional
public class StringToTemplateConverter implements Converter<String, Template> {

	@Autowired
	private TemplateRepository templateRepository;


	@Override
	public Template convert(final String string) {
		Template template;
		int id;

		try {
			if (StringUtils.isEmpty(string))
				template = null;
			else {
				id = Integer.valueOf(string);
				template = this.templateRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return template;
	}

}

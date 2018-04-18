
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Recruited;

@Component
@Transactional
public class RecuitedToStringConverter implements Converter<Recruited, String> {

	@Override
	public String convert(final Recruited o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}


package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.KeybladeWielder;

@Component
@Transactional
public class KeybladeWielderToStringConverter implements Converter<KeybladeWielder, String> {

	@Override
	public String convert(final KeybladeWielder o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}

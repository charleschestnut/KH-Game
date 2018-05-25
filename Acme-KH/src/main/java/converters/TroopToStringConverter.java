
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Troop;

@Component
@Transactional
public class TroopToStringConverter implements Converter<Troop, String> {

	@Override
	public String convert( Troop o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}

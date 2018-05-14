
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Faction;

@Component
@Transactional
public class FactionToStringConverter implements Converter<Faction, String> {

	@Override
	public String convert(final Faction o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}
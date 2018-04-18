
package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.OrgRange;

@Component
@Transactional
public class OrgRangeToStringConverter implements Converter<OrgRange, String> {

	@Override
	public String convert(final OrgRange o) {
		String result;
		StringBuilder builder;
		if (o == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(o.getDeclaringClass().toString(), "UTF-8"));

				result = builder.toString();
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}

}

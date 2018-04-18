
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Recruiter;

@Component
@Transactional
public class RecruiterToStringConverter implements Converter<Recruiter, String> {

	@Override
	public String convert(final Recruiter o) {
		String result;

		if (o == null)
			result = null;
		else
			result = String.valueOf(o.getId());

		return result;
	}

}

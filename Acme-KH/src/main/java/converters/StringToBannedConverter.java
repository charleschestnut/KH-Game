
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;
import repositories.BannedRepository;

import domain.Actor;
import domain.Banned;

@Component
@Transactional
public class StringToBannedConverter implements Converter<String, Banned> {

	@Autowired
	BannedRepository	repository;


	@Override
	public Banned convert(String text) {
		Banned result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}

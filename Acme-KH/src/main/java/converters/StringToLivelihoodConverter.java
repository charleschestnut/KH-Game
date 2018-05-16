
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import repositories.AdministratorRepository;
import repositories.LivelihoodRepository;

import domain.Actor;
import domain.Livelihood;

@Component
@Transactional
public class StringToLivelihoodConverter implements Converter<String, Livelihood> {

	@Autowired
	LivelihoodRepository	repository;


	@Override
	public Livelihood convert(String text) {
		Livelihood result;
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

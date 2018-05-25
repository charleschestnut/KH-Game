
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import repositories.AdministratorRepository;
import repositories.DefenseRepository;

import domain.Actor;
import domain.Defense;

@Component
@Transactional
public class StringToDefenseConverter implements Converter<String, Defense> {

	@Autowired
	DefenseRepository	repository;


	@Override
	public Defense convert(String text) {
		Defense result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch ( Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}

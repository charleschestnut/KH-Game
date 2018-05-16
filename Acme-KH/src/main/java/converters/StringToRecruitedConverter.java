
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import repositories.AdministratorRepository;
import repositories.RecruitedRepository;

import domain.Actor;
import domain.Recruited;

@Component
@Transactional
public class StringToRecruitedConverter implements Converter<String, Recruited> {

	@Autowired
	RecruitedRepository	repository;


	@Override
	public Recruited convert(String text) {
		Recruited result;
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

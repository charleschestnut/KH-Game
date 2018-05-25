
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import repositories.AdministratorRepository;
import repositories.PrizeRepository;

import domain.Actor;
import domain.Prize;

@Component
@Transactional
public class StringToPrizeConverter implements Converter<String, Prize> {

	@Autowired
	PrizeRepository	repository;


	@Override
	public Prize convert(String text) {
		Prize result;
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

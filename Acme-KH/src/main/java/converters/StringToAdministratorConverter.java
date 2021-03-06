
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;

import domain.Administrator;

@Component
@Transactional
public class StringToAdministratorConverter implements Converter<String, Administrator> {

	@Autowired
	AdministratorRepository	repository;


	@Override
	public Administrator convert(String text) {
		Administrator result;
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

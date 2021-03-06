
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BuildingRepository;
import domain.Building;

@Component
@Transactional
public class StringToBuildingConverter implements Converter<String, Building> {

	@Autowired
	BuildingRepository	repository;


	@Override
	public Building convert( String text) {
		Building result;
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

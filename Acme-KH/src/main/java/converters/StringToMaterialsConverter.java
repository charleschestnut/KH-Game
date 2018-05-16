
package converters;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import repositories.AdministratorRepository;

import domain.Actor;
import domain.Coordinates;
import domain.Materials;

@Component
@Transactional
public class StringToMaterialsConverter implements Converter<String, Materials> {


	@Override
	public Materials convert(String text) {
		Materials result;
		String parts[]; 

		if(text == null)
			result = null;
		else
			try {
				parts = text.split("\\|");
				result = new Materials();
				result.setGummiCoal(Integer.valueOf(URLDecoder.decode(parts[0], "UFT-8")));
				result.setMunny(Integer.valueOf(URLDecoder.decode(parts[1], "UFT-8")));
				result.setMytrhil(Integer.valueOf(URLDecoder.decode(parts[2], "UFT-8")));
				
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}
}


package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Building;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BuildingServiceTest extends AbstractTest {

	@Autowired
	private BuildingService	buildingService;


	//Aqui solo se ejecuta el test de delete porque los edificios se borran llamando al servicio de building
	//pero se crean en sus propios servicios

	@Test
	public void deleteDriver() {
		final Object testingData[][] = {

			{
				"manager1", "recruiter1", IllegalArgumentException.class
			}, //Intentando borrar un recruiter final (error)
			{
				"manager2", "recruiter3", IllegalArgumentException.class
			},	//Intentando borrar un recruiter que no es tuyo (error)
			{
				"manager1", "recruiter3", null
			}
		//OK

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void template(String username, String buildingId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			Building b = this.buildingService.findOne(super.getEntityId(buildingId));
			this.buildingService.delete(b);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

}

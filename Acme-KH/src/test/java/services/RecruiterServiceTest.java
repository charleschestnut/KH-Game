
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Recruiter;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RecruiterServiceTest extends AbstractTest {

	@Autowired
	private RecruiterService	recruiterService;


	//El delete est� en el test de building
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"manager1", "recruiter1", null, "a"
			}, //Editando
			{
				"manager2", "recruiter1", IllegalArgumentException.class, "a"
			},	//Intentando borrar un recruiter que no es tuyo (error)
			{
				"manager1", "recruiter1", ConstraintViolationException.class, ""
			}
		//Descripci�n err�nea

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
		}
	}

	protected void template(String username, String buildingId, final Class<?> expected, String description) {
		Class<?> caught;
		Recruiter d;

		caught = null;
		try {
			super.authenticate(username);
			d = this.recruiterService.findOne(super.getEntityId(buildingId));
			d.setDescription(description);

			this.recruiterService.save(d);
			this.recruiterService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
}

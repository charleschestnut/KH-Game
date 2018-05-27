
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Requirement;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequirementServiceTest extends AbstractTest {

	@Autowired
	private RequirementService	reqirementService;


	//El delete está en el test de building
	@Test
	public void saveDriver() {
		final Object testingData[][] = {

			{
				"manager1", "requirement1", IllegalArgumentException.class
			},
			//edit un requisito (erroneo es  final)
			{
				"manager2", "requirement2", IllegalArgumentException.class
			},
			//edit un requisito (erroneo es de otro)
			{
				"manager1", "requirement2", null
			},
		//edit un requisito 

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	@Test
	public void deleteDriver() {
		final Object testingData[][] = {

			{
				"manager1", "requirement1", IllegalArgumentException.class
			},
			//edit un requisito (erroneo es  final)
			{
				"manager2", "requirement2", IllegalArgumentException.class
			},
			//edit un requisito (erroneo es de otro)
			{
				"manager1", "requirement2", null
			},
		//edit un requisito 

		};

		for (int i = 0; i < testingData.length; i++) {

			super.startTransaction();
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void template(String username, String requirementId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Requirement r = this.reqirementService.findOne(super.getEntityId(requirementId));
			r.setLvl(2);
			r.setId(0); //Porque un requisito no se puede editar
			this.reqirementService.save(r);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

	protected void deleteTemplate(String username, String requirementId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			Requirement r = this.reqirementService.findOne(super.getEntityId(requirementId));
			this.reqirementService.delete(r);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}
}


package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequirementRepository;
import domain.Building;
import domain.Requirement;

@Service
@Transactional
public class RequirementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequirementRepository	RequirementRepository;
	@Autowired
	private Validator				validator;
	@Autowired
	private ActorService			actorService;


	// CRUD methods

	public Requirement create(final Building b) {
		Requirement requirement;

		requirement = new Requirement();
		requirement.setMainBuilding(b);

		return requirement;
	}

	public Requirement save(final Requirement Requirement) {
		Assert.notNull(Requirement);
		Assert.isTrue(Requirement.getMainBuilding().getContentManager().equals(this.actorService.findByPrincipal()), "error.message.req.creator");
		Assert.isTrue(!Requirement.getMainBuilding().getIsFinal(), "error.message.req.final");
		Assert.isTrue(!Requirement.getMainBuilding().equals(Requirement.getRequiredBuilding()), "error.message.req.sameBuilding");
		Assert.isTrue(Requirement.getRequiredBuilding().getIsFinal(), "error.message.req.final2");
		Assert.isTrue(Requirement.getId() == 0, "error.message.req.edit");
		Requirement saved;

		saved = this.RequirementRepository.save(Requirement);

		return saved;
	}

	public Requirement findOne(final int RequirementId) {
		Assert.notNull(RequirementId);

		Requirement Requirement;

		Requirement = this.RequirementRepository.findOne(RequirementId);

		return Requirement;
	}

	public Collection<Requirement> findAll() {
		Collection<Requirement> Requirements;

		Requirements = this.RequirementRepository.findAll();

		return Requirements;
	}

	public void delete(final Requirement Requirement) {
		Assert.notNull(Requirement);
		Assert.isTrue(Requirement.getMainBuilding().getContentManager().equals(this.actorService.findByPrincipal()), "error.message.req.creator");
		Assert.isTrue(!Requirement.getMainBuilding().getIsFinal(), "error.message.req.final");

		this.RequirementRepository.delete(Requirement);
	}

	public Requirement reconstruct(Requirement requirement, final BindingResult binding) {
		final Requirement original = this.findOne(requirement.getId());

		if (!original.getMainBuilding().getIsFinal()) {
			if (requirement.getId() != 0)
				requirement.setMainBuilding(original.getMainBuilding());
		}

		else
			requirement = original;

		this.validator.validate(requirement, binding);

		return requirement;
	}

	//other methods

	public Collection<Requirement> getRequirementsByBuilding(final Integer buildingId) {
		return this.RequirementRepository.getRequirementsForABuilt(buildingId);
	}

	public Boolean isTrue(final Requirement r) {
		final Integer i = this.RequirementRepository.getIsTrueRequirement(r.getRequiredBuilding().getId(), r.getLvl(), this.actorService.findByPrincipal().getId());
		Boolean res = true;
		if (i == null || i < 1)
			res = false;
		return res;
	}

	public Boolean fulfillsRequirements(final Integer buildingId) {
		final Integer i = this.RequirementRepository.getBuildingFulfillsReqs(buildingId, this.actorService.findByPrincipal().getId());
		Boolean res = true;
		if (i == null || i < 1)
			res = false;
		return res;
	}

}

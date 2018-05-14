
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.DefenseRepository;
import security.LoginService;
import domain.ContentManager;
import domain.Defense;

@Service
@Transactional
public class DefenseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private DefenseRepository	DefenseRepository;
	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;


	// CRUD methods

	public Defense create() {
		Defense Defense;

		Defense = (domain.Defense) this.buildingService.create();

		return Defense;
	}
	public Defense save(final Defense Defense) {
		Assert.notNull(Defense);
		Assert.isTrue(Defense.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");

		Defense saved;

		saved = this.DefenseRepository.save(Defense);

		return saved;
	}

	public Defense findOne(final int DefenseId) {
		Assert.notNull(DefenseId);

		Defense Defense;

		Defense = this.DefenseRepository.findOne(DefenseId);

		return Defense;
	}

	public Collection<Defense> findAll() {
		Collection<Defense> Defenses;

		Defenses = this.DefenseRepository.findAll();

		return Defenses;
	}

	public void delete(final Defense Defense) {
		Assert.notNull(Defense);

		this.DefenseRepository.delete(Defense);
	}

	public Defense reconstruct(Defense defense, final BindingResult binding) {
		final Defense original = this.findOne(defense.getId());

		if (defense.getId() == 0) {
			defense.setContentManager((ContentManager) this.actorService.findByPrincipal());
			defense.setIsFinal(false);

		} else if (!original.getIsFinal())
			defense.setContentManager(original.getContentManager());

		else
			defense = original;

		this.validator.validate(defense, binding);

		return defense;
	}
}

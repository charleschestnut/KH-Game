
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LivelihoodRepository;
import security.LoginService;
import domain.ContentManager;
import domain.Livelihood;

@Service
@Transactional
public class LivelihoodService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private LivelihoodRepository	LivelihoodRepository;
	@Autowired
	private BuildingService			buildingService;
	@Autowired
	private Validator				validator;
	@Autowired
	private ActorService			actorService;


	// CRUD methods

	public Livelihood create() {
		Livelihood Livelihood;

		Livelihood = (domain.Livelihood) this.buildingService.create();

		return Livelihood;
	}

	public Livelihood save(final Livelihood Livelihood) {
		Assert.notNull(Livelihood);
		Assert.isTrue(Livelihood.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");

		Livelihood saved;

		saved = this.LivelihoodRepository.save(Livelihood);

		return saved;
	}

	public Livelihood findOne(final int LivelihoodId) {
		Assert.notNull(LivelihoodId);

		Livelihood Livelihood;

		Livelihood = this.LivelihoodRepository.findOne(LivelihoodId);

		return Livelihood;
	}

	public Collection<Livelihood> findAll() {
		Collection<Livelihood> Livelihoods;

		Livelihoods = this.LivelihoodRepository.findAll();

		return Livelihoods;
	}

	public void delete(final Livelihood Livelihood) {
		Assert.notNull(Livelihood);

		this.LivelihoodRepository.delete(Livelihood);
	}
	public Livelihood reconstruct(Livelihood l, final BindingResult binding) {
		final Livelihood original = this.findOne(l.getId());

		if (l.getId() == 0) {
			l.setContentManager((ContentManager) this.actorService.findByPrincipal());
			l.setIsFinal(false);

		} else if (!original.getIsFinal())
			l.setContentManager(original.getContentManager());

		else
			l = original;

		this.validator.validate(l, binding);

		return l;
	}
}

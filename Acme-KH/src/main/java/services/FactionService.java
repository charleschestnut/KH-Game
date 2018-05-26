
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FactionRepository;
import security.Authority;
import domain.Faction;

@Service
@Transactional
public class FactionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FactionRepository	FactionRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	Validator					validator;


	public Faction create() {
		Faction faction;

		faction = new Faction();
		faction.setExtraAttack(0.0);
		faction.setExtraDefense(0.0);
		faction.setExtraResources(0.0);
		faction.setGalaxy(0);

		return faction;
	}

	public Faction save(final Faction Faction) {
		Assert.notNull(Faction);

		Faction saved;

		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(Authority.MANAGER), "message.error.notManager");

		saved = this.FactionRepository.save(Faction);

		return saved;
	}

	public Faction findOne(final int FactionId) {
		Assert.notNull(FactionId);

		Faction Faction;

		Faction = this.FactionRepository.findOne(FactionId);

		Assert.notNull(Faction, "error.message.null");
		return Faction;
	}

	public Collection<Faction> findAll() {
		Collection<Faction> Factions;

		Factions = this.FactionRepository.findAll();

		return Factions;
	}

	public void delete(final Faction Faction) {
		Assert.notNull(Faction);

		this.FactionRepository.delete(Faction);
	}
	public Faction reconstruct(final Faction s, final BindingResult binding) {
		Faction result;

		if (s.getId() == 0)
			result = s;
		else {
			result = this.findOne(s.getId());
			result.setExtraAttack(s.getExtraAttack());
			result.setExtraDefense(s.getExtraDefense());
			result.setExtraResources(s.getExtraResources());
			result.setName(s.getName());

		}

		this.validator.validate(result, binding);

		return result;
	}
}

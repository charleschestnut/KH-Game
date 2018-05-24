
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FactionRepository;
import domain.Faction;

@Service
@Transactional
public class FactionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FactionRepository	FactionRepository;


	// CRUD methods

	public Faction create() {
		Faction Faction;

		Faction = new Faction();

		return Faction;
	}

	public Faction save(Faction Faction) {
		Assert.notNull(Faction);

		Faction saved;

		saved = this.FactionRepository.save(Faction);

		return saved;
	}

	public Faction findOne(int FactionId) {
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

	public void delete(Faction Faction) {
		Assert.notNull(Faction);

		this.FactionRepository.delete(Faction);
	}

}

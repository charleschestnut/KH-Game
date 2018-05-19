
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TroopRepository;
import domain.Materials;
import domain.Recruiter;
import domain.Troop;

@Service
@Transactional
public class TroopService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TroopRepository	TroopRepository;

	@Autowired
	private BuiltService	builtService;


	// CRUD methods

	public Troop create(final Recruiter recruiter) {
		Troop troop;
		troop = new Troop();
		final Materials cost = new Materials();

		troop.setAttack(0);
		troop.setDefense(0);
		troop.setName("");
		troop.setTimeToRecruit(0);
		troop.setRecruiter(recruiter);
		troop.setRecruiterRequiredLvl(0);

		cost.setGummiCoal(0);
		cost.setMunny(0);
		cost.setMytrhil(0);

		troop.setCost(cost);

		return troop;
	}

	public Troop save(final Troop troop) {

		final Troop saved = this.TroopRepository.save(troop);

		return saved;
	}

	public Troop findOne(final int TroopId) {
		Assert.notNull(TroopId);

		Troop Troop;

		Troop = this.TroopRepository.findOne(TroopId);

		return Troop;
	}

	public Collection<Troop> findAll() {
		Collection<Troop> Troops;

		Troops = this.TroopRepository.findAll();

		return Troops;
	}

	public void delete(final Troop Troop) {
		Assert.notNull(Troop);

		this.TroopRepository.delete(Troop);
	}

	//Other methods

	public Collection<Troop> getTroopsFromRecruiter(final Integer recruiterId) {
		return this.TroopRepository.getTroopsFromRecruiter(recruiterId);
	}

	public Collection<Troop> getStoragedTroops(final Integer builtId) {
		return this.TroopRepository.getStoragedTroops(builtId);
	}

}

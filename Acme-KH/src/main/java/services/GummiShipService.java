
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GummiShipRepository;
import domain.GummiShip;
import domain.Materials;
import domain.Recruiter;

@Service
@Transactional
public class GummiShipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GummiShipRepository	GummiShipRepository;


	// CRUD methods

	public GummiShip create(final Recruiter recruiter) {
		GummiShip gummiShip;
		final Materials cost = new Materials();

		gummiShip = new GummiShip();
		gummiShip.setSlots(0);
		gummiShip.setName("");
		gummiShip.setTimeToRecruit(0);
		gummiShip.setRecruiterRequiredLvl(0);
		gummiShip.setRecruiter(recruiter);

		cost.setGummiCoal(0);
		cost.setMunny(0);
		cost.setMytrhil(0);

		gummiShip.setCost(cost);

		return gummiShip;
	}

	public GummiShip save(final GummiShip GummiShip) {
		Assert.notNull(GummiShip);

		GummiShip saved;

		saved = this.GummiShipRepository.save(GummiShip);

		return saved;
	}

	public GummiShip findOne(final int GummiShipId) {
		Assert.notNull(GummiShipId);

		GummiShip GummiShip;

		GummiShip = this.GummiShipRepository.findOne(GummiShipId);

		return GummiShip;
	}

	public Collection<GummiShip> findAll() {
		Collection<GummiShip> GummiShips;

		GummiShips = this.GummiShipRepository.findAll();

		return GummiShips;
	}

	public void delete(final GummiShip GummiShip) {
		Assert.notNull(GummiShip);

		this.GummiShipRepository.delete(GummiShip);
	}
	//Other methods

	public Collection<GummiShip> getGummiShipFromRecruiter(final Integer recruiterId) {
		return this.GummiShipRepository.getGummiShipsFromRecruiter(recruiterId);
	}

}

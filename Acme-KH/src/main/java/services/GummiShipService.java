
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.GummiShipRepository;
import domain.GummiShip;
import domain.Materials;
import domain.Recruiter;
import domain.Troop;

@Service
@Transactional
public class GummiShipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GummiShipRepository	gummiShipRepository;
	
	@Autowired
	private Validator validator;


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

		saved = this.gummiShipRepository.save(GummiShip);

		return saved;
	}

	public GummiShip findOne(final int GummiShipId) {
		Assert.notNull(GummiShipId);

		GummiShip GummiShip;

		GummiShip = this.gummiShipRepository.findOne(GummiShipId);

		return GummiShip;
	}

	public Collection<GummiShip> findAll() {
		Collection<GummiShip> GummiShips;

		GummiShips = this.gummiShipRepository.findAll();

		return GummiShips;
	}

	public void delete(final GummiShip GummiShip) {
		Assert.notNull(GummiShip);

		this.gummiShipRepository.delete(GummiShip);
	}
	//Other methods

	public Collection<GummiShip> getGummiShipFromRecruiter(final Integer recruiterId) {
		return this.gummiShipRepository.getGummiShipsFromRecruiter(recruiterId);
	}
	
	public Collection<GummiShip> getStoragedGummiShip(final Integer builtId) {
		return this.gummiShipRepository.getStoragedGummiShip(builtId);
	}
	
	public Collection<GummiShip> getGummiShipsAvailableForBuilt(Integer builtLevel){
		return this.gummiShipRepository.getGummiShipsAvailableForBuilt(builtLevel);
	}
	
	// ------ RECONSTRUCT -------
	public GummiShip reconstruct(GummiShip g, BindingResult binding) {
		GummiShip result;
		final GummiShip original = this.gummiShipRepository.findOne(g.getId());
		
		if (g.getId() == 0) {
			result = g;
		} else {
			//Aquí van los atributos hidden
			result = g;
			result.setRecruiter(original.getRecruiter());
		}
		this.validator.validate(result, binding);

		return result;

	}
	
}

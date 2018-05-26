
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	Validator				validator;


	// CRUD methods

	public Troop create(Recruiter recruiter) {
		Troop troop;
		troop = new Troop();
		Materials cost = new Materials();

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

	public Troop save(Troop troop) {

		Troop saved = this.TroopRepository.save(troop);

		return saved;
	}

	public Troop findOne(int TroopId) {
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

	public void delete(Troop Troop) {
		Assert.notNull(Troop);

		this.TroopRepository.delete(Troop);
	}

	//Other methods

	public Collection<Troop> getTroopsFromRecruiter(Integer recruiterId) {
		return this.TroopRepository.getTroopsFromRecruiter(recruiterId);
	}

	public Collection<Troop> getStoragedTroops(Integer builtId) {
		return this.TroopRepository.getStoragedTroops(builtId);
	}

	public Collection<Troop> getTroopsAvailableForBuilt(Integer builtLevel) {
		return this.TroopRepository.getTroopsAvailableForBuilt(builtLevel);
	}

	public Collection<Troop> getTroopsAvailableFromRecruiterAndLvl(Integer recruiterId, Integer lvl) {
		return this.TroopRepository.getTroopsAvailableFromRecruiterAndLvl(recruiterId, lvl);
	}

	// ------ RECONSTRUCT -----
	public Troop reconstruct(Troop t, BindingResult binding) {
		Troop result;
		Troop original = this.TroopRepository.findOne(t.getId());

		if (t.getId() == 0)
			result = t;
		else {
			//Aquí van los atributos hidden
			result = t;
			result.setRecruiter(original.getRecruiter());

		}
		this.validator.validate(result, binding);

		return result;

	}
	
	public Troop getTroopsFromRecruiter(String name){
		return this.TroopRepository.getTroopByName(name);
	}
	
	public Collection<String> getTroopsNames(){
		return this.TroopRepository.getTroopsNames();
	}
	
	public Troop getTroopByName(String name){
		return this.TroopRepository.getTroopByName(name);
	}

}

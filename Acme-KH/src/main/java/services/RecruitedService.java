
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecruitedRepository;
import domain.Built;
import domain.GummiShip;
import domain.Recruited;
import domain.Troop;

@Service
@Transactional
public class RecruitedService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecruitedRepository	RecruitedRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private TroopService		troopService;

	@Autowired
	private GummiShipService	gummiShipService;

	@Autowired
	private BuiltService		builtService;


	// CRUD methods

	public Recruited createRecruitedTroop(final int troopId) {
		Recruited recruited;
		recruited = new Recruited();
		final Troop troop = this.troopService.findOne(troopId);

		final List<Built> availables = (List<Built>) this.builtService.getMyFreeWarehousesTroop();
		Assert.isTrue(availables.size() > 0);
		recruited.setStorageBuilding(availables.get(0));
		recruited.setTroop(troop);

		return recruited;
	}

	public Recruited createRecruitedGummiShip(final int gummiShipId) {
		Recruited recruited;
		recruited = new Recruited();
		final GummiShip gummiShip = this.gummiShipService.findOne(gummiShipId);

		final List<Built> availables = (List<Built>) this.builtService.getMyFreeWarehousesGummi();
		Assert.isTrue(availables.size() > 0);
		recruited.setStorageBuilding(availables.get(0));
		recruited.setGummiShip(gummiShip);

		return recruited;
	}

	public Recruited save(final Recruited recruited) {
		Assert.notNull(recruited);
		Assert.notNull(recruited.getStorageBuilding());
		Assert.isTrue(!(recruited.getGummiShip() == null && recruited.getTroop() == null));

		if (recruited.getGummiShip() == null)	// Miramos que el nivel sea el permitido o superior.
			Assert.isTrue(recruited.getGummiShip().getRecruiterRequiredLvl() >= recruited.getStorageBuilding().getLvl(), "error.message.recruited.lowLevel");

		else
			// Miramos que el nivel sea el permitido o superior.
			Assert.isTrue(recruited.getTroop().getRecruiterRequiredLvl() >= recruited.getStorageBuilding().getLvl(), "error.message.recruited.lowLevel");

		// Tenemos que ver que el BUILT pertenece al principal.
		final List<Built> availables = (List<Built>) this.builtService.getMyFreeWarehousesGummi();
		Assert.isTrue(availables.contains(recruited.getStorageBuilding()) && availables.size() > 0, "error.message.recruited.built");

		//Tenemos que ver que el edificio tiene espacio para guardarlo.

		Recruited saved;
		saved = this.RecruitedRepository.save(recruited);

		return saved;
	}

	public Recruited findOne(final int RecruitedId) {
		Assert.notNull(RecruitedId);

		Recruited Recruited;

		Recruited = this.RecruitedRepository.findOne(RecruitedId);

		return Recruited;
	}

	public Collection<Recruited> findAll() {
		Collection<Recruited> Recruiteds;

		Recruiteds = this.RecruitedRepository.findAll();

		return Recruiteds;
	}

	public void delete(final Recruited Recruited) {
		Assert.notNull(Recruited);

		this.RecruitedRepository.delete(Recruited);
	}

	// OTHER METHODS

	public Collection<Recruited> getMyStoragedRecruitedTroops(final Integer builtId) {
		return this.RecruitedRepository.getMyStoragedRecruitedTroops(builtId);
	}

	public Collection<Recruited> getMyStoragedRecruitedGummiShip(final Integer builtId) {
		return this.RecruitedRepository.getMyStoragedRecruitedGummiShip(builtId);
	}

	public Collection<Recruited> getMyRecruited(final Integer builtId) {
		return this.RecruitedRepository.getMyRecruited(builtId);
	}

}

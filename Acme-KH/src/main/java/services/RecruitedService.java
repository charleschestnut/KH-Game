
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecruitedRepository;
import domain.Built;
import domain.Recruited;

@Service
@Transactional
public class RecruitedService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecruitedRepository	RecruitedRepository;

	@Autowired
	private BuiltService		builtService;


	// CRUD methods

	public Recruited create(Built built) {
		Recruited recruited;
		recruited = new Recruited();

		if (built.getTroop() != null)
			recruited.setTroop(built.getTroop());
		else
			recruited.setGummiShip(built.getGummiShip());

		return recruited;
	}

	public Recruited save(Built built) {
		List<Built> warehouses;
		Recruited recruited = this.create(built);

		Assert.notNull(built);
		Assert.isTrue(!(built.getGummiShip() == null && built.getTroop() == null));
		Assert.isTrue(!(built.getGummiShip() != null && built.getTroop() != null));

		//Voy a reclutar una tropa
		if (built.getTroop() != null) {
			warehouses = new ArrayList<Built>(this.builtService.getMyFreeWarehousesTroop());

			Assert.isTrue(warehouses != null && warehouses.size() > 0, "error.message.recruited.warehouses");

			recruited.setStorageBuilding(warehouses.get(0));

		} else { //Voy a reclutar una nave

			warehouses = new ArrayList<Built>(this.builtService.getMyFreeWarehousesGummi());

			Assert.isTrue(warehouses != null && warehouses.size() > 0, "error.message.recruited.warehouses");

			recruited.setStorageBuilding(warehouses.get(0));
		}

		Recruited saved;
		saved = this.RecruitedRepository.save(recruited);

		return saved;
	}
	public Recruited findOne(int RecruitedId) {
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

	public void delete(Recruited Recruited) {
		Assert.notNull(Recruited);

		this.RecruitedRepository.delete(Recruited);
	}

	// OTHER METHODS

	public Collection<Recruited> getMyStoragedRecruitedTroops(Integer builtId) {
		return this.RecruitedRepository.getMyStoragedRecruitedTroops(builtId);
	}

	public Collection<Recruited> getMyStoragedRecruitedGummiShip(Integer builtId) {
		return this.RecruitedRepository.getMyStoragedRecruitedGummiShip(builtId);
	}

	public Collection<Recruited> getMyRecruited(Integer builtId) {
		return this.RecruitedRepository.getMyRecruited(builtId);
	}

}


package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BuiltRepository;
import domain.Building;
import domain.Built;
import domain.KeybladeWielder;
import domain.Materials;

@Service
@Transactional
public class BuiltService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BuiltRepository			BuiltRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private RequirementService		requirementService;


	// CRUD methods

	//TODO:metodos collect o recruit, start to collect or start to recruit

	public Built create(final Building b) {
		Built built;

		built = new Built();
		built.setLvl(1);
		built.setBuilding(b);
		built.setKeybladeWielder((KeybladeWielder) this.actorService.findByPrincipal());
		built.setCreationDate(new Date(System.currentTimeMillis() - 1000));

		return built;
	}

	public Built save(final Built built) {
		final KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();

		Assert.notNull(built);
		Assert.isTrue(this.getMyBuilts().size() < this.configurationService.getConfiguration().getWorldSlots(), "error.message.built.slots");
		Assert.isTrue(this.requirementService.fulfillsRequirements(built.getBuilding().getId()), "error.message.built.requirements");
		Assert.isTrue(player.getMaterials().isHigherThan(built.getBuilding().getCost()), "error.message.built.materials");
		Assert.isTrue(built.getKeybladeWielder().equals(player), "error.message.built.creator");

		Built saved;

		saved = this.BuiltRepository.save(built);

		return saved;
	}
	public Built findOne(final int BuiltId) {
		Assert.notNull(BuiltId);

		Built Built;

		Built = this.BuiltRepository.findOne(BuiltId);

		return Built;
	}

	public Collection<Built> findAll() {
		Collection<Built> Builts;

		Builts = this.BuiltRepository.findAll();

		return Builts;
	}

	public void delete(final Built built) {
		Assert.notNull(built);
		Assert.isTrue(built.getKeybladeWielder().equals(this.actorService.findByPrincipal()), "error.message.built.creator");

		this.BuiltRepository.delete(built);
	}

	//other methods
	public Collection<Built> getMyBuilts() {
		return this.BuiltRepository.getMyBuildings(this.actorService.findByPrincipal().getId());
	}
	public Integer getMyDefenseByBuildings() {
		return this.BuiltRepository.myDefenseByBuildings(this.actorService.findByPrincipal().getId());
	}
	public Collection<Built> getMyFreeWarehousesTroop() {
		return this.BuiltRepository.getMyFreeWarehousesTroop(this.actorService.findByPrincipal().getId());
	}
	public Collection<Built> getMyFreeWarehousesGummi() {
		return this.BuiltRepository.getMyFreeWarehousesGummi(this.actorService.findByPrincipal().getId());
	}
	public Materials extraMaterials() {
		final Integer playerId = this.actorService.findByPrincipal().getId();

		final Integer munny = this.BuiltRepository.getExtraMunny(playerId);
		final Integer coal = this.BuiltRepository.getExtraGummiCoal(playerId);
		final Integer mytrhil = this.BuiltRepository.getExtraMythril(playerId);

		final Materials materials = new Materials();
		materials.setMunny(munny);
		materials.setMytrhil(mytrhil);
		materials.setGummiCoal(coal);

		return materials;
	}
}

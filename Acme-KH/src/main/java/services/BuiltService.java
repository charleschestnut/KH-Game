
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
import domain.GummiShip;
import domain.KeybladeWielder;
import domain.Livelihood;
import domain.Materials;
import domain.Prize;
import domain.Recruited;
import domain.Recruiter;
import domain.Troop;
import domain.Warehouse;

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
	@Autowired
	private LivelihoodService		livelihoodService;
	@Autowired
	private WarehouseService		warehouseService;
	@Autowired
	private RecruiterService		recruiterService;
	@Autowired
	private RecruitedService		recruitedService;
	@Autowired
	private PrizeService			prizeService;
	@Autowired
	private KeybladeWielderService	keybladeWielderService;


	// CRUD methods

	public Built create(Building b) {
		Built built;

		built = new Built();
		built.setLvl(0);
		built.setBuilding(b);
		built.setKeybladeWielder((KeybladeWielder) this.actorService.findByPrincipal());
		built.setCreationDate(new Date(System.currentTimeMillis() - 1000));

		return built;
	}

	public Built save(Built built) {
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();

		Assert.notNull(built);
		Assert.isTrue(this.getMyBuilts().size() < this.configurationService.getConfiguration().getWorldSlots(), "error.message.built.slots");
		Assert.isTrue(this.requirementService.fulfillsRequirements(built.getBuilding().getId()), "error.message.built.requirements");
		Assert.isTrue(player.getMaterials().isHigherThan(built.getBuilding().getCost()), "error.message.built.materials");
		Assert.isTrue(built.getKeybladeWielder().equals(player), "error.message.built.creator");

		Built saved;

		saved = this.BuiltRepository.save(built);

		Materials myOldMaterials = player.getMaterials();
		Materials myNewMaterials = myOldMaterials.substract(built.getBuilding().getCost());

		player.setMaterials(myNewMaterials);
		this.keybladeWielderService.save(player);

		return saved;
	}
	public void upgrade(Built built) {
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		Date today = new Date(System.currentTimeMillis() - 100);

		Assert.notNull(built);
		Assert.isTrue(built.getKeybladeWielder().equals(player), "error.message.built.creator");
		Assert.isTrue(built.getLvl() < built.getBuilding().getMaxLvl(), "error.message.built.maxLvL");

		if (built.getLvl() == 0) {

			Long time1 = today.getTime() - built.getCreationDate().getTime();
			Long time2 = (long) (built.getBuilding().getTimeToConstruct() * 60 * 1000);

			Assert.isTrue(time1 >= time2, "error.message.built.working");
		} else {

			Materials myOldMaterials = player.getMaterials();

			Assert.isTrue(myOldMaterials.isHigherThan(built.getBuilding().getTotalMaterials(built.getLvl())), "error.message.built.materials");

			Materials myNewMaterials = myOldMaterials.substract(built.getBuilding().getTotalMaterials(built.getLvl()));

			player.setMaterials(myNewMaterials);
			this.keybladeWielderService.save(player);

		}
		built.setLvl(built.getLvl() + 1);
		this.BuiltRepository.save(built);
	}
	public Built findOne(int BuiltId) {
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

	public void delete(Built built) {
		Assert.notNull(built);
		Assert.isTrue(built.getKeybladeWielder().equals(this.actorService.findByPrincipal()), "error.message.built.creator");

		Warehouse w = this.warehouseService.findOne(built.getBuilding().getId());

		if (w != null) {
			Collection<Recruited> recruiteds = this.recruitedService.getMyRecruited(built.getId());
			for (Recruited r : recruiteds)
				this.recruitedService.delete(r);

			KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
			Materials extra = this.extraMaterials();
			Materials base = this.configurationService.getConfiguration().getBaseMaterials();
			Materials esteWarehouse = w.getTotalSlotsMaterials(built.getLvl());
			Materials tenia = player.getMaterials();
			Materials total = base.add(extra);
			total = total.substract(esteWarehouse);
			Materials tengo = tenia.removeExcess(total);

			if (!tengo.equals(tenia)) {
				player.setMaterials(tengo);
				this.keybladeWielderService.save(player);
			}

		}

		this.BuiltRepository.delete(built);
	}
	public void startToCollect(Built b) {
		Livelihood l = this.livelihoodService.findOne(b.getBuilding().getId());

		Assert.notNull(l, "error.message.built.noBuilding");
		Assert.isTrue(b.getKeybladeWielder().equals(this.actorService.findByPrincipal()), "error.message.built.creator");
		Assert.isTrue(b.getLvl() > 0, "error.message.built.unbuilt");
		Assert.isNull(b.getActivationDate(), "error.message.built.alreadyInUse");

		b.setActivationDate(new Date(System.currentTimeMillis() - 2000));

		this.BuiltRepository.save(b);
	}
	public void startToRecruitTroop(Built b, Troop t) {
		Recruiter r = this.recruiterService.findOne(b.getBuilding().getId());
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();

		Assert.notNull(r, "error.message.built.noBuilding");
		Assert.notNull(t, "error.message.built.noTroop");
		Assert.isNull(b.getActivationDate(), "error.message.built.alreadyInUse");
		Assert.isTrue(b.getKeybladeWielder().equals(this.actorService.findByPrincipal()), "error.message.built.creator");
		Assert.isTrue(t.getRecruiter().equals(b.getBuilding()), "error.message.built.recruit");
		Assert.isTrue(b.getLvl() > 0, "error.message.built.unbuilt");
		Assert.isTrue(player.getMaterials().isHigherThan(t.getCost()), "error.message.built.materials");

		b.setActivationDate(new Date(System.currentTimeMillis()));
		b.setTroop(t);

		Materials myOldMaterials = player.getMaterials();
		Materials myNewMaterials = myOldMaterials.substract(t.getCost());

		player.setMaterials(myNewMaterials);
		this.keybladeWielderService.save(player);

		this.BuiltRepository.save(b);
	}

	public void startToRecruitGummiShip(Built b, GummiShip g) {
		Recruiter r = this.recruiterService.findOne(b.getBuilding().getId());
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();

		Assert.notNull(r, "error.message.built.noBuilding");
		Assert.notNull(g, "error.message.built.noShip");
		Assert.isNull(b.getActivationDate(), "error.message.built.alreadyInUse");
		Assert.isTrue(b.getKeybladeWielder().equals(this.actorService.findByPrincipal()), "error.message.built.creator");
		Assert.isTrue(g.getRecruiter().equals(b.getBuilding()), "error.message.built.recruit");
		Assert.isTrue(b.getLvl() > 0, "error.message.built.unbuilt");
		Assert.isTrue(player.getMaterials().isHigherThan(g.getCost()), "error.message.built.materials");

		b.setActivationDate(new Date(System.currentTimeMillis()));
		b.setGummiShip(g);

		Materials myOldMaterials = player.getMaterials();
		Materials myNewMaterials = myOldMaterials.substract(g.getCost());

		player.setMaterials(myNewMaterials);
		this.keybladeWielderService.save(player);

		this.BuiltRepository.save(b);
	}

	public void collect(Built b) {
		Livelihood l = this.livelihoodService.findOne(b.getBuilding().getId());
		Long days = (long) 2592000;
		Date expiration = new Date(System.currentTimeMillis() + days * 1000);
		Date today = new Date(System.currentTimeMillis() - 1000);
		Long time1 = today.getTime() - b.getActivationDate().getTime();
		Long time2 = (long) (l.getTotalTime(b.getLvl()) * 60 * 1000);

		Assert.notNull(l, "error.message.built.noBuilding");
		Assert.isTrue(b.getKeybladeWielder().equals(this.actorService.findByPrincipal()), "error.message.built.creator");
		Assert.notNull(b.getActivationDate(), "error.message.built.notInUse");
		Assert.isTrue(time1 >= time2, "error.message.built.working");

		Prize p = new Prize();
		p.setDate(expiration);
		p.setDescription("built.prize.defaultDescription");
		p.setKeybladeWielder((KeybladeWielder) this.actorService.findByPrincipal());
		p.setMaterials(l.getTotalCollectMaterials(b.getLvl()));

		this.prizeService.save(p);

		b.setActivationDate(null);
		this.BuiltRepository.save(b);

	}
	public Recruited recruit(Built b) {
		Recruited res;
		Recruiter r = this.recruiterService.findOne(b.getBuilding().getId());
		Date today = new Date(System.currentTimeMillis() - 1000);
		Long time1 = today.getTime() - b.getActivationDate().getTime();
		Long time2;
		if (b.getTroop() != null)
			time2 = (long) (b.getTroop().getTimeToRecruit() * 60 * 1000);
		else
			time2 = (long) (b.getGummiShip().getTimeToRecruit() * 60 * 1000);

		Assert.notNull(r, "error.message.built.noBuilding");
		Assert.isTrue(b.getKeybladeWielder().equals(this.actorService.findByPrincipal()), "error.message.built.creator");
		Assert.notNull(b.getActivationDate(), "error.message.built.notInUse");
		Assert.isTrue(time1 >= time2, "error.message.built.working");

		res = this.recruitedService.save(b);

		b.setActivationDate(null);
		this.BuiltRepository.save(b);

		return res;

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
		Integer playerId = this.actorService.findByPrincipal().getId();

		Integer munny = this.BuiltRepository.getExtraMunny(playerId);
		Integer coal = this.BuiltRepository.getExtraGummiCoal(playerId);
		Integer mytrhil = this.BuiltRepository.getExtraMythril(playerId);

		if (mytrhil == null || mytrhil < 0)
			mytrhil = 0;

		if (munny == null || munny < 0)
			munny = 0;

		if (coal == null || coal < 0)
			coal = 0;

		Materials materials = new Materials();
		materials.setMunny(munny);
		materials.setMytrhil(mytrhil);
		materials.setGummiCoal(coal);

		return materials;
	}
	public Materials maxMaterials() {
		Materials base = this.configurationService.getConfiguration().getBaseMaterials();
		Materials res = this.extraMaterials().add(base);

		return res;

	}
	public Integer getDefenseByBuildings(Integer actorId) {
		return this.BuiltRepository.myDefenseByBuildings(actorId);
	}
}

package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecruitedRepository;
import domain.Built;
import domain.GummiShip;
import domain.KeybladeWielder;
import domain.Recruited;
import domain.Recruiter;
import domain.Troop;

@Service
@Transactional
public class RecruitedService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecruitedRepository RecruitedRepository;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private TroopService troopService;
	
	@Autowired
	private GummiShipService gummiShipService;
	
	@Autowired
	private BuiltService builtService;

	// CRUD methods
	
	public Recruited createRecruitedTroop(int troopId, int recruiterId){
		Recruited recruited;
		recruited = new Recruited();
		Troop troop = this.troopService.findOne(troopId);
		
		Built building = this.builtService.findOne(recruiterId);
		recruited.setStorageBuilding(building);
		recruited.setTroop(troop);
		
		return recruited;
	}
	
	public Recruited createRecruitedGummiShip(int gummiShipId, int recruiterId){
		Recruited recruited;
		recruited = new Recruited();
		GummiShip gummiShip= this.gummiShipService.findOne(gummiShipId);
		
		Built building = this.builtService.findOne(recruiterId);
		recruited.setStorageBuilding(building);
		recruited.setGummiShip(gummiShip);
		
		return recruited;
	}
	
	
	
	public Recruited save(Recruited recruited){
		Boolean ambosNull = false;
		Assert.notNull(recruited);
		Assert.notNull(recruited.getStorageBuilding());
		
		Assert.isTrue(!(recruited.getGummiShip() == null && recruited.getTroop() == null));
		
		
		if(recruited.getGummiShip() == null )	// Miramos que el nivel sea el permitido o superior.
			Assert.isTrue(recruited.getGummiShip().getRecruiterRequiredLvl() >= recruited.getStorageBuilding().getLvl());
		
		else	// Miramos que el nivel sea el permitido o superior.
			Assert.isTrue(recruited.getTroop().getRecruiterRequiredLvl() >= recruited.getStorageBuilding().getLvl());
		
		
		// Tenemos que ver que el BUILT pertenece al principal.
		Collection<Built> builts = this.builtService.getMyBuilts();
		Assert.isTrue(builts.contains(recruited.getStorageBuilding()));
		
		Recruited saved;
		saved = RecruitedRepository.save(recruited);
		
		return saved;
	}
	
	public Recruited findOne(int RecruitedId){
		Assert.notNull(RecruitedId);
		
		Recruited Recruited;
		
		Recruited = RecruitedRepository.findOne(RecruitedId);
		
		return Recruited;
	}
	
	public Collection<Recruited> findAll(){
		Collection<Recruited> Recruiteds;
		
		Recruiteds = RecruitedRepository.findAll();
		
		return Recruiteds;
	}
	
	public void delete(Recruited Recruited){
		Assert.notNull(Recruited);
		
		RecruitedRepository.delete(Recruited);
	}

}

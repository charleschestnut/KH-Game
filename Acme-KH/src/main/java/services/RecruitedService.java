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
	
	public Recruited createRecruitedTroop(int troopId){
		Recruited recruited;
		recruited = new Recruited();
		Troop troop = this.troopService.findOne(troopId);
		
		List<Built> availables = (List<Built>) this.builtService.getMyFreeWarehousesTroop();
		Assert.isTrue(availables.size()>0);
		recruited.setStorageBuilding(availables.get(0));
		recruited.setTroop(troop);
		
		return recruited;
	}
	
	public Recruited createRecruitedGummiShip(int gummiShipId){
		Recruited recruited;
		recruited = new Recruited();
		GummiShip gummiShip= this.gummiShipService.findOne(gummiShipId);
		
		List<Built> availables = (List<Built>) this.builtService.getMyFreeWarehousesGummi();
		Assert.isTrue(availables.size()>0);
		recruited.setStorageBuilding(availables.get(0));
		recruited.setGummiShip(gummiShip);
		
		return recruited;
	}
	
	
	
	public Recruited save(Recruited recruited){
		Assert.notNull(recruited);
		Assert.notNull(recruited.getStorageBuilding());
		Assert.isTrue(!(recruited.getGummiShip() == null && recruited.getTroop() == null));
		
		
		if(recruited.getGummiShip() == null )	// Miramos que el nivel sea el permitido o superior.
			Assert.isTrue(recruited.getGummiShip().getRecruiterRequiredLvl() >= recruited.getStorageBuilding().getLvl(), "error.message.recruited.lowLevel");
		
		else	// Miramos que el nivel sea el permitido o superior.
			Assert.isTrue(recruited.getTroop().getRecruiterRequiredLvl() >= recruited.getStorageBuilding().getLvl(), "error.message.recruited.lowLevel");
		
		
		// Tenemos que ver que el BUILT pertenece al principal.
		List<Built> availables = (List<Built>) this.builtService.getMyFreeWarehousesGummi();
		Assert.isTrue(availables.contains(recruited.getStorageBuilding()) && availables.size()>0, "error.message.recruited.built");
		
		//Tenemos que ver que el edificio tiene espacio para guardarlo.
		
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

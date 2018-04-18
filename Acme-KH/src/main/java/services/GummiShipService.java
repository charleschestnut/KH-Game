package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GummiShipRepository;
import domain.GummiShip;

@Service
@Transactional
public class GummiShipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GummiShipRepository GummiShipRepository;

	// CRUD methods
	
	public GummiShip create(){
		GummiShip GummiShip;
		
		GummiShip = new GummiShip();
		
		return GummiShip;
	}
	
	public GummiShip save(GummiShip GummiShip){
		Assert.notNull(GummiShip);
		
		GummiShip saved;
		
		saved = GummiShipRepository.save(GummiShip);
		
		return saved;
	}
	
	public GummiShip findOne(int GummiShipId){
		Assert.notNull(GummiShipId);
		
		GummiShip GummiShip;
		
		GummiShip = GummiShipRepository.findOne(GummiShipId);
		
		return GummiShip;
	}
	
	public Collection<GummiShip> findAll(){
		Collection<GummiShip> GummiShips;
		
		GummiShips = GummiShipRepository.findAll();
		
		return GummiShips;
	}
	
	public void delete(GummiShip GummiShip){
		Assert.notNull(GummiShip);
		
		GummiShipRepository.delete(GummiShip);
	}

}
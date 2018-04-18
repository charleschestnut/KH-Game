package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BuildingRepository;
import domain.Building;

@Service
@Transactional
public class BuildingService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BuildingRepository BuildingRepository;

	// CRUD methods
	
	public Building create(){
		Building Building;
		
		Building = new Building();
		
		return Building;
	}
	
	public Building save(Building Building){
		Assert.notNull(Building);
		
		Building saved;
		
		saved = BuildingRepository.save(Building);
		
		return saved;
	}
	
	public Building findOne(int BuildingId){
		Assert.notNull(BuildingId);
		
		Building Building;
		
		Building = BuildingRepository.findOne(BuildingId);
		
		return Building;
	}
	
	public Collection<Building> findAll(){
		Collection<Building> Buildings;
		
		Buildings = BuildingRepository.findAll();
		
		return Buildings;
	}
	
	public void delete(Building Building){
		Assert.notNull(Building);
		
		BuildingRepository.delete(Building);
	}

}
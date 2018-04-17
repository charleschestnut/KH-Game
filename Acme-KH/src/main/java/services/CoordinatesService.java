package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CoordinatesRepository;
import domain.Coordinates;

@Service
@Transactional
public class CoordinatesService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CoordinatesRepository CoordinatesRepository;

	// CRUD methods
	
	public Coordinates create(){
		Coordinates Coordinates;
		
		Coordinates = new Coordinates();
		
		return Coordinates;
	}
	
	public Coordinates save(Coordinates Coordinates){
		Assert.notNull(Coordinates);
		
		Coordinates saved;
		
		saved = CoordinatesRepository.save(Coordinates);
		
		return saved;
	}
	
	public Coordinates findOne(int CoordinatesId){
		Assert.notNull(CoordinatesId);
		
		Coordinates Coordinates;
		
		Coordinates = CoordinatesRepository.findOne(CoordinatesId);
		
		return Coordinates;
	}
	
	public Collection<Coordinates> findAll(){
		Collection<Coordinates> Coordinatess;
		
		Coordinatess = CoordinatesRepository.findAll();
		
		return Coordinatess;
	}
	
	public void delete(Coordinates Coordinates){
		Assert.notNull(Coordinates);
		
		CoordinatesRepository.delete(Coordinates);
	}

}
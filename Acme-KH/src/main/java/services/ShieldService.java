package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShieldRepository;
import domain.Shield;

@Service
@Transactional
public class ShieldService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShieldRepository ShieldRepository;

	// CRUD methods
	
	public Shield create(){
		Shield Shield;
		
		Shield = new Shield();
		
		return Shield;
	}
	
	public Shield save(Shield Shield){
		Assert.notNull(Shield);
		
		Shield saved;
		
		saved = ShieldRepository.save(Shield);
		
		return saved;
	}
	
	public Shield findOne(int ShieldId){
		Assert.notNull(ShieldId);
		
		Shield Shield;
		
		Shield = ShieldRepository.findOne(ShieldId);
		
		return Shield;
	}
	
	public Collection<Shield> findAll(){
		Collection<Shield> Shields;
		
		Shields = ShieldRepository.findAll();
		
		return Shields;
	}
	
	public void delete(Shield Shield){
		Assert.notNull(Shield);
		
		ShieldRepository.delete(Shield);
	}

}
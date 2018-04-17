package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannedRepository;
import domain.Banned;

@Service
@Transactional
public class BannedService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BannedRepository bannedRepository;

	// CRUD methods
	
	public Banned create(){
		Banned banned;
		
		banned = new Banned();
		
		return banned;
	}
	
	public Banned save(Banned banned){
		Assert.notNull(banned);
		
		Banned saved;
		
		saved = bannedRepository.save(banned);
		
		return saved;
	}
	
	public Banned findOne(int bannedId){
		Assert.notNull(bannedId);
		
		Banned banned;
		
		banned = bannedRepository.findOne(bannedId);
		
		return banned;
	}
	
	public Collection<Banned> findAll(){
		Collection<Banned> banneds;
		
		banneds = bannedRepository.findAll();
		
		return banneds;
	}
	
	public void delete(Banned banned){
		Assert.notNull(banned);
		
		bannedRepository.delete(banned);
	}

}
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.KeybladeWielderRepository;
import domain.KeybladeWielder;

@Service
@Transactional
public class KeybladeWielderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private KeybladeWielderRepository KeybladeWielderRepository;

	// CRUD methods
	
	public KeybladeWielder create(){
		KeybladeWielder KeybladeWielder;
		
		KeybladeWielder = new KeybladeWielder();
		
		return KeybladeWielder;
	}
	
	public KeybladeWielder save(KeybladeWielder KeybladeWielder){
		Assert.notNull(KeybladeWielder);
		
		KeybladeWielder saved;
		
		saved = KeybladeWielderRepository.save(KeybladeWielder);
		
		return saved;
	}
	
	public KeybladeWielder findOne(int KeybladeWielderId){
		Assert.notNull(KeybladeWielderId);
		
		KeybladeWielder KeybladeWielder;
		
		KeybladeWielder = KeybladeWielderRepository.findOne(KeybladeWielderId);
		
		return KeybladeWielder;
	}
	
	public Collection<KeybladeWielder> findAll(){
		Collection<KeybladeWielder> KeybladeWielders;
		
		KeybladeWielders = KeybladeWielderRepository.findAll();
		
		return KeybladeWielders;
	}
	
	public void delete(KeybladeWielder KeybladeWielder){
		Assert.notNull(KeybladeWielder);
		
		KeybladeWielderRepository.delete(KeybladeWielder);
	}

}
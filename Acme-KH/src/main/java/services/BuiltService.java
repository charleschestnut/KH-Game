package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BuiltRepository;
import domain.Built;

@Service
@Transactional
public class BuiltService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BuiltRepository BuiltRepository;

	// CRUD methods
	
	public Built create(){
		Built Built;
		
		Built = new Built();
		
		return Built;
	}
	
	public Built save(Built Built){
		Assert.notNull(Built);
		
		Built saved;
		
		saved = BuiltRepository.save(Built);
		
		return saved;
	}
	
	public Built findOne(int BuiltId){
		Assert.notNull(BuiltId);
		
		Built Built;
		
		Built = BuiltRepository.findOne(BuiltId);
		
		return Built;
	}
	
	public Collection<Built> findAll(){
		Collection<Built> Builts;
		
		Builts = BuiltRepository.findAll();
		
		return Builts;
	}
	
	public void delete(Built Built){
		Assert.notNull(Built);
		
		BuiltRepository.delete(Built);
	}

}
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MaterialsRepository;
import domain.Materials;

@Service
@Transactional
public class MaterialsService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MaterialsRepository MaterialsRepository;

	// CRUD methods
	
	public Materials create(){
		Materials Materials;
		
		Materials = new Materials();
		
		return Materials;
	}
	
	public Materials save(Materials Materials){
		Assert.notNull(Materials);
		
		Materials saved;
		
		saved = MaterialsRepository.save(Materials);
		
		return saved;
	}
	
	public Materials findOne(int MaterialsId){
		Assert.notNull(MaterialsId);
		
		Materials Materials;
		
		Materials = MaterialsRepository.findOne(MaterialsId);
		
		return Materials;
	}
	
	public Collection<Materials> findAll(){
		Collection<Materials> Materialss;
		
		Materialss = MaterialsRepository.findAll();
		
		return Materialss;
	}
	
	public void delete(Materials Materials){
		Assert.notNull(Materials);
		
		MaterialsRepository.delete(Materials);
	}

}
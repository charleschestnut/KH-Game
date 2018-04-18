package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarehouseRepository;
import domain.Warehouse;

@Service
@Transactional
public class WarehouseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private WarehouseRepository WarehouseRepository;

	// CRUD methods
	
	public Warehouse create(){
		Warehouse Warehouse;
		
		Warehouse = new Warehouse();
		
		return Warehouse;
	}
	
	public Warehouse save(Warehouse Warehouse){
		Assert.notNull(Warehouse);
		
		Warehouse saved;
		
		saved = WarehouseRepository.save(Warehouse);
		
		return saved;
	}
	
	public Warehouse findOne(int WarehouseId){
		Assert.notNull(WarehouseId);
		
		Warehouse Warehouse;
		
		Warehouse = WarehouseRepository.findOne(WarehouseId);
		
		return Warehouse;
	}
	
	public Collection<Warehouse> findAll(){
		Collection<Warehouse> Warehouses;
		
		Warehouses = WarehouseRepository.findAll();
		
		return Warehouses;
	}
	
	public void delete(Warehouse Warehouse){
		Assert.notNull(Warehouse);
		
		WarehouseRepository.delete(Warehouse);
	}

}
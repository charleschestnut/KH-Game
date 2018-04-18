package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PurchaseRepository;
import domain.Purchase;

@Service
@Transactional
public class PurchaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PurchaseRepository PurchaseRepository;

	// CRUD methods
	
	public Purchase create(){
		Purchase Purchase;
		
		Purchase = new Purchase();
		
		return Purchase;
	}
	
	public Purchase save(Purchase Purchase){
		Assert.notNull(Purchase);
		
		Purchase saved;
		
		saved = PurchaseRepository.save(Purchase);
		
		return saved;
	}
	
	public Purchase findOne(int PurchaseId){
		Assert.notNull(PurchaseId);
		
		Purchase Purchase;
		
		Purchase = PurchaseRepository.findOne(PurchaseId);
		
		return Purchase;
	}
	
	public Collection<Purchase> findAll(){
		Collection<Purchase> Purchases;
		
		Purchases = PurchaseRepository.findAll();
		
		return Purchases;
	}
	
	public void delete(Purchase Purchase){
		Assert.notNull(Purchase);
		
		PurchaseRepository.delete(Purchase);
	}

}
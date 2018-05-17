package services;

import java.util.Collection;
import java.util.Date;

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
	private PurchaseRepository purchaseRepository;

	// CRUD methods
	
	public Purchase create(){
		Purchase purchase;
		
		purchase = new Purchase();
		
		return purchase;
	}
	
	public Purchase save(Purchase purchase){
		Assert.notNull(purchase);
		
		Purchase saved;
		
		saved = purchaseRepository.save(purchase);
		
		return saved;
	}
	
	public Purchase findOne(int purchaseId){
		Assert.notNull(purchaseId);
		
		Purchase purchase;
		
		purchase = purchaseRepository.findOne(purchaseId);
		
		return purchase;
	}
	
	public Collection<Purchase> findAll(){
		Collection<Purchase> purchases;
		
		purchases = purchaseRepository.findAll();
		
		return purchases;
	}
	
	public void delete(Purchase purchase){
		Assert.notNull(purchase);
		
		purchaseRepository.delete(purchase);
	}
	
	// OTROS METODOS -----------
	
	// Activar un item
	public void activeItem(Purchase purchase) {
		purchase.setActivationDate(new Date(System.currentTimeMillis()-100));
		this.save(purchase);
	}
	
	// Lista de purchases sobre un item concreto que no estan activados
	public Collection<Purchase> noActivePurchases(int itemId) {
		return this.purchaseRepository.noActivePurchases(itemId);
	}
	
	public Collection<Purchase> noActivePurchasesByPlayer(int playerId) {
		return this.purchaseRepository.noActivePurchasesByPlayer(playerId);
	}

}
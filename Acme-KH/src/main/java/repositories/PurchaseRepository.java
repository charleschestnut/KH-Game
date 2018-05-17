package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	
	// Devuelve los purchases dado un item que no estan activos
	@Query("select p from Purchase p where p.item.id =?1 and (CURRENT_TIMESTAMP < p.expirationDate) and p.activationDate = null")
	Collection<Purchase> noActivePurchases(int itemId);
	
	@Query("select p from Purchase p where p.player.id =?1 and (CURRENT_TIMESTAMP < p.expirationDate) and p.activationDate = null")
	Collection<Purchase> noActivePurchasesByPlayer(int playerId);

}

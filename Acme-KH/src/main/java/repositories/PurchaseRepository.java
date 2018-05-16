package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	
	@Query("select p from Purchase p where p.item.id =?1 and p.activationDate = null")
	Collection<Purchase> noActivePurchases(int itemId);

}

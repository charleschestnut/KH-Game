package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	

}

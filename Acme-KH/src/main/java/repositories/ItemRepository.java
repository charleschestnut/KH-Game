package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	@Query("select i from Purchase p join p.item i where p.player.id = ?1 and (CURRENT_TIMESTAMP < p.expirationDate)")
	Collection<Item> myItems(int playerId);
	
}

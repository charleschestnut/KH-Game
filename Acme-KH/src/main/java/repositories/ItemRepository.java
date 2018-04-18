package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	

}

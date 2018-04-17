package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Chatty;

public interface ChattyRepository extends JpaRepository<Chatty, Integer> {
	

}

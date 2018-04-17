package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import domain.Banned;

public interface BannedRepository extends JpaRepository<Banned, Integer> {
	

}

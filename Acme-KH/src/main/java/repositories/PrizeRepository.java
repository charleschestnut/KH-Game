package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Prize;

public interface PrizeRepository extends JpaRepository<Prize, Integer> {
	

}

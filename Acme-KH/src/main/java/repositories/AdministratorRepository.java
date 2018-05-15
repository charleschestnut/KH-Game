package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Actor;
import domain.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
	

}

package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
	

}

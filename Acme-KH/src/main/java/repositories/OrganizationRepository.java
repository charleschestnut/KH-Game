package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.KeybladeWielder;
import domain.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	Organization findOrganizationByPlayer(int playerId); //TODO
	
	Organization keybladeWielderHasOrganization(int playerId); //TODO
	
	Collection<KeybladeWielder> getMembersOfOrganization(int organizationId); //TODO
	

}

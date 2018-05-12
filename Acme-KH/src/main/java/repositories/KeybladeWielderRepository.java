package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.KeybladeWielder;

public interface KeybladeWielderRepository extends JpaRepository<KeybladeWielder, Integer> {

	Collection<KeybladeWielder> findMembersOfOrganization(int organizationId); //TODO
	

}

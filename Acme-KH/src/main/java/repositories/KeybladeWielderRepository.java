package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.KeybladeWielder;

public interface KeybladeWielderRepository extends JpaRepository<KeybladeWielder, Integer> {

	@Query("select i.keybladeWielder from Invitation i where (i.invitationStatus='ACCEPTED' and i.organization.id=?1)")
	Collection<KeybladeWielder> findMembersOfOrganization(int organizationId);
	

}

package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
	

}

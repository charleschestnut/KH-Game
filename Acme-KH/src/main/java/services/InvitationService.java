package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvitationRepository;
import domain.Invitation;

@Service
@Transactional
public class InvitationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private InvitationRepository InvitationRepository;

	// CRUD methods
	
	public Invitation create(){
		Invitation Invitation;
		
		Invitation = new Invitation();
		
		return Invitation;
	}
	
	public Invitation save(Invitation Invitation){
		Assert.notNull(Invitation);
		
		Invitation saved;
		
		saved = InvitationRepository.save(Invitation);
		
		return saved;
	}
	
	public Invitation findOne(int InvitationId){
		Assert.notNull(InvitationId);
		
		Invitation Invitation;
		
		Invitation = InvitationRepository.findOne(InvitationId);
		
		return Invitation;
	}
	
	public Collection<Invitation> findAll(){
		Collection<Invitation> Invitations;
		
		Invitations = InvitationRepository.findAll();
		
		return Invitations;
	}
	
	public void delete(Invitation Invitation){
		Assert.notNull(Invitation);
		
		InvitationRepository.delete(Invitation);
	}

}
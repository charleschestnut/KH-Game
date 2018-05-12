package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InvitationRepository;
import domain.Invitation;
import domain.InvitationStatus;
import domain.KeybladeWielder;
import domain.OrgRange;
import domain.Organization;

@Service
@Transactional
public class InvitationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private InvitationRepository InvitationRepository;
	
	@Autowired
	private KeybladeWielderService keybladeWielderService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private ActorService actorService;

	// CRUD methods
	
	public Invitation create(int keybladeWielderId){
		Invitation invitation;
		
		invitation = new Invitation();
		KeybladeWielder invited = this.keybladeWielderService.findOne(keybladeWielderId);
		
		invitation.setKeybladeWielder(invited);
		invitation.setInvitationStatus(InvitationStatus.PENDING);
		invitation.setOrgRange(OrgRange.GUEST); //Ponemos GUEST por defecto y luego podemos elegir el que queramos.
		
		//QUERY DONDE COGEMOS LA ORGANIZACIÓN DEL USUARIO ACTUAL
		KeybladeWielder playerActual = (KeybladeWielder) this.actorService.findByPrincipal();
		Organization actual = this.organizationService.findOrganizationByPlayer(playerActual.getId());
		
		invitation.setOrganization(actual);
		
		
		return invitation;
	}
	
	public Invitation save(Invitation invitation){
		Assert.notNull(invitation);
		Assert.notNull(invitation.getInvitationStatus());
		
		
		//QUERY DONDE COGEMOS LA ORGANIZACIÓN DEL USUARIO ACTUAL
		Organization actual = new Organization();
		Boolean noTieneOrganizacion = this.organizationService.keybladeWielderHasOrganization(invitation.getKeybladeWielder().getId());
		
		//Es la misma organización que la organización actual del jugador y el invitado no tiene invitación.
		Assert.isTrue(invitation.getOrganization().equals(actual));
		Assert.isTrue(noTieneOrganizacion);
		
		invitation.setDate(new Date(System.currentTimeMillis() -1000));
		
		
		Invitation saved = InvitationRepository.save(invitation);
		
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
	
	
	public Invitation createForOrganizationCreation(int keybladeWielderId, int organizationId){
		Invitation invitation;
		
		invitation = new Invitation();
		KeybladeWielder invited = this.keybladeWielderService.findOne(keybladeWielderId);
		
		invitation.setKeybladeWielder(invited);
		invitation.setInvitationStatus(InvitationStatus.ACCEPTED);
		invitation.setOrgRange(OrgRange.MASTER); //Como crea el la organización, es un MASTER.
		
		
		Organization nueva = this.organizationService.findOne(organizationId);
		invitation.setOrganization(nueva);
		
		Invitation saved = InvitationRepository.save(invitation);
		
		return saved;
	}

	public Invitation findInvitationByKeybladeWielderId(int playerId) { //TODO
		Invitation actual = this.InvitationRepository.findInvitationByKeybladeWielderId(playerId);
		return actual;
	}

}
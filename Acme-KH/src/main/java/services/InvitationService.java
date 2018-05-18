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
	private InvitationRepository invitationRepository;
	
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
		invitation.setDate(new Date(System.currentTimeMillis()-1000));
		invitation.setContent("");
		//QUERY DONDE COGEMOS LA ORGANIZACIÓN DEL USUARIO ACTUAL
		KeybladeWielder playerActual = (KeybladeWielder) this.actorService.findByPrincipal();
		Organization actual = this.organizationService.findOrganizationByPlayer(playerActual.getId());
		
		invitation.setOrganization(actual);
		
		
		return invitation;
	}
	
	public Invitation save(Invitation invitation){
		Assert.notNull(invitation);
		Assert.notNull(invitation.getInvitationStatus());
		Assert.isTrue(!invitation.getOrgRange().equals(OrgRange.MASTER), "error.message.invitation.notBeMaster");
		KeybladeWielder principal = (KeybladeWielder) this.actorService.findByPrincipal();
		
		// QUERY DONDE COGEMOS LA ORGANIZACIÓN DEL USUARIO ACTUAL
		Organization actual = this.organizationService.findOrganizationByPlayer(principal.getId());
		Boolean tieneOrganizacion = this.organizationService.keybladeWielderHasOrganization(invitation.getKeybladeWielder().getId());
		
		// Es la misma organización que la organización actual del jugador y el invitado no tiene invitación.
		Assert.isTrue(invitation.getOrganization().equals(actual), "error.message.chatty.sameOrganization");
		Assert.isTrue(!tieneOrganizacion, "error.message.invitation.hasOrganization");
		
		// El creador de la invitación debe ser MASTER u OFFICER
		Invitation invitPrincipal = this.findInvitationByKeybladeWielderInAnOrganization(principal.getId(), invitation.getOrganization().getId());
		Assert.isTrue(invitPrincipal.getOrgRange().equals(OrgRange.MASTER) || invitPrincipal.getOrgRange().equals(OrgRange.OFFICER), "error.message.notMasterOrOfficer");
		
		invitation.setDate(new Date(System.currentTimeMillis() -1000));
		Invitation saved = invitationRepository.save(invitation);
		
		return saved;
	}
	
	public Invitation findOne(int InvitationId){
		Assert.notNull(InvitationId);
		
		Invitation Invitation;
		
		Invitation = invitationRepository.findOne(InvitationId);
		
		return Invitation;
	}
	
	public Collection<Invitation> findAll(){
		Collection<Invitation> Invitations;
		
		Invitations = invitationRepository.findAll();
		
		return Invitations;
	}
	
	public void delete(Invitation Invitation){
		Assert.notNull(Invitation);
		
		invitationRepository.delete(Invitation);
	}
	
	
	public void createForOrganizationCreation(int keybladeWielderId, int organizationId){
		Invitation invitation;
		
		invitation = new Invitation();
		KeybladeWielder invited = this.keybladeWielderService.findOne(keybladeWielderId);
		
		invitation.setKeybladeWielder(invited);
		invitation.setInvitationStatus(InvitationStatus.ACCEPTED);
		invitation.setOrgRange(OrgRange.MASTER); //Como crea el la organización, es un MASTER.
		invitation.setContent("Organization");
		
		Organization nueva = this.organizationService.findOne(organizationId);
		invitation.setOrganization(nueva);
		invitation.setDate(nueva.getCreationDate());
		
		Invitation saved = invitationRepository.save(invitation);
		
	}

	public Collection<Invitation> findInvitationsByKeybladeWielderId(int playerId) { 
		Collection<Invitation> actual = this.invitationRepository.findCorrectInvitationsByKeybladeWielder(playerId);
		
		Collection<Invitation> toDelete = this.invitationRepository.findDeleteableInvitationsByKeybladeWielder(playerId);
		
		for(Invitation i : toDelete){
			this.invitationRepository.delete(i);
		}
		
		
		return actual;
	}
	
	public Invitation findInvitationByKeybladeWielderInAnOrganization(int playerId, int organizationId) { 
		return this.invitationRepository.findInvitationOfKeywielderInAnOrganization(playerId, organizationId);
	}

	
	public void chageRangeToMaster(int invitationId) {
		Invitation toEdit = this.findOne(invitationId);
		toEdit.setOrgRange(OrgRange.MASTER);
		this.invitationRepository.save(toEdit);
	}
	
	public void changeRange(int invitationId) {
		Invitation toEdit = this.findOne(invitationId);
		if(toEdit.getOrgRange().equals(OrgRange.OFFICER))
			toEdit.setOrgRange(OrgRange.GUEST);
		else if(toEdit.getOrgRange().equals(OrgRange.GUEST))
			toEdit.setOrgRange(OrgRange.OFFICER);
		this.invitationRepository.save(toEdit);
	}
	
	public void declineInvitation(int invitationId) {
		Invitation toEdit = this.findOne(invitationId);
		toEdit.setInvitationStatus(InvitationStatus.CANCELLED);
		this.invitationRepository.save(toEdit);
	}
	
	public void AcceptInvitation(int invitationId) {
		Invitation toEdit = this.findOne(invitationId);
		toEdit.setInvitationStatus(InvitationStatus.ACCEPTED);
		this.invitationRepository.save(toEdit);
	}
	
	
	public Collection<Invitation> findOfficersInOrganization(int organizationId){
		return this.invitationRepository.findOfficersInOrganization(organizationId);
	}

	
	Collection<Invitation> findGuestsInOrganization(int organizationId){
		return this.invitationRepository.findGuestsInOrganization(organizationId);
	}

	public Collection<Invitation> findAllMembersOfOrganization(int orgId) {
		return this.invitationRepository.findAllMembersOfOrganization(orgId);
	}

	public Invitation getInvitationOfMasterOrOfficer(int id) {
		Invitation actual = this.invitationRepository.getInvitationOfMasterOrOfficer(id);
		return actual;
	}

	public void interchangeRange(Invitation master, Invitation toMaster) {
		master.setOrgRange(OrgRange.OFFICER);
		toMaster.setOrgRange(OrgRange.MASTER);
		this.invitationRepository.save(master);
		this.invitationRepository.save(toMaster);
		
	}

}
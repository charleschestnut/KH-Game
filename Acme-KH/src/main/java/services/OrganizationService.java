package services;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrganizationRepository;
import security.LoginService;
import domain.Invitation;
import domain.KeybladeWielder;
import domain.OrgRange;
import domain.Organization;

@Service
@Transactional
public class OrganizationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private KeybladeWielderService keybladeWielderService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private InvitationService invitationService;

	// CRUD methods
	
	public Organization create(){
		Organization Organization;
		//Sólo puede crearla si no pertenece a ninguna organizacion.
		
		Organization = new Organization();
		
		return Organization;
	}
	
	public Organization save(Organization organization){
		Assert.notNull(organization);
		Assert.isTrue(!organization.getName().isEmpty());
		Assert.isTrue(!organization.getDescription().isEmpty());
		Organization saved;
		
		if(organization.getId()!=0){  // Si quiere editar la organización, debe ser MASTER de esa organización
			
			Invitation actual = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(this.actorService.findByPrincipal().getId(), organization.getId());
			Assert.isTrue(actual.getOrgRange().equals(OrgRange.MASTER) && actual.getOrganization().equals(organization), "error.message.invitation.notMaster");
			saved = organizationRepository.save(organization);
		
		}else{  //Si está creando una organización, el principal no debe tener organización.
			
			Boolean tieneOrganizacion = this.keybladeWielderHasOrganization(this.actorService.findByPrincipal().getId());
			Assert.isTrue(!tieneOrganizacion, "error.message.invitation.hasOrganization");
			//Tengo que crear una invitación aceptada automáticamente para mí.
			saved = organizationRepository.save(organization);
			this.invitationService.createForOrganizationCreation(this.actorService.findByPrincipal().getId(), saved.getId());
		}
		
		//Tengo que crear una invitación aceptada automáticamente para mí.
		
		return saved;
	}
	
	public Organization findOne(int organizationId){
		Assert.notNull(organizationId);
		
		Organization Organization;
		
		Organization = organizationRepository.findOne(organizationId);
		
		return Organization;
	}
	
	public Collection<Organization> findAll(){
		Collection<Organization> Organizations;
		
		Organizations = organizationRepository.findAll();
		
		return Organizations;
	}
	
	public void delete(Organization organization){
		Assert.notNull(organization);
		//Sólo puede hacerlo el admin o si se queda sin gente.
		//Query que me devuelva los keywielders conectados a esa organización.
		Collection<KeybladeWielder> members = this.keybladeWielderService.findMembersOfOrganization(organization.getId()); //TODO
		Assert.isTrue(members.size() == 0 || LoginService.getPrincipal().isAuthority("ADMIN"));
		
		this.organizationRepository.delete(organization);
	}

	public Boolean keybladeWielderHasOrganization(int playerId){ 
		Boolean res = true;
		
		//Llamamos al repositorio.
		Organization actual = this.organizationRepository.findOrganizationByPlayer(playerId);
		if(actual ==null)
			res = false;
		
		return res;
	}
	
	public void leaveOrganization(int organizationId){ //Cuando dejo la organización, borro la invitación aceptada que tengo.
		
		Organization organization = this.findOne(organizationId);
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Invitation invitationActual = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), organizationId);
		
		if(this.keybladeWielderService.findMembersOfOrganization(organizationId).size()==1){ // Si sólo queda él, se borra la organización también.
			this.invitationService.delete(invitationActual);
			this.organizationRepository.delete(organization);
		}else{
			List<Invitation> allOfficers = (List<Invitation>) this.invitationService.findOfficersInOrganization(organizationId);
			if(allOfficers.size()!=0)
				this.invitationService.chageRange(allOfficers.get(0).getId(), OrgRange.MASTER);
			else{
				List<Invitation> allGuests = (List<Invitation>) this.invitationService.findGuestsInOrganization(organizationId);
				this.invitationService.chageRange(allGuests.get(0).getId(), OrgRange.MASTER);
			}
				
		}

		if(invitationActual.getOrgRange().equals(OrgRange.MASTER)){
			
			
		}
		this.invitationService.delete(invitationActual);
		
		//Query que me devuelva los keywielders conectados a esa organización.
		Collection<KeybladeWielder> members = this.keybladeWielderService.findMembersOfOrganization(organizationId);
		if(members.size() == 0)
			this.delete(organization);
		
	}
	
	public Organization findOrganizationByPlayer (int playerId){
		
		return this.organizationRepository.findOrganizationByPlayer(playerId);
	}
}
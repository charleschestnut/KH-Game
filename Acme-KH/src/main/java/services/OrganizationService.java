package services;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.OrganizationRepository;
import security.LoginService;
import domain.Chatty;
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
	
	@Autowired
	private ChattyService chattyService;
	
	@Autowired
	Validator						validator;

	// CRUD methods
	
	public Organization create(){
		Organization organization;
		//S�lo puede crearla si no pertenece a ninguna organizacion.
		
		organization = new Organization();
		organization.setCreationDate(new Date(System.currentTimeMillis()-10000));
		
		return organization;
	}
	
	public Organization save(Organization organization){
		Assert.notNull(organization);
		Assert.isTrue(!organization.getName().isEmpty());
		Assert.isTrue(!organization.getDescription().isEmpty());
		Organization saved;
		
		if(organization.getId()!=0){  // Si quiere editar la organizaci�n, debe ser MASTER de esa organizaci�n
			
			Invitation actual = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(this.actorService.findByPrincipal().getId(), organization.getId());
			Assert.isTrue(actual.getOrgRange().equals(OrgRange.MASTER) && actual.getOrganization().equals(organization), "error.message.invitation.notMaster");
			saved = organizationRepository.save(organization);
		
		}else{  //Si est� creando una organizaci�n, el principal no debe tener organizaci�n.
			
			organization.setCreationDate(new Date(System.currentTimeMillis()-1000));
			Boolean tieneOrganizacion = this.keybladeWielderHasOrganization(this.actorService.findByPrincipal().getId());
			Assert.isTrue(!tieneOrganizacion, "error.message.invitation.hasOrganization");
			//Tengo que crear una invitaci�n aceptada autom�ticamente para m�.
			saved = organizationRepository.save(organization);
			this.invitationService.createForOrganizationCreation(this.actorService.findByPrincipal().getId(), saved.getId()); 
		}
		
		//Tengo que crear una invitaci�n aceptada autom�ticamente para m�.
		
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
		//S�lo puede hacerlo el admin o si se queda sin gente.
		//Query que me devuelva los keywielders conectados a esa organizaci�n.
		Collection<KeybladeWielder> members = this.keybladeWielderService.findMembersOfOrganization(organization.getId()); 
		Assert.isTrue(members.size() == 0 || LoginService.getPrincipal().isAuthority("ADMIN"));
		
		if(LoginService.getPrincipal().isAuthority("ADMIN")){
			Collection<Chatty> chattys = this.chattyService.getToDeleteOrganiation(organization.getId());
			Collection<Invitation> invitations = this.invitationService.getToDeleteOrganization(organization.getId());
			this.chattyService.deleteAll(chattys);
			this.invitationService.deleteAll(invitations);
		}
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
	
	public void leaveOrganization(int organizationId){ //Cuando dejo la organizaci�n, borro la invitaci�n aceptada que tengo.
		
		Organization organization = this.findOne(organizationId);
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Invitation invitationActual = this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), organizationId);
		this.chattyService.deleteAll(this.chattyService.findForLeavingOrganization(invitationActual.getId()));
		
		if(this.keybladeWielderService.findMembersOfOrganization(organizationId).size()==1){ // Si s�lo queda �l, se borra la organizaci�n tambi�n.
			
			this.invitationService.deleteAll(this.invitationService.getToDeleteOrganization(organizationId));
			this.organizationRepository.delete(organization);
		}else{
			List<Invitation> allOfficers = (List<Invitation>) this.invitationService.findOfficersInOrganization(organizationId);
			List<Invitation> allGuests = (List<Invitation>) this.invitationService.findGuestsInOrganization(organizationId);
			
			if(invitationActual.getOrgRange().equals(OrgRange.MASTER) ){ //Si se va el MASTER, le pasa el rango al OFFICER m�s antiguo, si no, al GUEST. Luego se borra la invitaci�n.
				
				if(allOfficers.size()!=0)
					this.invitationService.chageRangeToMaster(allOfficers.get(0).getId());
				else
					this.invitationService.chageRangeToMaster(allGuests.get(0).getId());
			}
			this.invitationService.delete(invitationActual);
		}
		
	}
	
	public Organization findOrganizationByPlayer (int playerId){
		
		return this.organizationRepository.findOrganizationByPlayer(playerId);
	}


	
	// ------ RECONSTRUCT -----
	public Organization reconstruct(Organization o, BindingResult binding) {
		Organization result;
		Organization original = this.organizationRepository.findOne(o.getId());
		
		if (o.getId() == 0) {
			result = o;
			result.setCreationDate(new Date(System.currentTimeMillis()-2000));
		} else {
			//Aqu� van los atributos hidden
			result = o;
			result.setCreationDate(original.getCreationDate());
			
		}
		this.validator.validate(result, binding);

		return result;

	}

	public Boolean getCanSendInvitation() {
		KeybladeWielder actual =(KeybladeWielder) this.actorService.findByPrincipal();
		Invitation invitActual = this.invitationService.getInvitationOfMasterOrOfficer(actual.getId());
		if(invitActual == null)
			return false;
		else
			return true;
	}
	
}
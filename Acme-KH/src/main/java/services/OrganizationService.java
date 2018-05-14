package services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrganizationRepository;
import security.LoginService;
import domain.Invitation;
import domain.KeybladeWielder;
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
	
	public Organization save(Organization Organization){
		Assert.notNull(Organization);
		
		Organization saved;
	
		saved = organizationRepository.save(Organization);
		//Tengo que crear una invitación aceptada automáticamente para mí.
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		this.invitationService.createForOrganizationCreation(actual.getId(), saved.getId());
		
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

	public Boolean keybladeWielderHasOrganization(int playerId){ //TODO
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
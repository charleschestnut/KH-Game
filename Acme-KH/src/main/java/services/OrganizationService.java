package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrganizationRepository;
import domain.Organization;

@Service
@Transactional
public class OrganizationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OrganizationRepository OrganizationRepository;

	// CRUD methods
	
	public Organization create(){
		Organization Organization;
		
		Organization = new Organization();
		
		return Organization;
	}
	
	public Organization save(Organization Organization){
		Assert.notNull(Organization);
		
		Organization saved;
		
		saved = OrganizationRepository.save(Organization);
		
		return saved;
	}
	
	public Organization findOne(int OrganizationId){
		Assert.notNull(OrganizationId);
		
		Organization Organization;
		
		Organization = OrganizationRepository.findOne(OrganizationId);
		
		return Organization;
	}
	
	public Collection<Organization> findAll(){
		Collection<Organization> Organizations;
		
		Organizations = OrganizationRepository.findAll();
		
		return Organizations;
	}
	
	public void delete(Organization Organization){
		Assert.notNull(Organization);
		
		OrganizationRepository.delete(Organization);
	}

}
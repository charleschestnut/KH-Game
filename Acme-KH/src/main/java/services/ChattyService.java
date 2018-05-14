package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChattyRepository;
import domain.Chatty;
import domain.Configuration;
import domain.KeybladeWielder;
import domain.Organization;

@Service
@Transactional
public class ChattyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChattyRepository chattyRepository;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private ConfigurationService configurationService;

	// CRUD methods
	
	public Chatty create(){
		Chatty chatty;
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Organization org = this.organizationService.findOrganizationByPlayer(actual.getId());
		chatty = new Chatty();
		
		chatty.setContent("");
		chatty.setDate(new Date(System.currentTimeMillis()-1000));
		chatty.setInvitation(this.invitationService.findInvitationByKeybladeWielderInAnOrganization(actual.getId(), org.getId()));
		
		return chatty;
	}
	
	
	public Chatty save(Chatty chatty){
		Assert.notNull(chatty);
		Assert.notNull(chatty.getInvitation());
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		Organization org = this.organizationService.findOrganizationByPlayer(actual.getId());
		
		Assert.isTrue(org.equals(chatty.getInvitation().getOrganization())); // Deben ser de la misma organización.
		chatty.setDate(new Date(System.currentTimeMillis()-1000));
		
		Chatty saved;
		
		saved = chattyRepository.save(chatty);
		
		return saved;
	}
	
	
	public Chatty findOne(int chattyId){
		Assert.notNull(chattyId);
		
		Chatty chatty;
		
		chatty = chattyRepository.findOne(chattyId);
		
		return chatty;
	}
	
	public Collection<Chatty> findAll(){
		Collection<Chatty> chattys;
		
		chattys = chattyRepository.findAll();
		
		return chattys;
	}
	
	public void delete(Chatty chatty){
		Assert.notNull(chatty);
		
		chattyRepository.delete(chatty);
	}

	
	public Collection<Chatty> getChattyFromAnOrganization(int organizationId){
		List<Chatty> all = (List<Chatty>) this.chattyRepository.getChattyFromAnOrganization(organizationId);
		Integer chattyLimit = this.configurationService.getConfiguration().getOrgMessages();
		Collection<Chatty> availables = new ArrayList<Chatty>();
		
		if(all.size() >= chattyLimit){
			availables = all.subList(0, chattyLimit-1);
			List<Chatty> toDelete= all.subList(chattyLimit, all.size());
			
			for(Chatty c : toDelete){
				this.chattyRepository.delete(c);
			}
		}
		return availables;
	}
}
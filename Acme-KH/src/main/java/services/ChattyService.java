package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChattyRepository;
import domain.Chatty;

@Service
@Transactional
public class ChattyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChattyRepository chattyRepository;

	// CRUD methods
	
	public Chatty create(){
		Chatty chatty;
		
		chatty = new Chatty();
		
		return chatty;
	}
	
	public Chatty save(Chatty chatty){
		Assert.notNull(chatty);
		
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

}
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContentManagerRepository;
import domain.ContentManager;

@Service
@Transactional
public class ContentManagerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ContentManagerRepository contentManagerRepository;

	// CRUD methods
	
	public ContentManager create(){
		ContentManager contentManager;
		
		contentManager = new ContentManager();
		
		return contentManager;
	}
	
	public ContentManager save(ContentManager contentManager){
		Assert.notNull(contentManager);
		
		ContentManager saved;
		
		saved = contentManagerRepository.save(contentManager);
		
		return saved;
	}
	
	public ContentManager findOne(int contentManagerId){
		Assert.notNull(contentManagerId);
		
		ContentManager contentManager;
		
		contentManager = contentManagerRepository.findOne(contentManagerId);
		
		return contentManager;
	}
	
	public Collection<ContentManager> findAll(){
		Collection<ContentManager> contentManagers;
		
		contentManagers = contentManagerRepository.findAll();
		
		return contentManagers;
	}
	
	public void delete(ContentManager contentManager){
		Assert.notNull(contentManager);
		
		contentManagerRepository.delete(contentManager);
	}

}
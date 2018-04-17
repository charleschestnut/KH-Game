package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GameMasterRepository;
import domain.GameMaster;

@Service
@Transactional
public class GameMasterService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GameMasterRepository GameMasterRepository;

	// CRUD methods
	
	public GameMaster create(){
		GameMaster GameMaster;
		
		GameMaster = new GameMaster();
		
		return GameMaster;
	}
	
	public GameMaster save(GameMaster GameMaster){
		Assert.notNull(GameMaster);
		
		GameMaster saved;
		
		saved = GameMasterRepository.save(GameMaster);
		
		return saved;
	}
	
	public GameMaster findOne(int GameMasterId){
		Assert.notNull(GameMasterId);
		
		GameMaster GameMaster;
		
		GameMaster = GameMasterRepository.findOne(GameMasterId);
		
		return GameMaster;
	}
	
	public Collection<GameMaster> findAll(){
		Collection<GameMaster> GameMasters;
		
		GameMasters = GameMasterRepository.findAll();
		
		return GameMasters;
	}
	
	public void delete(GameMaster GameMaster){
		Assert.notNull(GameMaster);
		
		GameMasterRepository.delete(GameMaster);
	}

}
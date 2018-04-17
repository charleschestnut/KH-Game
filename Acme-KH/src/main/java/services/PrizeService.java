package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PrizeRepository;
import domain.Prize;

@Service
@Transactional
public class PrizeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PrizeRepository PrizeRepository;

	// CRUD methods
	
	public Prize create(){
		Prize Prize;
		
		Prize = new Prize();
		
		return Prize;
	}
	
	public Prize save(Prize Prize){
		Assert.notNull(Prize);
		
		Prize saved;
		
		saved = PrizeRepository.save(Prize);
		
		return saved;
	}
	
	public Prize findOne(int PrizeId){
		Assert.notNull(PrizeId);
		
		Prize Prize;
		
		Prize = PrizeRepository.findOne(PrizeId);
		
		return Prize;
	}
	
	public Collection<Prize> findAll(){
		Collection<Prize> Prizes;
		
		Prizes = PrizeRepository.findAll();
		
		return Prizes;
	}
	
	public void delete(Prize Prize){
		Assert.notNull(Prize);
		
		PrizeRepository.delete(Prize);
	}

}
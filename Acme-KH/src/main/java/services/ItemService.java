package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;

@Service
@Transactional
public class ItemService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemRepository ItemRepository;

	// CRUD methods
	
	public Item create(){
		Item Item;
		
		Item = new Item();
		
		return Item;
	}
	
	public Item save(Item Item){
		Assert.notNull(Item);
		
		Item saved;
		
		saved = ItemRepository.save(Item);
		
		return saved;
	}
	
	public Item findOne(int ItemId){
		Assert.notNull(ItemId);
		
		Item Item;
		
		Item = ItemRepository.findOne(ItemId);
		
		return Item;
	}
	
	public Collection<Item> findAll(){
		Collection<Item> Items;
		
		Items = ItemRepository.findAll();
		
		return Items;
	}
	
	public void delete(Item Item){
		Assert.notNull(Item);
		
		ItemRepository.delete(Item);
	}

}
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import domain.Item;
import domain.KeybladeWielder;
import domain.Purchase;

@Service
@Transactional
public class ItemService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemRepository ItemRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private PurchaseService purchaseService;

	// CRUD methods
	
	public Item create(){
		Item Item;
		
		Item = new Item();
		
		return Item;
	}
	
	public Item save(Item item){
		Assert.notNull(item);
		
		Item saved;
		
		saved = ItemRepository.save(item);
		
		return saved;
	}
	
	public Item findOne(int itemId){
		Assert.notNull(itemId);
		
		Item Item;
		
		Item = ItemRepository.findOne(itemId);
		
		return Item;
	}
	
	// Con esto puedo saber todos los items que hay, lo usare para mostrarlos en la tienda
	public Collection<Item> findAll(){
		Collection<Item> Items;
		Items = ItemRepository.findAll();
		
		return Items;
	}
	
	public void delete(Item item){
		Assert.notNull(item);
		
		ItemRepository.delete(item);
	}
	
	// OTROS METODOS -----------------
	
	// Comprar un item de la tienda
	public Purchase buyItem(Item item){
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		// Solo lo podremos comprar si disponemos del Munny que cuesta el item
		Assert.isTrue(item.getMunnyCost() <= player.getMaterials().getMunny());
		Purchase p = this.purchaseService.create();
		
		p.setPlayer(player);
		p.setPurchaseDate(new Date());
		p.setItem(item);
		player.getMaterials().setMunny(player.getMaterials().getMunny() - item.getMunnyCost());
		this.purchaseService.save(p);
		
		return p;
		
	}

}
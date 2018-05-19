package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import domain.ContentManager;
import domain.Item;
import domain.KeybladeWielder;
import domain.Purchase;

@Service
@Transactional
public class ItemService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private KeybladeWielderService keybladeWielderService;
	@Autowired
	private Validator validator;

	// CRUD methods

	public Item create() {
		Item Item;

		Item = new Item();

		return Item;
	}

	public Item save(Item item) {
		Assert.notNull(item);

		Item saved;

		saved = itemRepository.save(item);

		return saved;
	}

	public Item findOne(int itemId) {
		Assert.notNull(itemId);

		Item Item;

		Item = itemRepository.findOne(itemId);

		return Item;
	}

	// Con esto puedo saber todos los items que hay, lo usare para mostrarlos en
	// la tienda
	public Collection<Item> findAll() {
		Collection<Item> Items;
		Items = itemRepository.findAll();

		return Items;
	}

	public void delete(Item item) {
		Assert.notNull(item);

		itemRepository.delete(item);
	}

	// OTROS METODOS -----------------

	// Comprar un item de la tienda
	public Purchase buyItem(Item item) {
		KeybladeWielder player = (KeybladeWielder) this.actorService
				.findByPrincipal();
		// Solo lo podremos comprar si disponemos del Munny que cuesta el item
		Assert.isTrue(item.getMunnyCost() <= player.getMaterials().getMunny());
		Purchase p = this.purchaseService.create();
		Date currentDate = new Date(System.currentTimeMillis()-100);

		p.setPlayer(player);
		p.setPurchaseDate(currentDate);
		p.setItem(item);
		player.getMaterials().setMunny(
				player.getMaterials().getMunny() - item.getMunnyCost());

		// Hacemos set de la que sera la fecha en la que el objeto expirara
		Date expirationDate = new Date(currentDate.getTime()
				+ p.getItem().getExpiration() * 24 * 60 * 60 * 1000);
		p.setExpirationDate(expirationDate);

		this.purchaseService.save(p);
		this.keybladeWielderService.save(player);

		return p;

	}

	// Items que he comprado en la tienda y los puedo usar (no han caducado)
	public Collection<Item> myItems(int playerId) {
		return this.itemRepository.myItems(playerId);
	}
	
	// Items que ha creado un Content Manager en especifico
	public Collection<Item> itemsByManager(int managerId){
		return this.itemRepository.itemsByManager(managerId);
	}
	
	
	public Item reconstruct(Item item, BindingResult binding) {
		Item result;
		Item original = this.itemRepository.findOne(item.getId());

		if (item.getId() == 0) {
			result = item;
			result.setContentManager((ContentManager) this.actorService.findByPrincipal());
		} else {
			result = this.itemRepository.findOne(item.getId());

			result.setName(item.getName());
			result.setDescription(item.getDescription());
			result.setOnSell(item.getOnSell());
			result.setType(item.getType());
			result.setDuration(item.getDuration());
			result.setExpiration(item.getExpiration());
			result.setExtra(item.getExtra());
			result.setMunnyCost(item.getMunnyCost());
			
			result.setContentManager(original.getContentManager());
			
		}
		this.validator.validate(result, binding);

		return result;

	}


}
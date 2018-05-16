package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Item;
import domain.KeybladeWielder;

import services.ActorService;
import services.ItemService;

@Controller
@RequestMapping("/item/player")
public class ItemController extends AbstractController{
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ActorService actorService;
	
	// Listar los objetos que se pueden comprar en la tienda
	@RequestMapping(value = "/shopItemsList", method = RequestMethod.GET)
	public ModelAndView shopItemsList() {
		ModelAndView result;
		Collection<Item> items;

		items = this.itemService.findAll();
		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		Integer playerMunny = player.getMaterials().getMunny();

		result = new ModelAndView("item/player/shopItemsList");
		result.addObject("items", items);
		result.addObject("playerMunny", playerMunny);
		//result.addObject("requestURI", "item/player/shopItemsList.do");
		return result;
	}
	

	// Comprar un item de la tienda
	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public ModelAndView buyItem(@RequestParam(value = "itemId", required = true) int itemId) {
		ModelAndView result;
		Item item;

		item = this.itemService.findOne(itemId);
		this.itemService.buyItem(item);

		result = new ModelAndView("redirect:/item/player/shopItemsList.do");
		
		return result;
	}
	
	// Listar los items que tengo comprados (y no estan caducados) para usarlos
	@RequestMapping(value = "/ownedItemsList", method = RequestMethod.GET)
	public ModelAndView myItemsList() {
		ModelAndView result;
		Collection<Item> items;

		KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		items = this.itemService.myItems(player.getId());

		result = new ModelAndView("item/player/ownedItemsList");
		result.addObject("items", items);
		
		return result;
	}

}

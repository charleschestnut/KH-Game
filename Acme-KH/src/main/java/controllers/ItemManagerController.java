package controllers;

import java.util.Collection;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.ContentManager;
import domain.Item;
import services.ActorService;
import services.ItemService;

@Controller()
@RequestMapping("/item/manager")
public class ItemManagerController extends AbstractController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ActorService actorService;
	
	// Lista de items que ha creado el Content Manager logeado
	@RequestMapping(value = "/createdItems", method = RequestMethod.GET)
	public ModelAndView shopItemsList() {
		ModelAndView result;
		Collection<Item> items;

		ContentManager manager = (ContentManager) this.actorService.findByPrincipal();
		items = this.itemService.itemsByManager(manager.getId());
	

		result = new ModelAndView("item/manager/createdItems");
		result.addObject("items", items);
		
		return result;
	}
	
	// Creation ---------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Item item;

			item = itemService.create();
			result = createEditModelAndView(item);

			return result;
		}
	
	
		// EDITION -----------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Item item;

		item = this.itemService.create();

		result = new ModelAndView("item/manager/edit");
		result.addObject("item", item);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Item item, BindingResult binding) {
		ModelAndView result;
		item.setId(0);
		item = this.itemService.reconstruct(item, binding);
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(item);
		} else
			try {
				this.itemService.save(item);
				result = new ModelAndView("redirect:/item/manager/createdItems.do");
			} catch (Throwable oops) {
				System.out.println("--------------------------: " + ExceptionUtils.getStackTrace(oops));
				result = this.createEditModelAndView(item, oops.getMessage());

			}
		return result;
	}
	
	
	protected ModelAndView createEditModelAndView(Item item) {
		return this.createEditModelAndView(item, null);
	}

	protected ModelAndView createEditModelAndView(Item item, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("item/manager/edit");
		result.addObject("item", item);
		result.addObject("message", messageCode);

		return result;
	}



}

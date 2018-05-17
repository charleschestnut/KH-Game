package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Item;

import services.ItemService;

@Controller()
@RequestMapping("/item/manager")
public class ItemManagerController extends AbstractController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Item item;

		item = this.itemService.create();

		result = new ModelAndView("item/manager/edit");
		result.addObject("item", item);

		return result;
	}


}

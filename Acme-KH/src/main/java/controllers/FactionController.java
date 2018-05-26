
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.FactionService;
import domain.Faction;

@Controller
@RequestMapping("/faction")
public class FactionController extends AbstractController {

	@Autowired
	private FactionService	factionService;
	@Autowired
	private ActorService	actorService;


	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res;
		final Collection<Faction> factions = this.factionService.findAll();

		res = new ModelAndView("faction/list");
		res.addObject("factions", factions);
		res.addObject("requestURI", "faction/list.do");

		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final Integer factionId) {
		ModelAndView res = new ModelAndView("faction/display");

		Faction faction = this.factionService.findOne(factionId);
		try {
			//Si no existe el edificio o no es final y no soy el dueño pa fuera
			if (faction == null || (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(Authority.MANAGER)))
				res = new ModelAndView("redirect:list.do");
			else
				res.addObject("faction", faction);

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:list.do");
		}
		return res;
	}

	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView res;
		final Faction faction = this.factionService.create();

		res = new ModelAndView("faction/edit");
		res.addObject("faction", faction);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int factionId) {
		ModelAndView result;
		Faction faction;

		faction = this.factionService.findOne(factionId);
		Assert.notNull(faction);

		result = this.createEditModelAndView(faction);
		result.addObject("contiene", faction);

		return result;
	}

	@RequestMapping(value = "/edit", params = "save")
	public ModelAndView save(final Faction faction, final BindingResult binding) {
		ModelAndView res;
		Faction fact = this.factionService.reconstruct(faction, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(faction);
		else
			try {
				this.factionService.save(fact);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(faction, msg);
			}

		return res;
	}

	protected ModelAndView createEditModelAndView(Faction faction) {
		return this.createEditModelAndView(faction, null);
	}

	protected ModelAndView createEditModelAndView(Faction faction, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("faction/edit");
		result.addObject("faction", faction);
		result.addObject("message", messageCode);

		return result;
	}

}

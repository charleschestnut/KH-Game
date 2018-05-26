
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BattleService;
import services.GummiShipService;
import services.KeybladeWielderService;
import services.TroopService;
import domain.Battle;
import domain.GummiShip;
import domain.KeybladeWielder;
import domain.Troop;
import form.BattleForm;

@Controller
@RequestMapping("/battle")
public class BattleController extends AbstractController {

	@Autowired
	private BattleService			battleService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private TroopService			troopService;
	@Autowired
	private GummiShipService		gummiShipService;
	@Autowired
	private KeybladeWielderService	keybladeWielderService;


	@RequestMapping("/recruited")
	public ModelAndView recruited(@RequestParam(required = true) final String nickname) {
		ModelAndView res;
		final Collection<Troop> troops = this.troopService.findAll();
		final Collection<GummiShip> gummiShips = this.gummiShipService.findAll();
		final Collection<String> nombres = new ArrayList<String>();
		String nom;
		for (final Troop t : troops) {
			nom = '"' + t.getName() + '"';
			nombres.add(nom);
		}
		for (final GummiShip g : gummiShips) {
			nom = '"' + g.getName() + '"';
			nombres.add(nom);
		}
		final BattleForm bf = new BattleForm();
		bf.setEnemy(nickname);
		System.out.println(bf.getEnemy());
		final int numT = troops.size();
		final int numS = gummiShips.size();
		final int num = numT + numS;

		res = new ModelAndView("battle/recruited");
		res.addObject("troops", troops);
		res.addObject("gummiShips", gummiShips);
		res.addObject("num", num);
		res.addObject("nombres", nombres);
		res.addObject("enemy", nickname);
		res.addObject("battleForm", bf);

		return res;
	}

	@RequestMapping(value = "/recruited", params = "save")
	public ModelAndView save(@Valid final BattleForm battleForm, final BindingResult binding) {
		ModelAndView res;
		System.out.println(battleForm.getEnemy());
		System.out.println("Aqui ha entrao");
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(battleForm);
			System.out.println(binding.getAllErrors());
		} else
			try {
				this.battleService.fight(battleForm);
				res = new ModelAndView("redirect:list.do");
				System.out.println("Intenta");
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(battleForm, msg);
				System.out.println("catch");
				System.out.println(oops.getMessage());
			}

		return res;
	}
	@RequestMapping("/listBattles")
	public ModelAndView listBattles() {
		final ModelAndView res;
		final KeybladeWielder keyblader = (KeybladeWielder) this.actorService.findByPrincipal();
		final Collection<Battle> battles = this.battleService.myBattles(keyblader.getId());

		res = new ModelAndView("battle/listBattles");
		res.addObject("battles", battles);
		res.addObject("requestURI", "battle/listBattles.do");

		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam(required = true) final Integer battleId) {
		final ModelAndView res;
		final Battle battle = this.battleService.findOne(battleId);

		res = new ModelAndView("battle/display");
		res.addObject("battle", battle);
		res.addObject("requestURI", "battle/display.do");

		return res;
	}
	@RequestMapping("/listPlayers")
	public ModelAndView list() {
		final ModelAndView res;
		final Collection<KeybladeWielder> players = this.keybladeWielderService.playersToAttackt();

		res = new ModelAndView("battle/listPlayers");
		res.addObject("players", players);
		res.addObject("requestURI", "faction/list.do");

		return res;
	}
	protected ModelAndView createEditModelAndView(final BattleForm battle) {
		return this.createEditModelAndView(battle, null);
	}

	protected ModelAndView createEditModelAndView(final BattleForm battle, final String messageCode) {
		ModelAndView res;

		res = new ModelAndView("battle/recruited");

		return res;
	}

}

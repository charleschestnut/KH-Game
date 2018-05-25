
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BuildingService;
import services.BuiltService;
import services.GummiShipService;
import services.TroopService;
import domain.Building;
import domain.Built;
import domain.Defense;
import domain.GummiShip;
import domain.Livelihood;
import domain.Recruited;
import domain.Recruiter;
import domain.Troop;
import form.BuiltForm;
import form.RecruitedForm;

@Controller
@RequestMapping("/built")
public class BuiltController extends AbstractController {

	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private BuiltService		builtService;
	@Autowired
	private TroopService		troopService;
	@Autowired
	private GummiShipService	gummiShipService;


	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res;
		final Collection<Built> builts = this.builtService.getMyBuilts();

		res = new ModelAndView("built/list");
		res.addObject("builts", builts);

		return res;

	}

	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView res;
		final Collection<Building> buildings = this.buildingService.getAvailableBuildings();
		final BuiltForm form = new BuiltForm();

		res = new ModelAndView("built/edit");
		res.addObject("buildings", buildings);
		res.addObject("builtForm", form);

		return res;
	}

	@RequestMapping(value = "/create", params = "save")
	public ModelAndView save(@Valid final BuiltForm builtForm, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(builtForm);
		else
			try {
				final Built b = this.builtService.create(builtForm.getBuilding());
				this.builtService.save(b);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(builtForm, msg);
			}

		return res;
	}

	@RequestMapping("/upgrade")
	public ModelAndView update(@RequestParam final Integer builtId) {
		ModelAndView res;
		final Built b = this.builtService.findOne(builtId);
		if (b == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.builtService.upgrade(b);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createListModelAndView(msg);
			}
		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final Integer builtId) {
		ModelAndView res = new ModelAndView("built/display");

		final Built b = this.builtService.findOne(builtId);
		final Building building = b.getBuilding();

		if (!b.getKeybladeWielder().getUserAccount().equals(LoginService.getPrincipal()) || b.getLvl() == 0)
			res = new ModelAndView("redirect:list.do");

		else if (building instanceof Defense)
			res.addObject("defense", true);
		else if (building instanceof Recruiter) {

			res.addObject("recruiter", true);
			res.addObject("troops", this.troopService.getTroopsAvailableFromRecruiterAndLvl(building.getId(), b.getLvl()));
			res.addObject("gummiShips", this.gummiShipService.getGummiShipsAvailableFromRecruiterAndLvl(building.getId(), b.getLvl()));
		} else if (building instanceof Livelihood)
			res.addObject("livelihood", true);
		else {
			res.addObject("warehouse", true);
			res.addObject("storagedTroops", this.troopService.getStoragedTroops(builtId));
			res.addObject("storagedShips", this.gummiShipService.getStoragedGummiShip(builtId));
		}

		res.addObject("built", b);

		return res;
	}
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam final Integer builtId) {
		ModelAndView res;
		final Built built = this.builtService.findOne(builtId);

		try {
			this.builtService.delete(built);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			final String msg = this.getErrorMessage(oops);
			res = this.createListModelAndView(msg);
		}
		return res;
	}

	@RequestMapping("/startCollect")
	public ModelAndView startCollect(@RequestParam final Integer builtId) {
		ModelAndView res;
		final Built b = this.builtService.findOne(builtId);
		if (b == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.builtService.startToCollect(b);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createListModelAndView(msg);
			}

		return res;
	}

	@RequestMapping("/collect")
	public ModelAndView collect(@RequestParam final Integer builtId) {
		ModelAndView res;
		final Built b = this.builtService.findOne(builtId);
		if (b == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.builtService.collect(b);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createListModelAndView(msg);
			}

		return res;
	}

	@RequestMapping("/startRecruit")
	public ModelAndView startRecruit(@RequestParam final Integer builtId) {
		final ModelAndView res;

		final Built b = this.builtService.findOne(builtId);
		if (b == null || !b.getKeybladeWielder().getUserAccount().equals(LoginService.getPrincipal()))
			res = new ModelAndView("redirect:list.do");
		else {
			res = new ModelAndView("built/recruit");
			final RecruitedForm form = new RecruitedForm();
			form.setBuilt(b);
			res.addObject("recruitedForm", form);

		}

		return res;
	}

	@RequestMapping(value = "/startRecruit", params = "next", method = RequestMethod.POST)
	public ModelAndView nextRecruit(final String wantRecruit, @Valid final RecruitedForm recruitedForm, final BindingResult binding) {
		final ModelAndView res;

		if (binding.hasErrors())
			res = new ModelAndView("redirect:list.do");
		else {
			res = new ModelAndView("built/recruit");
			res.addObject("wantRecruit", wantRecruit);
			if (wantRecruit.equals("troop")) {
				final Collection<Troop> troops = this.troopService.getTroopsAvailableFromRecruiterAndLvl(recruitedForm.getBuilt().getBuilding().getId(), recruitedForm.getBuilt().getLvl());
				res.addObject("troops", troops);
			} else if (wantRecruit.equals("ship")) {
				final Collection<GummiShip> ships = this.gummiShipService.getGummiShipsAvailableFromRecruiterAndLvl(recruitedForm.getBuilt().getBuilding().getId(), recruitedForm.getBuilt().getLvl());
				res.addObject("ships", ships);
			}
			res.addObject("recruitedForm", recruitedForm);
		}

		return res;
	}
	@RequestMapping(value = "/startRecruit", params = "save", method = RequestMethod.POST)
	public ModelAndView saveRecruit(final String wantRecruit, @Valid final RecruitedForm recruitedForm, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors() || (!wantRecruit.equals("troop") && !wantRecruit.equals("ship")))
			res = this.createRecruitModelAndView(recruitedForm, wantRecruit, null);
		else
			try {
				if (wantRecruit.equals("troop")) {
					this.builtService.startToRecruitTroop(recruitedForm.getBuilt(), recruitedForm.getTroop());
					res = new ModelAndView("redirect:list.do");
				} else {
					this.builtService.startToRecruitGummiShip(recruitedForm.getBuilt(), recruitedForm.getGummiship());
					res = new ModelAndView("redirect:list.do");
				}
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createRecruitModelAndView(recruitedForm, wantRecruit, msg);
			}

		return res;
	}

	@RequestMapping("/recruit")
	public ModelAndView recruit(@RequestParam final Integer builtId) {
		ModelAndView res;
		final Built built = this.builtService.findOne(builtId);
		Recruited recruited;

		if (built == null || !built.getKeybladeWielder().getUserAccount().equals(LoginService.getPrincipal()))
			res = new ModelAndView("redirect:list.do");
		else
			try {
				recruited = this.builtService.recruit(built);
				res = new ModelAndView("redirect:display.do?builtId=" + recruited.getStorageBuilding().getId());
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createListModelAndView(msg);
			}

		return res;
	}
	protected ModelAndView createRecruitModelAndView(final RecruitedForm form, final String wantRecruit, final String msg) {
		final ModelAndView res;

		res = new ModelAndView("built/recruit");
		res.addObject("wantRecruit", wantRecruit);
		res.addObject("recruitedForm", form);
		res.addObject("message", msg);

		if (wantRecruit.equals("troop")) {
			final Collection<Troop> troops = this.troopService.getTroopsAvailableFromRecruiterAndLvl(form.getBuilt().getBuilding().getId(), form.getBuilt().getLvl());
			res.addObject("troops", troops);
		} else if (wantRecruit.equals("ship")) {
			final Collection<GummiShip> ships = this.gummiShipService.getGummiShipsAvailableFromRecruiterAndLvl(form.getBuilt().getBuilding().getId(), form.getBuilt().getLvl());
			res.addObject("ships", ships);
		}

		return res;
	}
	protected ModelAndView createListModelAndView(final String msg) {
		final ModelAndView res;
		final Collection<Built> builts = this.builtService.getMyBuilts();

		res = new ModelAndView("built/list");
		res.addObject("builts", builts);
		res.addObject("message", msg);

		return res;
	}

	protected ModelAndView createEditModelAndView(final BuiltForm form) {
		return this.createEditModelAndView(form, null);

	}
	protected ModelAndView createEditModelAndView(final BuiltForm form, final String msg) {
		ModelAndView res;
		final Collection<Building> buildings = this.buildingService.getAvailableBuildings();

		res = new ModelAndView("built/edit");
		res.addObject("buildings", buildings);
		res.addObject("builtForm", form);
		res.addObject("message", msg);

		return res;

	}

}

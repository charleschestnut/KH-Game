
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BuildingService;
import services.BuiltService;
import services.DefenseService;
import services.LivelihoodService;
import services.RecruiterService;
import services.RequirementService;
import services.WarehouseService;
import domain.Building;
import domain.Built;
import domain.Defense;
import domain.Livelihood;
import domain.Recruiter;
import domain.Warehouse;
import form.BuiltForm;

@Controller
@RequestMapping("/built")
public class BuiltController extends AbstractController {

	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private BuiltService		builtService;
	@Autowired
	private DefenseService		defenseService;
	@Autowired
	private RecruiterService	recruiterService;
	@Autowired
	private WarehouseService	warehouseService;
	@Autowired
	private LivelihoodService	livelihoodService;
	@Autowired
	private RequirementService	requirementService;


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
		Recruiter recruiter = null;
		Warehouse warehouse = null;
		Livelihood livelihood = null;

		final Built b = this.builtService.findOne(builtId);
		final Integer buildingId = b.getBuilding().getId();

		if (!b.getKeybladeWielder().getUserAccount().equals(LoginService.getPrincipal()) || b.getLvl() == 0)
			res = new ModelAndView("redirect:list.do");

		final Defense defense = this.defenseService.findOne(buildingId);
		if (defense != null)
			res.addObject("defense", true);
		else {

			recruiter = this.recruiterService.findOne(buildingId);
			if (recruiter != null)
				res.addObject("recruiter", true);
			//TODO: añadir query para las tropas y gummi ships disponibles dentro del if
			else {
				livelihood = this.livelihoodService.findOne(buildingId);
				if (livelihood != null)
					res.addObject("livelihood", true);
				else {
					warehouse = this.warehouseService.findOne(buildingId);
					if (warehouse != null)
						res.addObject("warehouse", true);

				}

			}
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

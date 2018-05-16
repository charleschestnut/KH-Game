
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.BuildingService;
import services.BuiltService;
import services.DefenseService;
import services.LivelihoodService;
import services.RecruiterService;
import services.RequirementService;
import services.WarehouseService;
import domain.Building;
import domain.Built;
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

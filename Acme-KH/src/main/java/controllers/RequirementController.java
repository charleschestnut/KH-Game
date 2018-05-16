
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

import services.BuildingService;
import services.RequirementService;
import domain.Building;
import domain.Requirement;

@Controller
@RequestMapping("/requirement/contentManager")
public class RequirementController extends AbstractController {

	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private RequirementService	requirementService;


	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam final Integer buildingId) {
		final ModelAndView res;
		final Collection<Building> buildings = this.buildingService.getAvailableBuildings();
		final Building building = this.buildingService.findOne(buildingId);
		final Requirement r = this.requirementService.create(building);

		if (building != null)
			buildings.remove(building);

		res = new ModelAndView("requirement/edit");
		res.addObject("buildings", buildings);
		res.addObject("requirement", r);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Requirement requirement, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(requirement);
		else
			try {
				this.requirementService.save(requirement);
				res = new ModelAndView("redirect:/building/display.do?buildingId=" + requirement.getMainBuilding().getId());
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(requirement, msg);
			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Requirement requirement, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(requirement);
		else
			try {
				this.requirementService.delete(requirement);
				res = new ModelAndView("redirect:/building/display.do?buildingId=" + requirement.getMainBuilding().getId());
			} catch (final Throwable oops) {
				final String msg = this.getErrorMessage(oops);
				res = this.createEditModelAndView(requirement, msg);
			}

		return res;
	}
	protected ModelAndView createEditModelAndView(final Requirement requirement) {
		return this.createEditModelAndView(requirement, null);
	}

	protected ModelAndView createEditModelAndView(final Requirement requirement, final String msg) {
		final ModelAndView res;
		final Collection<Building> buildings = this.buildingService.getAvailableBuildings();

		if (requirement.getMainBuilding() != null)
			buildings.remove(requirement.getMainBuilding());

		res = new ModelAndView("requirement/edit");
		res.addObject("buildings", buildings);
		res.addObject("requirement", requirement);
		res.addObject("message", msg);

		return res;
	}
}

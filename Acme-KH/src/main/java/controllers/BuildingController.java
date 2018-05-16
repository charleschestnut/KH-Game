
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BuildingService;
import services.DefenseService;
import services.LivelihoodService;
import services.RecruiterService;
import services.RequirementService;
import services.WarehouseService;
import domain.Building;
import domain.Defense;
import domain.Livelihood;
import domain.Recruiter;
import domain.Warehouse;

@Controller
@RequestMapping("/building")
public class BuildingController extends AbstractController {

	@Autowired
	private BuildingService		buildingService;
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
		final Collection<Building> buildings = this.buildingService.getAvailableBuildings();

		res = new ModelAndView("building/list");
		res.addObject("buildings", buildings);
		res.addObject("requestURI", "building/list.do");

		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final Integer buildingId) {
		ModelAndView res = new ModelAndView("building/display");
		Recruiter recruiter = null;
		Warehouse warehouse = null;
		Livelihood livelihood = null;

		final Building b = this.buildingService.findOne(buildingId);
		try {
			//Si no existe el edificio o no es final y no soy el dueño pa fuera
			if (b == null || (!b.getIsFinal() && !b.getContentManager().getUserAccount().equals(LoginService.getPrincipal())))
				res = new ModelAndView("redirect:list.do");
			else {

				final Defense defense = this.defenseService.findOne(buildingId);
				if (defense != null) {
					res.addObject("defense", true);
					res.addObject("building", defense);
				} else {

					recruiter = this.recruiterService.findOne(buildingId);
					if (recruiter != null) {
						res.addObject("recruiter", true);
						res.addObject("building", recruiter);
						//TODO: añadir query para las tropas y gummi ships

					} else {
						livelihood = this.livelihoodService.findOne(buildingId);
						if (livelihood != null) {
							res.addObject("livelihood", true);
							res.addObject("building", livelihood);

						} else {
							warehouse = this.warehouseService.findOne(buildingId);
							res.addObject("warehouse", true);
							res.addObject("building", warehouse);

						}

					}
				}

			}

		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:list.do");
		}

		res.addObject("requirements", this.requirementService.getRequirementsByBuilding(buildingId));

		return res;
	}
}

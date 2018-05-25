
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BuildingService;
import services.GummiShipService;
import services.RequirementService;
import services.TroopService;
import domain.Building;
import domain.Defense;
import domain.Livelihood;
import domain.Recruiter;

@Controller
@RequestMapping("/building")
public class BuildingController extends AbstractController {

	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private RequirementService	requirementService;
	@Autowired
	private TroopService		troopService;
	@Autowired
	private GummiShipService	gummiShipService;


	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView res;
		Collection<Building> buildings = this.buildingService.getAvailableBuildings();

		res = new ModelAndView("building/list");
		res.addObject("buildings", buildings);
		res.addObject("requestURI", "building/list.do");

		return res;
	}

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam Integer buildingId) {
		ModelAndView res = new ModelAndView("building/display");

		Building b = this.buildingService.findOne(buildingId);
		try {
			//Si no existe el edificio o no es  y no soy el dueño pa fuera
			if (b == null || (!b.getIsFinal() && !b.getContentManager().getUserAccount().equals(LoginService.getPrincipal())))
				res = new ModelAndView("redirect:list.do");
			else if (b instanceof Defense)
				res.addObject("defense", true);
			else if (b instanceof Recruiter) {

				res.addObject("recruiter", true);
				res.addObject("troops", this.troopService.getTroopsFromRecruiter(b.getId()));
				res.addObject("gummiShips", this.gummiShipService.getGummiShipFromRecruiter(b.getId()));
			} else if (b instanceof Livelihood)
				res.addObject("livelihood", true);
			else
				res.addObject("warehouse", true);

		} catch (Throwable oops) {
			res = new ModelAndView("redirect:list.do");
		}

		res.addObject("requirements", this.requirementService.getRequirementsByBuilding(buildingId));
		res.addObject("building", b);

		return res;
	}
}

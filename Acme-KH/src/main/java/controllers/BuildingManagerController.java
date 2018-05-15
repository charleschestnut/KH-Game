
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BuildingService;
import services.DefenseService;
import services.LivelihoodService;
import services.RecruiterService;
import services.RequirementService;
import services.WarehouseService;
import domain.Building;

@Controller
@RequestMapping("/building/contentManager")
public class BuildingManagerController extends AbstractController {

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


	@RequestMapping("/myList")
	public ModelAndView myList() {
		final ModelAndView res;
		final Collection<Building> buildings = this.buildingService.getMyCreatedBuildings();

		res = new ModelAndView("building/list");
		res.addObject("buildings", buildings);
		res.addObject("requestURI", "building/contentManager/myList.do");

		return res;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam(required = false) final Integer buildingId, @RequestParam(required = false) final String buildingType) {
		final ModelAndView res = new ModelAndView("building/edit");
		//============= EDITANDO UNO EXISTENTE ==============
		if (buildingId != null) {

		}
		//========== FIN EDITANDO ========================
		return res;
	}
}

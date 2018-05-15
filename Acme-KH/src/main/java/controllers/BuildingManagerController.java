
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
import domain.Defense;
import domain.Livelihood;
import domain.Recruiter;
import domain.Warehouse;

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
		ModelAndView res = new ModelAndView("building/edit");
		Defense defense;
		Recruiter recruiter;
		Livelihood livelihood;
		final Warehouse warehouse;

		//============= EDITANDO UNO EXISTENTE ==============
		if (buildingId != null)
			switch (buildingType) {
			case "defense":
				defense = this.defenseService.findOne(buildingId);
				res.addObject("buildingType", "defense");
				res.addObject("defense", defense);
				break;
			case "recruiter":
				recruiter = this.recruiterService.findOne(buildingId);
				res.addObject("buildingType", "recruiter");
				res.addObject("recruiter", recruiter);
				break;
			case "livelihood":
				livelihood = this.livelihoodService.findOne(buildingId);
				res.addObject("buildingType", "livelihood");
				res.addObject("livelihood", livelihood);
				break;
			case "warehouse":
				warehouse = this.warehouseService.findOne(buildingId);
				res.addObject("buildingType", "warehouse");
				res.addObject("warehouse", warehouse);
				break;
			default:
				res = new ModelAndView("redirect:edit.do");
				break;
			}//========== FIN EDITANDO ========================
		//============CREANDO DESDE 0 =============
		else if (buildingType.equals("defense") || buildingType.equals("recruiter") || buildingType.equals("livelihood") || buildingType.equals("warehouse"))
			switch (buildingType) {
			case "defense":
				defense = this.defenseService.create();
				res.addObject("buildingType", "defense");
				res.addObject("defense", defense);
				break;
			case "recruiter":
				recruiter = this.recruiterService.create();
				res.addObject("buildingType", "recruiter");
				res.addObject("recruiter", recruiter);
				break;
			case "livelihood":
				livelihood = this.livelihoodService.create();
				res.addObject("buildingType", "livelihood");
				res.addObject("livelihood", livelihood);
				break;
			default:
				warehouse = this.warehouseService.create();
				res.addObject("buildingType", "warehouse");
				res.addObject("warehouse", warehouse);
				break;
			} // ======== FIN DEL CREATE DESDE 0 =========
		// EN OTRO CASO SE IRÁ AL FORMULARIO PARA SELECCIONAR LO QUE SER

		return res;
	}
}

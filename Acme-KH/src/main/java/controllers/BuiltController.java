
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.BuildingService;
import services.BuiltService;
import services.DefenseService;
import services.LivelihoodService;
import services.RecruiterService;
import services.RequirementService;
import services.WarehouseService;
import domain.Built;

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

}

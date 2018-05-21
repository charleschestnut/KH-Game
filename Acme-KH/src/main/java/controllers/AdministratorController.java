/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.KeybladeWielderService;
import services.ReportService;
import services.ReportUpdateService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}
	
	// SUPPORTED SERVICES -----------------------------------------------------
	@Autowired
	KeybladeWielderService keybladeWielderService;
	@Autowired
	ReportService reportService;
	@Autowired
	ReportUpdateService reportUpdateService;
	@Autowired
	ItemService itemService;

	// DASHBOARD ---------------------------------------------------------------		

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");
		
		result.addObject("ratioOfUserPerFaction", this.keybladeWielderService.ratioOfUserPerFaction());
		result.addObject("getTopWinsPlayers", this.keybladeWielderService.getTopWinsPlayers());
		result.addObject("getTopWinRatioPlayers", this.keybladeWielderService.getTopWinRatioPlayers());
		result.addObject("getTopMunnyPlayers", this.keybladeWielderService.getTopMunnyPlayers());
		result.addObject("getTopMytrhilPlayers", this.keybladeWielderService.getTopMytrhilPlayers());
		result.addObject("avgOfWinRatio", this.keybladeWielderService.avgOfWinRatio());
		
		result.addObject("getAvgReportPerUser", this.reportService.getAvgReportPerUser());
		result.addObject("getStddevReportPerUser", this.reportService.getStddevReportPerUser());
		result.addObject("getRatioOfResolvedReport", this.reportService.getRatioOfResolvedReport());
		result.addObject("getRatioOfIrresolvableReport", this.reportService.getRatioOfIrresolvableReport());
		result.addObject("getRatioOfSuspiciousReport", this.reportService.getRatioOfSuspiciousReport());
		
		result.addObject("avgUpdatesFromGm", this.reportUpdateService.avgUpdatesFromGm());
		result.addObject("stddevUpdatesFromGm", this.reportUpdateService.stddevUpdatesFromGm());
		result.addObject("maxUpdatesFromGm", this.reportUpdateService.maxUpdatesFromGm());
		result.addObject("minUpdatesFromGm", this.reportUpdateService.minUpdatesFromGm());
		result.addObject("avgUpdatesFromReport", this.reportUpdateService.avgUpdatesFromReport());
		result.addObject("stddevUpdatesFromReport", this.reportUpdateService.stddevUpdatesFromReport());
		result.addObject("maxUpdatesFromReport", this.reportUpdateService.maxUpdatesFromReport());
		result.addObject("minUpdatesFromReport", this.reportUpdateService.minUpdatesFromReport());
		result.addObject("avgSuspiciousUpdatesFromGm", this.reportUpdateService.avgSuspiciousUpdatesFromGm());

		result.addObject("maxCreatedItem", this.itemService.maxCreatedItem());
		result.addObject("minCreatedItem", this.itemService.minCreatedItem());
		result.addObject("avgCreatedItem", this.itemService.avgCreatedItem());
		result.addObject("stddevCreatedItem", this.itemService.stddevCreatedItem());
		
		return result;
	}

}

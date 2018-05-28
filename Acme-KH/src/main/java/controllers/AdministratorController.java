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

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BannedService;
import services.ItemService;
import services.KeybladeWielderService;
import services.ReportService;
import services.ReportUpdateService;
import domain.Actor;
import domain.Banned;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}


	// SUPPORTED SERVICES -----------------------------------------------------
	@Autowired
	KeybladeWielderService	keybladeWielderService;
	@Autowired
	ReportService			reportService;
	@Autowired
	ReportUpdateService		reportUpdateService;
	@Autowired
	ItemService				itemService;
	@Autowired
	BannedService			bannedService;
	@Autowired
	ActorService			actorService;


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

	@RequestMapping(value = "/banned/list", method = RequestMethod.GET)
	public ModelAndView bannedList() {
		ModelAndView result;
		Collection<Actor> bannedUsers;

		bannedUsers = this.bannedService.findAllBannedUsers();

		result = new ModelAndView("administrator/banned/list");
		result.addObject("users", bannedUsers);

		return result;
	}

	@RequestMapping(value = "/banned/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam String username, Locale locale) {
		ModelAndView result;
		Actor actor;
		Banned banned;

		try {
			actor = this.actorService.findByUserAccountUsername(username);
			banned = this.bannedService.create(actor);
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/administrator/banned/list.do");
			result.addObject("message", this.showDetails(locale, this.getErrorMessage(oops)));
			return result;
		}

		result = new ModelAndView("administrator/banned/create");
		result.addObject(banned);

		return result;
	}

	@RequestMapping(value = "/banned/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam String username, Locale locale) {
		ModelAndView result;
		Actor actor;
		Banned banned;

		result = new ModelAndView("redirect:/administrator/banned/list.do");
		try {
			actor = this.actorService.findByUserAccountUsername(username);
			banned = this.bannedService.findToUnbanByActor(actor.getId());
			banned = this.bannedService.unban(banned);
		} catch (Throwable oops) {
			result.addObject("message", this.showDetails(locale, this.getErrorMessage(oops)));
		}

		return result;
	}

	@RequestMapping(value = "banned/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Banned banned, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewForm(banned, "error.message.commit");
		else
			try {
				banned = this.bannedService.save(banned);
				result = new ModelAndView("redirect:/administrator/banned/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndViewForm(banned, this.getErrorMessage(oops));
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndViewForm(Banned banned) {
		ModelAndView result;
		result = this.createEditModelAndViewForm(banned, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewForm(Banned banned, String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/banned/create");
		result.addObject("banned", banned);
		result.addObject("message", message);

		return result;
	}

}

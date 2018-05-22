/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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

import domain.Invitation;
import domain.InvitationStatus;
import domain.KeybladeWielder;
import domain.OrgRange;
import domain.Organization;
import domain.Recruiter;
import domain.Troop;

import services.ActorService;
import services.BuildingService;
import services.BuiltService;
import services.InvitationService;
import services.OrganizationService;
import services.RecruiterService;
import services.TroopService;

@Controller
@RequestMapping("/troop/contentManager")
public class TroopController extends AbstractController {

	// SERVICES -----------------------------------------------
	
	@Autowired
	private TroopService troopService;
	
	@Autowired
	private RecruiterService recruiterService;
	
	@Autowired
	private BuildingService buildingService;
	
	
	// Constructors -----------------------------------------------------------

	public TroopController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView action1() {
		ModelAndView result;
		Collection<Troop> all = this.troopService.findAll();
		
		result = new ModelAndView("troop/contentManager/list");
		result.addObject("troops",all);
		System.out.println(all);
		return result;
	}

	// Action-2 ---------------------------------------------------------------		
	@RequestMapping("/edit")
	public ModelAndView create(String recruiterId, @RequestParam (required=false) String troopId) {
		ModelAndView result;
		Troop t;
		int rId = Integer.parseInt(recruiterId);
		
		if(troopId == null){
			Recruiter r = (Recruiter) this.buildingService.findOne(rId);
			t = this.troopService.create(r);
			
		}else{
			int tId = Integer.parseInt(troopId);
			t = this.troopService.findOne(tId);
			
		}
		result = new ModelAndView("troop/contentManager/edit");
		result.addObject("troop", t);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Troop troop, BindingResult binding/*,
			@RequestParam (required=true) int troopId*/) {
		ModelAndView result;
		Troop trp = this.troopService.reconstruct(troop, binding);
		
		if(binding.hasErrors()){
			System.out.println(binding.getAllErrors());
			result = createEditModelAndView(troop);
		}else{
			try{
				
				
				this.recruiterService.addTroop(trp.getRecruiter(), trp);
				
				result = new ModelAndView("redirect:/troop/contentManager/list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(troop, "troop.commit.error");
			}
		}
		return result;
	}
	
	private ModelAndView createEditModelAndView(Troop troop) {
		
		return createEditModelAndView(troop, null);
	}

	private ModelAndView createEditModelAndView(Troop troop, String msg) {
		ModelAndView result;
		result = new ModelAndView("troop/contentManager/edit");

		result.addObject("troop", troop);
		result.addObject("message", msg);
		return result;
	}
	
	
	

	
	
}

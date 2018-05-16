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
import domain.KeybladeWielder;
import domain.Organization;

import services.ActorService;
import services.InvitationService;
import services.KeybladeWielderService;
import services.OrganizationService;

@Controller
@RequestMapping("/organization")
public class OrganizationController extends AbstractController {

	// SERVICIOS PARA CONTROLADOR ---------------------------------------------
	@Autowired
	private KeybladeWielderService keybladeWielderService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public OrganizationController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Organization> all = this.organizationService.findAll();
		
		result = new ModelAndView("organization/list");
		result.addObject("organizations", all);
		result.addObject("requestURI", "organization/list.do");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/membersList")
	public ModelAndView membersList(@RequestParam String organizationId) {
		ModelAndView result;
		int orgId = Integer.parseInt(organizationId);

		Collection<Invitation> membersInvitations = this.invitationService.findAllMembersOfOrganization(orgId);
		
		result = new ModelAndView("organization/membersList");
		result.addObject("organizationId", orgId);
		result.addObject("requestURI", "organization/membersList.do?organizationId="+organizationId);
		result.addObject("membersInvitations", membersInvitations);
		
		return result;
	}
	
	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/edit")
	public ModelAndView create() {
		ModelAndView result;
		KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		
		if(this.organizationService.keybladeWielderHasOrganization(actual.getId()))
			return new ModelAndView("organization/list");
		
		Organization o = this.organizationService.create();
		result = new ModelAndView("organization/edit");
		result.addObject("organization", o);
		
		return result;
	}
		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Organization organization, BindingResult binding) {
		ModelAndView result;
		Organization org = this.organizationService.reconstruct(organization, binding);
		
		if(binding.hasErrors()){
			result = createEditModelAndView(organization);
		}else{
			try{
				Organization toSave = org;
				
				toSave = this.organizationService.save(org);
				
				result = new ModelAndView("redirect:/organization/membersList.do?organizationId="+toSave.getId());
				result.addObject("members", this.keybladeWielderService.findMembersOfOrganization(organization.getId()));
			}catch(Throwable oops){
				result = createEditModelAndView(organization, "organization.commit.error");
			}
		}
		return result;
	}
	
	private ModelAndView createEditModelAndView(Organization organization) {
		
		return createEditModelAndView(organization, null);
	}

	private ModelAndView createEditModelAndView(Organization organization, String msg) {
		ModelAndView result;
		result = new ModelAndView("organization/edit");

		result.addObject("organization", organization);
		result.addObject("message", msg);
		return result;
	}

	
		
}

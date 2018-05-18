/*
 * ProfileController.java
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.OrganizationService;
import domain.Actor;
import domain.KeybladeWielder;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private OrganizationService	organizationService;


	// Actor ---------------------------------------------------------------		

	@RequestMapping(value = "/actor/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) String username) {
		ModelAndView result;
		Actor actor;

		if (username == null)
			actor = this.actorService.findByPrincipal();
		else
			try {
				actor = this.actorService.findByUserAccountUsername(username);
			} catch (Throwable oops) { //Si mete un username invalido (nulo o no dentro de los limites [3, 32]), mostrar error o alternativa
				actor = this.actorService.findByPrincipal();
			}

		result = new ModelAndView("profile/actor/display");
		if (actor instanceof KeybladeWielder) {
			KeybladeWielder user = (KeybladeWielder) actor;
			result.addObject("user", user);
			result.addObject("usernameInvitation", user.getUserAccount().getUsername());
			if (username != null){
			result.addObject("hasOrganization", this.organizationService.keybladeWielderHasOrganization(user.getId()));
			result.addObject("puedoEnviarInvitation", this.organizationService.getCanSendInvitation());
			System.out.println(this.organizationService.keybladeWielderHasOrganization(user.getId()));
			System.out.println(this.organizationService.getCanSendInvitation());
			}
		} else
			result.addObject("user", actor);

		return result;
	}
}

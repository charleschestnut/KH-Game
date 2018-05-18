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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.OrganizationService;
import domain.Actor;
import domain.KeybladeWielder;
import form.ActorForm;

@Controller
@RequestMapping("/profile/actor")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private OrganizationService	organizationService;


	// Actor ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
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

			//from: Carlos
			if (username != null) {
				result.addObject("hasOrganization", this.organizationService.keybladeWielderHasOrganization(user.getId()));
				result.addObject("puedoEnviarInvitation", this.organizationService.getCanSendInvitation());
				System.out.println(this.organizationService.keybladeWielderHasOrganization(user.getId()));
				System.out.println(this.organizationService.getCanSendInvitation());
			}
			//end
		} else
			result.addObject("user", actor);

		return result;
	}

	// Edition  -------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Actor actor;
		ActorForm form = new ActorForm();

		actor = this.actorService.findOne(this.actorService.findByPrincipal().getId());

		form.setName(actor.getName());
		form.setSurname(actor.getSurname());
		form.setPhone(actor.getPhone());
		form.setEmail(actor.getEmail());
		form.setAvatar(actor.getAvatar());
		form.setUsername(actor.getUserAccount().getUsername());

		result = new ModelAndView("profile/actor/edit");
		result.addObject("actorForm", form);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ActorForm form, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndViewForm(form);
		else
			try {

				Actor actorReconstructed = this.actorService.reconstruct(form, binding);
				this.actorService.save(actorReconstructed);
				result = new ModelAndView("redirect:display.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndViewEdit(form, this.getErrorMessage(oops));
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Actor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Actor actor, String message) {
		ModelAndView result;

		result = new ModelAndView("profile/actor/register");
		result.addObject("actor", actor);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndViewEdit(ActorForm actor, String message) {
		ModelAndView result;

		result = new ModelAndView("profile/actor/edit");
		result.addObject("actorForm", actor);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndViewForm(ActorForm formServicio) {
		ModelAndView result;
		result = this.createEditModelAndViewForm(formServicio, null);
		return result;
	}

	protected ModelAndView createEditModelAndViewForm(ActorForm form, String message) {
		ModelAndView result;

		result = new ModelAndView("profile/actor/edit");
		result.addObject("actorForm", form);
		result.addObject("message", message);

		return result;
	}

}

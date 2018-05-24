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

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.BuiltService;
import services.ContentManagerService;
import services.FactionService;
import services.GameMasterService;
import services.KeybladeWielderService;
import services.OrganizationService;
import domain.Actor;
import domain.KeybladeWielder;
import form.ActorForm;

@Controller
@RequestMapping("/profile/actor")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private KeybladeWielderService	keybladeWielderService;

	@Autowired
	private GameMasterService		gameMasterService;

	@Autowired
	private ContentManagerService	contentManagerService;

	@Autowired
	private OrganizationService		organizationService;

	@Autowired
	private BuiltService			builtService;

	@Autowired
	private FactionService			factionService;


	// Actor ---------------------------------------------------------------	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<KeybladeWielder> players;

		players = this.keybladeWielderService.findAll();

		result = new ModelAndView("profile/actor/list");
		result.addObject("players", players);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) String username, Locale locale) {
		ModelAndView result;
		Actor actor;

		if (username == null)
			actor = this.actorService.findByPrincipal();
		else
			try {
				actor = this.actorService.findByUserAccountUsername(username);
			} catch (Throwable oops) { //Si mete un username invalido (nulo o no dentro de los limites [3, 32]), mostrar error o alternativa
				result = new ModelAndView("redirect:list.do");

				result.addObject("message", this.getErrorMessage(oops));
				return result;
			}

		result = new ModelAndView("profile/actor/display");
		if (actor instanceof KeybladeWielder) {
			KeybladeWielder user = (KeybladeWielder) actor;
			result.addObject("user", user);
			result.addObject("maxMaterial", this.builtService.maxMaterials());
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

	// Sing Up

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam(required = false) String accountType) {
		ModelAndView result;
		Actor actor;

		try {
			if (LoginService.getPrincipal().isAuthority(Authority.ADMIN)) {
				if (accountType == null || !((accountType.equals("GM") && !accountType.equals("MANAGER")) || (!accountType.equals("GM") && accountType.equals("MANAGER")))) {
					result = new ModelAndView("welcome/index");
					result.addObject("message", "error.message.commit");
					return result;
				}
			} else
				new Throwable();
			//Si no es ADMIN (Aqui no deberia llegar, porque solo puede entrar ADMIN o anonimos, y si es anonimo, salta el if y va al catch)

		} catch (Throwable oops) {
			accountType = "PLAYER";
		}

		actor = this.actorService.create(accountType);
		result = new ModelAndView("profile/actor/register");
		result.addObject("actor", actor);
		if (accountType == "PLAYER")
			result.addObject("factions", this.factionService.findAll());
		return result;
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView signingup(Actor actor, @ModelAttribute("worldName") String worldName, @ModelAttribute("factionId") String factionId, BindingResult binding) {
		ModelAndView result;

		actor = this.actorService.reconstruct(actor, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(actor, "error.message.commit");
		else
			try {
				if (actor.getUserAccount().isAuthority(Authority.GM))
					this.gameMasterService.saveFromCreate(actor);
				else if (actor.getUserAccount().isAuthority(Authority.MANAGER))
					this.contentManagerService.saveFromCreate(actor);
				else if (actor.getUserAccount().isAuthority(Authority.PLAYER))
					this.keybladeWielderService.saveFromCreate(actor, worldName, factionId);
				else
					new Throwable("error.message.commit");

				if (LoginService.getPrincipal().isAuthority(Authority.ADMIN))
					result = new ModelAndView("welcome/index");
				else
					result = new ModelAndView("redirect:/security/login.do");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(actor, this.getErrorMessage(oops));
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
		if (actor.getUserAccount().isAuthority("PLAYER"))
			result.addObject("factions", this.factionService.findAll());

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

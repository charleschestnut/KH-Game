/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.security.Security;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.BuiltService;
import domain.KeybladeWielder;

@Controller
public class AbstractController {
	
	@Autowired
	private ActorService		actorService;
	@Autowired
	private BuiltService			builtService;

	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	public String getErrorMessage(final Throwable oops) {
		String res = oops.getMessage();

		if (res == null || !res.startsWith("error.message."))
			res = "error.message.commit";

		return res;
	}
	
	@ModelAttribute
	public void addPlayerToModel(Model model){
		SecurityContext context;
		Boolean anonymous;
		
		try{
		context = SecurityContextHolder.getContext();
		anonymous = context.getAuthentication().getPrincipal().equals("anonymousUser");
		
			if(!anonymous && actorService.getPrincipalAuthority().equals(Authority.PLAYER)){
				KeybladeWielder player;
				
				player = (KeybladeWielder) actorService.findByPrincipal();
				
				model.addAttribute("playerFromAbstract", player);
				model.addAttribute("maxMaterialsFromAbstract", builtService.maxMaterials());
			}
		}catch(Throwable e){
			System.out.println("oh baia");
		}
	}
}

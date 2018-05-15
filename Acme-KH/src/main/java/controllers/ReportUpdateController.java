package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ReportUpdateService;
import domain.ReportStatus;
import domain.ReportUpdate;

@Controller()
@RequestMapping("/reportUpdate")
public class ReportUpdateController extends AbstractController {
	
	@Autowired
	private ReportUpdateService	reportUpdateService;
	
	@Autowired
	private ActorService		actorService;

	// Listing ----------------------------------------------------------------

//	@RequestMapping(value = "/gm/list", method = RequestMethod.GET)
//	public ModelAndView list() {
//		ModelAndView result;
//		Collection<Report> reports;
//
//		reports = reportService.findAll();
//		result = new ModelAndView("report/list");
//		result.addObject("reports", reports);
//
//		return result;
//	}
//	
//	@RequestMapping(value = "/player/list", method = RequestMethod.GET)
//	public ModelAndView playerList() {
//		ModelAndView result;
//		Collection<Report> reports;
//
//		reports = reportService.findReportsByPlayer(actorService.findByPrincipal().getId());
//		result = new ModelAndView("report/list");
//		result.addObject("reports", reports);
//
//		return result;
//	}
	
	// Display ----------------------------------------------------------------
//	@RequestMapping(value = "display", method = RequestMethod.GET)
//	public ModelAndView display(@RequestParam(required = false) Integer reportId) {
//		ModelAndView result;
//		Report report;
//		String auth;
//		
//		auth = actorService.getPrincipalAuthority();
//		Assert.isTrue(auth.equals("PLAYER") || auth.equals("GM"));
//
//		report = reportService.findOne(reportId);
//		
//		result = new ModelAndView("report/display");
//		
//		
//		result.addObject("report",report);
//		
//		return result;
//	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int reportId) {
		ModelAndView result;
		ReportUpdate reportUpdate;

		reportUpdate = reportUpdateService.create();
		result = createEditModelAndView(reportUpdate);
		result.addObject("reportId",reportId);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int reportId) {
		ModelAndView result;
		ReportUpdate report;

		report = reportUpdateService.findOne(reportId);
		Assert.notNull(report);
		result = createEditModelAndView(report);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ReportUpdate report, BindingResult binding, @ModelAttribute("reportId") String reportId,
			@ModelAttribute("status") String status) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = createEditModelAndView(report);
		} else {
			try {
				reportUpdateService.save(report,new Integer(reportId),ReportStatus.valueOf(status));
				result = new ModelAndView("redirect:/report/gm/list.do");
				
			} catch (Throwable oops) {
				String message = "error.commit";
				
				if(oops.getMessage().contains("error.message") || oops.getMessage().contains("org.hibernate.validator")){
					message = oops.getMessage();
				}
				
				result = createEditModelAndView(report, message);
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(ReportUpdate report, BindingResult binding) {
		ModelAndView result;

		try {
			reportUpdateService.delete(report);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(report, "bulletin.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(ReportUpdate reportUpdate) {
		ModelAndView result;

		result = createEditModelAndView(reportUpdate, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ReportUpdate reportUpdate,
			String message) {
		ModelAndView result;

		result = new ModelAndView("reportUpdate/edit");
		result.addObject("reportUpdate", reportUpdate);
		result.addObject("message", message);

		return result;
	}

}

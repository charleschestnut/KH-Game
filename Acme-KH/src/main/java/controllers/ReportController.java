package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Report;

import security.Authority;
import services.ActorService;
import services.ReportService;

@Controller()
@RequestMapping("/report")
public class ReportController extends AbstractController {
	
	@Autowired
	private ReportService	reportService;
	
	@Autowired
	private ActorService		actorService;

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/gm/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Report> reports;

		reports = reportService.findAll();
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);

		return result;
	}
	
	@RequestMapping(value = "/player/list", method = RequestMethod.GET)
	public ModelAndView playerList() {
		ModelAndView result;
		Collection<Report> reports;

		reports = reportService.findReportsByPlayer(actorService.findByPrincipal().getId());
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/player/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Report report;

		report = reportService.create();
		result = createEditModelAndView(report);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int reportId) {
		ModelAndView result;
		Report report;

		report = reportService.findOne(reportId);
		Assert.notNull(report);
		result = createEditModelAndView(report);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Report report, BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = createEditModelAndView(report);
		} else {
			try {
				reportService.save(report);
				if(actorService.getPrincipalAuthority().equals(Authority.PLAYER)){
					result = new ModelAndView("redirect:player/list.do");
					result.addObject("requestURI", "player/list.do");
				}else if(actorService.getPrincipalAuthority().equals(Authority.GM)){
					result = new ModelAndView("redirect:gm/list.do");
					result.addObject("requestURI", "gm/list.do");
				}
				
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
	public ModelAndView delete(Report report, BindingResult binding) {
		ModelAndView result;

		try {
			reportService.delete(report);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(report, "bulletin.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Report report) {
		ModelAndView result;

		result = createEditModelAndView(report, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Report report,
			String message) {
		ModelAndView result;

		result = new ModelAndView("report/edit");
		result.addObject("report", report);
		result.addObject("message", message);

		return result;
	}

}
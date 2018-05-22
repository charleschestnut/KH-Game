package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ReportService;
import domain.Actor;
import domain.Report;
import domain.ReportStatus;

@Controller()
@RequestMapping("/report")
public class ReportController extends AbstractController {
	
	@Autowired
	private ReportService	reportService;
	
	@Autowired
	private ActorService		actorService;

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Report> reports;

		reports = reportService.findAll();
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("user", "all");

		return result;
	}
	
	@RequestMapping(value = "/listByStatus", method = RequestMethod.GET)
	public ModelAndView listByStatus(@RequestParam (required = false) String status) {
		ModelAndView result;
		Collection<Report> reports;

		if(status != null){
			reports = reportService.getReportsByStatus(ReportStatus.valueOf(status));
		}else{
			reports = reportService.findAll();
		}
		
		result = new ModelAndView("report/table");
		result.addObject("reports", reports);
		result.addObject("user", "all");

		return result;
	}
	
	@RequestMapping(value = "/player/list", method = RequestMethod.GET)
	public ModelAndView playerList() {
		ModelAndView result;
		Collection<Report> reports;

		reports = reportService.findReportsByPlayer(actorService.findByPrincipal().getId());
		result = new ModelAndView("report/list");
		result.addObject("requestURI", "report/player/list.do");
		result.addObject("reports", reports);
		result.addObject("user", "player");

		return result;
	}
	
	@RequestMapping(value = "/player/listByStatus", method = RequestMethod.GET)
	public ModelAndView playerListByStatus(@RequestParam (required = false) String status) {
		ModelAndView result;
		Collection<Report> reports;

		if(!status.equals("all")){
			reports = reportService.getReportsByStatusAndPlayer(ReportStatus.valueOf(status), actorService.findByPrincipal().getId());
		}else{
			reports = reportService.findReportsByPlayer(actorService.findByPrincipal().getId());
		}
		
		result = new ModelAndView("report/table");
		result.addObject("requestURI", "report/player/list.do");
		result.addObject("reports", reports);
		result.addObject("user", "player");

		return result;
	}
	
	// Display ----------------------------------------------------------------
	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) Integer reportId) {
		ModelAndView result;
		Report report;
		String auth;
		
		auth = actorService.getPrincipalAuthority();
		Assert.isTrue(auth.equals("PLAYER") || auth.equals("GM") || auth.equals("ADMIN"));

		report = reportService.findOne(reportId);
		
		if(auth.equals("PLAYER")){
			Assert.isTrue(report.getKeybladeWielder().equals(actorService.findByPrincipal()),"error.message.owner");
		}
		
		result = new ModelAndView("report/display");
		
		
		result.addObject("report",report);
		
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/player/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Report report;
		Assert.isTrue(actorService.getPrincipalAuthority().equals(Authority.PLAYER));

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
	public ModelAndView save(Report report, BindingResult binding) {
		ModelAndView result = null;

		this.reportService.reconstruct(report, binding);
		
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

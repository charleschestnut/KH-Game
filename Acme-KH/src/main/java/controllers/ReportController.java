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

import services.ReportService;

@Controller()
@RequestMapping("/report")
public class ReportController extends AbstractController {
	
	@Autowired
	private ReportService	reportService;

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

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
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
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(report);
		} else {
			try {
				reportService.save(report);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(report,
						"bulletin.commit.error");
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

		result = new ModelAndView("bulletin/edit");
		result.addObject("report", report);
		result.addObject("message", message);

		return result;
	}

}

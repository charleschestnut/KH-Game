
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PrizeService;
import domain.Prize;

@Controller
@RequestMapping("/prize")
public class PrizeController extends AbstractController {

	@Autowired
	private PrizeService	prizeService;


	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res;
		final Collection<Prize> prizes = this.prizeService.getMyPrizes();

		res = new ModelAndView("prize/list");
		res.addObject("prizes", prizes);

		return res;
	}

	@RequestMapping("/open")
	public ModelAndView open(@RequestParam final Integer prizeId) {
		ModelAndView res;
		final Prize p = this.prizeService.findOne(prizeId);

		if (p == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.prizeService.open(p);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:list.do");
			}

		return res;
	}

}

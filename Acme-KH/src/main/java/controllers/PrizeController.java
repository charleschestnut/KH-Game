
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		ModelAndView res;
		Collection<Prize> prizes = this.prizeService.getMyPrizes();

		res = new ModelAndView("prize/list");
		res.addObject("prizes", prizes);

		return res;
	}

	@RequestMapping("/open")
	public ModelAndView open(@RequestParam Integer prizeId) {
		ModelAndView res;
		Prize p = this.prizeService.findOne(prizeId);

		if (p == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.prizeService.open(p);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = new ModelAndView("redirect:list.do");
			}

		return res;
	}

	@RequestMapping(value = "/openAJAX", method = RequestMethod.GET)
	public ModelAndView openAJAX(@RequestParam Integer prizeId) {
		ModelAndView res;
		Prize prize;
		Collection<Prize> prizes;

		prize = this.prizeService.findOne(prizeId);

		if (prize == null)
			res = new ModelAndView("prize/prizes");
		else
			try {
				this.prizeService.open(prize);
				res = new ModelAndView("prize/prizes");
			} catch (Throwable oops) {
				res = new ModelAndView("prize/prizes");
			}

		prizes = this.prizeService.getMyPrizes();
		res.addObject("prizes", prizes);

		return res;
	}

}

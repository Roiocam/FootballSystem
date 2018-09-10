package club.pypzx.FootballSystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "admin")
public class MainController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "admin/index";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main() {
		return "admin/main";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "admin/login";
	}

}

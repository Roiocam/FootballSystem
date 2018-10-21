package club.pypzx.FootballSystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "admin/view")
public class MainController {

	@GetMapping("/index")
	public String index() {
		return "admin/index";
	}

	@GetMapping("/main")
	public String main() {
		return "admin/main";
	}
	
	@GetMapping("/login")
	public String login() {
		return "admin/login";
	}
	/*
	 * 管理系统页面
	 * 
	 * */
	
	@GetMapping("/user")
	public String user() {
		return "admin/user";
	}
	@GetMapping("/cup")
	public String cup() {
		return "admin/cup";
	}
	@GetMapping("/team")
	public String team() {
		return "admin/team";
	}
	@GetMapping("/player")
	public String player() {
		return "admin/player";
	}
	@GetMapping("/game")
	public String game() {
		return "admin/game";
	}
	@GetMapping("/game2")
	public String game2() {
		return "admin/game2";
	}
	


}

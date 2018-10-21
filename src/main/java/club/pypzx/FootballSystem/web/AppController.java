package club.pypzx.FootballSystem.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import club.pypzx.FootballSystem.utils.ParamUtils;

@Controller
@RequestMapping(value = "app/view")
public class AppController {
	@RequestMapping(value = "main")
	public String main() {
		return "app/main";
	}

	@RequestMapping(value = "paper")
	public String paper() {
		return "app/paper";
	}
	@RequestMapping(value = "paperCup")
	public String paperCup() {
		return "app/paperCup";
	}

	@RequestMapping(value = "rank")
	public String rank() {
		return "app/rank";
	}
	@RequestMapping(value = "game")
	public String game() {
		return "app/game";
	}
	@RequestMapping(value = "fixtrue")
	public String fixtrue() {
		return "app/fixtrue";
	}


	@RequestMapping(value = "404")
	public String error() {
		return "app/404";
	}

	@RequestMapping(value = "sign")
	public String sign(HttpServletRequest request) {
		//判断是否为队长
		String teamId =(String) request.getSession().getAttribute("created");
		if(ParamUtils.validString(teamId)) {
			return "app/created";
		}
		//判断是否为队员
		String playerId = (String) request.getSession().getAttribute("signed");
		if(ParamUtils.validString(playerId)) {
			return "app/signed";
		}
		return "app/sign";
	}
	@RequestMapping(value = "live")
	public String live() {
		return "app/live";
	}
	@RequestMapping(value = "create")
	public String create(HttpServletRequest request) {
		String teamId =(String) request.getSession().getAttribute("created");
		if(ParamUtils.validString(teamId)) {
			return "app/created";
		}
		return "app/create";
	}
	@RequestMapping(value = "insert")
	public String insert() {
		return "app/insert";
	}
	@RequestMapping(value = "teamlist")
	public String teamlist() {
		return "app/teamlist";
	}
	@RequestMapping(value = "teamdetail")
	public String teamdetail() {
		return "app/teamdetail";
	}
}

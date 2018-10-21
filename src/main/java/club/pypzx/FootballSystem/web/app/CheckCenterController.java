//package club.pypzx.FootballSystem.web.app;
//
//import java.io.IOException;
//import java.util.Map;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import club.pypzx.FootballSystem.utils.ModelMapUtil;
//
//@Controller
//@RequestMapping("/checkcenter")
//public class CheckCenterController {
//
//	// 页面请求
//	@GetMapping("/socket/{cid}")
//	public ModelAndView socket(@PathVariable String cid) {
//		ModelAndView mav = new ModelAndView("/socket");
//		mav.addObject("cid", cid);
//		return mav;
//	}
//
//	// 推送数据接口
//	@ResponseBody
//	@RequestMapping("/socket/push/{cid}")
//	public Map<String, Object> pushToWeb(@PathVariable String cid, String message) {
//		try {
//			WebSocketServer.sendInfo(message, cid);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return ModelMapUtil.getErrorMap(cid + "#" + e.getMessage());
//		}
//		return ModelMapUtil.getSuccessMap(cid);
//	}
//
//}

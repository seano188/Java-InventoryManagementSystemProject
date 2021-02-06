package sg.edu.iss.team8ca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String login() {
		return "forward:/login";
	}
	
	@RequestMapping("/fixset")
	public String fixset() {
		return "/fixset";
	}
}


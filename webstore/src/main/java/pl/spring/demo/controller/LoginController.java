package pl.spring.demo.controller;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.spring.demo.constants.ViewNames;

@Controller
public class LoginController {
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return ViewNames.LOGIN;
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(Model model) {
		model.addAttribute("error", "true");
		return ViewNames.LOGIN;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "redirect:/";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied(Model model, Principal user) {
		model.addAttribute("userName", user.getName());
		return ViewNames.ERROR_403;
	}
	
}


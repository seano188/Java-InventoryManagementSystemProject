package sg.edu.iss.team8ca.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.team8ca.model.InvUsage;
import sg.edu.iss.team8ca.model.TransHistory;
import sg.edu.iss.team8ca.model.User;
import sg.edu.iss.team8ca.service.InvUsageImpl;
import sg.edu.iss.team8ca.service.InvUsageInterface;
import sg.edu.iss.team8ca.service.TransHistoryImpl;
import sg.edu.iss.team8ca.service.TransHistoryInterface;
import sg.edu.iss.team8ca.service.UserInterface;
import sg.edu.iss.team8ca.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserInterface crudint;

	@Autowired
	public void setUserService(UserService crudservice) {
		this.crudint = crudservice;
	}

	@Autowired
	private TransHistoryInterface transint;

	@Autowired
	public void setTransService(TransHistoryImpl transservice) {
		this.transint = transservice;
	}

	@Autowired
	private InvUsageInterface iuservice;

	@Autowired
	private void setInvUsageService(InvUsageImpl invUsageImpl) {
		this.iuservice = invUsageImpl;
	}

	@RequestMapping(value = "/list")
	public String list(Model model) {
		int pageSize = 5;
		int pageNo = 1;
		String sortField = "id";
		String sortDir = "asc";
		Page<User> page = crudint.findBykeywordContaining("", pageNo, pageSize, sortField, sortDir);
		List<User> userList = page.getContent();
		model.addAttribute("user", userList);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", "");
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		return "users";
	}

	@RequestMapping(value = "/search/page/{pageNo}/{pageSize}", method = RequestMethod.GET)
	public String searchWithPage(@RequestParam("keyword") String keyword, @PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "pageSize") int pageSize, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		if (keyword == null) {
			return "users";
		} else {
			Page<User> page = crudint.findBykeywordContaining(keyword, pageNo, pageSize, sortField, sortDir);
			List<User> userList = page.getContent();

			model.addAttribute("keyword", keyword);
			model.addAttribute("user", userList);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			return "users";
		}
	}

	@RequestMapping(value = "/search/page1")
	public String searchWithPageDropdown(@RequestParam("keyword") String keyword, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		if (keyword == null) {
			return "forward:/user/list";
		} else {
			Page<User> page = crudint.findBykeywordContaining(keyword, pageNo, pageSize, sortField, sortDir);
			List<User> userList = page.getContent();

			model.addAttribute("user", userList);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("keyword", keyword);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			return "users";
		}
	}

	@RequestMapping(value = "/add")
	public String addForm(Model model) {
		model.addAttribute("user", new User());
		return "user-form";
	}

	@RequestMapping(value = "/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", crudint.findUserById(id));
		return "user-form";
	}

	@RequestMapping(value = "/save")
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "user-form";
		}
		crudint.saveUser(user);
		return "forward:/user/list";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
		List<TransHistory> thList = transint.listTransForUser(id);
		List<InvUsage> iuList = iuservice.findUsageByUser(id);
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = crudint.findUserByUserName(currentUserName);
		if (iuList.size() > 0 && thList.size() > 0) {
			model.addAttribute("error", "thiu-exist");
			return list(model);
		} else if (user.getId() == id) {
			model.addAttribute("error", "user-exist");
			return list(model);
		}

		crudint.deleteUser(crudint.findUserById(id));
		return "forward:/user/list";
	}

}
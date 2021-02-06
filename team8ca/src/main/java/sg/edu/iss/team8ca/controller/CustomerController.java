package sg.edu.iss.team8ca.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.team8ca.model.Customer;
import sg.edu.iss.team8ca.model.InvUsage;
import sg.edu.iss.team8ca.service.CustomerImpl;
import sg.edu.iss.team8ca.service.CustomerInterface;
import sg.edu.iss.team8ca.service.InvUsageImpl;
import sg.edu.iss.team8ca.service.InvUsageInterface;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerInterface cuservice;

	@Autowired
	public void setCustomerService(CustomerImpl customerImpl) {
		this.cuservice = customerImpl;
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
		Page<Customer> page = cuservice.cusSearchPage("", pageNo, pageSize, sortField, sortDir);
		List<Customer> cusList = page.getContent();
		model.addAttribute("cusList", cusList);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		return "CustomerListing";
	}

	@RequestMapping(value = "/search/page/{pageNo}/{pageSize}", method = RequestMethod.GET)
	public String searchWithPage(@RequestParam ("keyword") String keyword, @PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "pageSize") int pageSize, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		if (keyword == null) {
			return "CustomerListing";
		} else {
			Page<Customer> page = cuservice.cusSearchPage(keyword, pageNo, pageSize, sortField, sortDir);
			List<Customer> cusList = page.getContent();

			model.addAttribute("cusList", cusList);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("keyword", keyword);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			return "CustomerListing";
		}
	}
	
	@RequestMapping(value = "/search/page1")
	public String searchWithPageDropdown(@RequestParam ("keyword") String keyword, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		if (keyword == null) {
			return "forward:/customer/list";
		} else {
			Page<Customer> page = cuservice.cusSearchPage(keyword, pageNo, pageSize, sortField, sortDir);
			List<Customer> cusList = page.getContent();

			model.addAttribute("cusList", cusList);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("keyword", keyword);
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			return "CustomerListing";
		}
	}
	
	@RequestMapping(value = "/add")
	public String addCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "CustomerForm";
	}

	@RequestMapping(value = "/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("customer", cuservice.findCustomerById(id));
		return "CustomerForm";
	}

	@RequestMapping(value = "/save")
	public String saveCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "CustomerForm";
		}
		cuservice.saveCustomer(customer);
		return "forward:/customer/list";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteCustomer(@PathVariable("id") Long id, Model model) {
		List<InvUsage> iuList = iuservice.findUsageByCus(id);
		if(iuList.size()>0) {
			model.addAttribute("error", "IU-exist");
			return list(model);
		}
			cuservice.deleteCustomer(cuservice.findCustomerById(id));
			return "forward:/customer/list";
		
	}
}

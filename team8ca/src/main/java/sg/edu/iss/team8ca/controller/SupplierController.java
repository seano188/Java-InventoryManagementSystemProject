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

import sg.edu.iss.team8ca.model.Brand;
import sg.edu.iss.team8ca.model.Supplier;
import sg.edu.iss.team8ca.service.SupplierInterface;
import sg.edu.iss.team8ca.service.SupplierService;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierInterface supint;

	@Autowired
	public void setMemberService(SupplierService supservice) {
		this.supint = supservice;
	}

	@RequestMapping(value = "/list")
	public String list(Model model) {
		int pageSize = 5;
		int pageNo = 1;
		String sortField = "id";
		String sortDir = "asc";
		Page<Supplier> page = supint.findBykeywordContaining("", pageNo, pageSize, sortField, sortDir);
		List<Supplier> supList = page.getContent();
		model.addAttribute("supplier", supList);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", "");
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		return "supplier";
	}

	@RequestMapping(value = "/search/page/{pageNo}/{pageSize}", method = RequestMethod.GET)
	public String searchWithPage(@RequestParam("keyword") String keyword, @PathVariable(value = "pageNo") int pageNo,
			@PathVariable(value = "pageSize") int pageSize, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		if (keyword == null) {
			return "supplier";
		} else {

			Page<Supplier> page = supint.findBykeywordContaining(keyword, pageNo, pageSize, sortField, sortDir);
			List<Supplier> supList = page.getContent();

			model.addAttribute("keyword", keyword);
			model.addAttribute("supplier", supList);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			return "supplier";
		}
	}

	@RequestMapping(value="/search/page1")

	public String searchWithPageDropdown(@RequestParam("keyword") String keyword, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {
		if (keyword == null) {
			return "forward:/supplier/list";
		} else {
			Page<Supplier> page = supint.findBykeywordContaining(keyword, pageNo, pageSize, sortField, sortDir);
			List<Supplier> supList = page.getContent();

			model.addAttribute("keyword", keyword);
			model.addAttribute("supplier", supList);
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			return "supplier";
		}
	}

	@RequestMapping(value = "/add")
	public String addForm(Model model) {
		model.addAttribute("supplier", new Supplier());
		return "SupplierForm";
	}

	@RequestMapping(value = "/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("supplier", supint.findSupplierById(id));
		return "SupplierForm";
	}

	@RequestMapping(value = "/save")
	public String saveSupplier(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "SupplierForm";
		}
		supint.saveSupplier(supplier);
		return "forward:/supplier/list";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteSupplier(@PathVariable("id") Long id, Model model) {
		List<Brand> brands = supint.findBrandBySupplier(id);
		if (brands.size() > 0) {
			model.addAttribute("error", "brand-exist");
			return list(model);
		}

		supint.deleteSupplier(supint.findSupplierById(id));
		return "forward:/supplier/list";
	}

}

package sg.edu.iss.team8ca.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.team8ca.model.Brand;
import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.model.Supplier;
import sg.edu.iss.team8ca.service.ProductListingImpl;
import sg.edu.iss.team8ca.service.SupplierService;

@Controller
@RequestMapping("/brand")
public class BrandController {
	@Autowired
	private ProductListingImpl plService;
	@Autowired
	private SupplierService supService;

	@RequestMapping(value = "/add")
	public String addBrand(Model model) {
		Brand brand = new Brand();
		List<Brand> blist = plService.listBrand();
		ArrayList<String> suplist = supService.findAllSupplierNames();
		model.addAttribute("brand", brand);
		model.addAttribute("blist", blist);
		model.addAttribute("supnames", suplist);
		return "add-brand";
	}

	@RequestMapping(value = "/save")
	public String saveBrand(@ModelAttribute("brand") Brand brand, Model model, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "add-brand";
		}
		Supplier supplier = supService.findSupplierByName(brand.getSupplier().getCompanyName());
		brand.setSupplier(supplier);
		plService.addBrand(brand);
		return "forward:/brand/add";
	}

	@RequestMapping(value = "/delete/{id}")
	public String deleteBrand(@PathVariable Long id, Model model) {
		List<Inventory> inventories = plService.findProductByBrand(id);
		if (inventories.size() > 0) {
			model.addAttribute("error", "inv-exist");
			return addBrand(model);
		} else {
			plService.deleteBrand(plService.findBrandById(id));
			return "forward:/brand/add";
		}
	}
}

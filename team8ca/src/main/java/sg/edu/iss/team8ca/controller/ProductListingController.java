package sg.edu.iss.team8ca.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import sg.edu.iss.team8ca.model.Brand;
import sg.edu.iss.team8ca.model.Category;
import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.model.Subcategory;
import sg.edu.iss.team8ca.model.Supplier;
import sg.edu.iss.team8ca.model.TransHistory;
import sg.edu.iss.team8ca.model.TransType;
import sg.edu.iss.team8ca.model.UsageDetails;
import sg.edu.iss.team8ca.model.User;
import sg.edu.iss.team8ca.service.InvUsageImpl;
import sg.edu.iss.team8ca.service.InvUsageInterface;
import sg.edu.iss.team8ca.service.ProductListingImpl;
import sg.edu.iss.team8ca.service.ProductListingInterface;
import sg.edu.iss.team8ca.service.ReorderReportService;
import sg.edu.iss.team8ca.service.SupplierInterface;
import sg.edu.iss.team8ca.service.SupplierService;
import sg.edu.iss.team8ca.service.TransHistoryImpl;
import sg.edu.iss.team8ca.service.TransHistoryInterface;
import sg.edu.iss.team8ca.service.UserInterface;
import sg.edu.iss.team8ca.service.UserService;

@Controller
@RequestMapping("/inventory")
public class ProductListingController {  
	
	@Autowired
	private SupplierInterface spservice;
	
	@Autowired
	private void setSupplierService(SupplierService supplierImpl) {
		this.spservice = supplierImpl;
	}
	
	@Autowired
	private ReorderReportService reorser;
	
	@Autowired
	private ProductListingInterface plService;
	
	@Autowired
	private void setProductListingService(ProductListingImpl plImpl) {
		this.plService = plImpl;
	};
	
	@Autowired
	private UserInterface uservice;

	@Autowired
	private void setUserService(UserService userImpl) {
		this.uservice = userImpl;
	}
	
	@Autowired
	private TransHistoryInterface thservice;
	
	@Autowired
	private void setTransHistoryService(TransHistoryImpl thImpl) {
		this.thservice=thImpl;
	}
	
	@Autowired
	private InvUsageInterface iuservice;
	
	@Autowired
	private void setInvUsageService (InvUsageImpl iuImpl) {
		this.iuservice = iuImpl;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		int pageSize = 5;
		int pageNo = 1;
		String sortField = "id";
		String sortDir = "asc";
		Page<Inventory> page = plService.findPaginated("", pageNo, pageSize, sortField, sortDir);
		List<Inventory> plist = page.getContent(); 
		LocalDate today = LocalDate.now();
		model.addAttribute("plist", plist);
		model.addAttribute("today", today.toString());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", "");
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	
		return "product-listing";
	}
	

	@RequestMapping(value = "/search/page/{pageNo}/{pageSize}", method = RequestMethod.GET)
	public String searchWithPage(@Param("keyword") String keyword ,@PathVariable ( value = "pageNo") int pageNo, 
			@PathVariable ( value = "pageSize") int pageSize, 
			@RequestParam ("sortField") String sortField,
			@RequestParam ("sortDir")String sortDir, Model model)  {
		
		Page<Inventory> page = plService.findPaginated(keyword, pageNo, pageSize, sortField, sortDir);
		List<Inventory> plist = page.getContent();
		
		if(keyword.equals(null)) 
		{
			keyword = "";
		}
		LocalDate today = LocalDate.now();
		model.addAttribute("plist", plist);
		model.addAttribute("today", today.toString());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		return "product-listing";
	}

	@RequestMapping(value = "/addproduct")
	public String addProduct(Model model) {
		Inventory inventory = new Inventory();
		ArrayList<String> blist = plService.findAllBrandNames();
		ArrayList<String> slist = plService.findAllSubcatNames();
		ArrayList<String> splist = spservice.findAllSupplierNames();
		ArrayList<String> clist = plService.findAllCategoryNames(); 
		model.addAttribute("inventory", inventory);
		model.addAttribute("bnames", blist);
		model.addAttribute("snames", slist);
		model.addAttribute("spnames", splist);
		model.addAttribute("cnames", clist);
		model.addAttribute("addOrEdit", "add");
		return "entry-form";
	}
	
	@RequestMapping(value = "/saveproduct")
	public String saveProduct(@ModelAttribute("inventory") @Valid Inventory inventory,
			BindingResult bindingResult, Model model, WebRequest request) {
		
		if (bindingResult.hasErrors()) {
			return "entry-form";
		}
				
		if(request.getParameter("newBrand").equals("false")==true) {
			Brand brand = plService.findBrandByName(inventory.getBrand().getBrandName());
			inventory.setBrand(brand);
		}else{
			String newBrandName = request.getParameter("newBrandName");
			String newBrandManu = request.getParameter("manufacturerName");

			if(request.getParameter("newSupplier").equals("false")==true) 
			{
				Supplier supplier = spservice.findSupplierByName(request.getParameter("companyName"));
				Brand brand = new Brand(newBrandName,newBrandManu,supplier);
				plService.addBrand(brand);
				inventory.setBrand(brand);
			}else{
				String newCompanyName = request.getParameter("newCompanyName");
				String contactNo = request.getParameter("contactNo");
				String address = request.getParameter("address");
				String email = request.getParameter("email");
				int postalCode = Integer.parseInt(request.getParameter("postalCode"));
				Supplier supplier = new Supplier(newCompanyName,contactNo,email,address,postalCode);
				spservice.saveSupplier(supplier);
				Brand brand = new Brand(newBrandName,newBrandManu,supplier);
				plService.addBrand(brand);
				inventory.setBrand(brand);
			}
		}
			if(request.getParameter("newSubcat").equals("false")==true) {
				Subcategory subcat = plService.findSubcatByName(inventory.getSubcategory().getSubcategoryName());
				inventory.setSubcategory(subcat);
				
			}else{
			
				String newSubcatName = request.getParameter("newSubcatName");
				String newSubcatType = request.getParameter("newSubcatType");

				if(request.getParameter("newCategory").equals("false")==true) 
				{
					Category category = plService.findCatByName(request.getParameter("categoryName"));
					Subcategory subcat = new Subcategory(newSubcatName, newSubcatType, category);
					plService.addSubcategory(subcat);
					inventory.setSubcategory(subcat);
					
				}else{
					
					String newCategoryName = request.getParameter("newCategoryName");
					Category category = new Category(newCategoryName);
					plService.addCategory(category);
					Subcategory subcat = new Subcategory(newSubcatName, newSubcatType, category);
					plService.addSubcategory(subcat);
					inventory.setSubcategory(subcat);
				}
			}
			
		Subcategory subcategory = plService.findSubcatByName(inventory.getSubcategory().getSubcategoryName());
		inventory.setSubcategory(subcategory);
				
//		//Add to transHistory	
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = uservice.findUserByUserName(currentUserName);
		if(model.getAttribute("addOrEdit")=="add"){
			String s = LocalTime.now(ZoneId.of("Asia/Singapore")).format(DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime localtime = LocalTime.parse(s);
			TransHistory trans = new TransHistory(TransType.NewInventory, Math.toIntExact(inventory.getStockQty()), inventory, LocalDate.now(), localtime, user);
			plService.saveProduct(inventory);
			thservice.saveTrans(trans);
			
		}else {
			plService.saveProduct(inventory);
			String s = LocalTime.now(ZoneId.of("Asia/Singapore")).format(DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime localtime = LocalTime.parse(s);
			TransHistory trans = new TransHistory(TransType.UpdateInventory, Math.toIntExact(0), inventory, LocalDate.now(), localtime, user);
			thservice.saveTrans(trans);	
		}
		return "redirect:/inventory/list";
	
	}
	
	@RequestMapping(value = "/editproduct/{id}", method = RequestMethod.GET)
	public String editProduct(@PathVariable ( value = "id") long id, Model model) {
		ArrayList<String> blist = plService.findAllBrandNames();
		ArrayList<String> slist = plService.findAllSubcatNames();
		ArrayList<String> splist = spservice.findAllSupplierNames();
		ArrayList<String> clist = plService.findAllCategoryNames(); 
		model.addAttribute("inventory", plService.findProductById(id));
		model.addAttribute("bnames", blist);
		model.addAttribute("snames", slist);
		model.addAttribute("spnames", splist);
		model.addAttribute("cnames", clist);
		model.addAttribute("addOrEdit", "edit");
		return "entry-form";	
		}

	@RequestMapping(value = "/deleteproduct/{id}", method = RequestMethod.GET)		
		public String deleteProduct(@PathVariable (value="id") Long id, Model model) {
			List<UsageDetails> udList = iuservice.listUsageForInv(id);
			if(udList.size()>0) {
				model.addAttribute("error", "usage-exist");
				return list(model);
			}
			else {
				plService.deleteProduct(plService.findProductById(id));
				return "redirect:/inventory/list";	
			}
	}
		
	@RequestMapping("/search")
	public String search(Model model, @Param("keyword") String keyword) {
		List<Inventory> plist = plService.list(keyword);
		LocalDate today = LocalDate.now();
		model.addAttribute("plist", plist);
		model.addAttribute("today", today.toString());
		model.addAttribute("keyword", keyword);
		return "product-listing";
	}
	
	@RequestMapping("/select")
	public String selectSupplier(Model model) {
		model.addAttribute("supplier", spservice.findAllSupplier());
		return "reorderreport";
	}
	
	@RequestMapping("/report/{id}")
	public String reorderReport(@PathVariable("id") long id,Model model) {
		model.addAttribute("message",reorser.printDatFile(id));
		return "message";
	}
	
	@RequestMapping(value = "/reorderlist", method = RequestMethod.GET)
	public String reorderList(Model model) {
		List<Inventory> plist = plService.list();
		model.addAttribute("plist", plist);
		model.addAttribute("error", null);
		
		return "reorder-product";
	}
	
	@RequestMapping("/search/reorder")
	public String searchReorder(Model model, @Param("keyword") String keyword) {
		List<Inventory> plist = plService.list(keyword);
		LocalDate today = LocalDate.now();
		model.addAttribute("plist", plist);
		model.addAttribute("today", today.toString());
		model.addAttribute("keyword", keyword);
		return "reorder-product";
	}
	
	@RequestMapping(value = "/reorder/{id}", method = RequestMethod.GET)
	public String reorderProduct(@PathVariable("id") Long id, 
			@RequestParam("inv_quantity") int quantity, Model model) {
		
		Inventory inv = plService.findProductById(id);
		
		int minOrder = inv.getMinimumOrder();
		int invQuantity = inv.getStockQty();
		int newInvQuantity = invQuantity + quantity;
		
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = uservice.findUserByUserName(currentUserName);

		if (quantity >= minOrder) {
			inv.setStockQty(newInvQuantity);
			plService.saveProduct(inv);
			model.addAttribute("error", null);
			String s = LocalTime.now(ZoneId.of("Asia/Singapore")).format(DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime localtime = LocalTime.parse(s);
			TransHistory trans = new TransHistory(TransType.ReStock, quantity, inv, LocalDate.now(), localtime, user);
			thservice.saveTrans(trans);
			return "forward:/inventory/reorderlist";
		}
		else {
			List<Inventory> plist = plService.list();			
			model.addAttribute("error", "qtyerror");
			model.addAttribute("plist", plist);
		return "reorder-product";
		}
	}
}

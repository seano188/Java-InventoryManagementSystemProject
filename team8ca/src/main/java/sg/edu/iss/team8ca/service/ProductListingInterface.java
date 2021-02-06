package sg.edu.iss.team8ca.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.team8ca.model.Brand;
import sg.edu.iss.team8ca.model.Category;
import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.model.Subcategory;

public interface ProductListingInterface {
	
	public List<Inventory> list();
	public void saveProduct(Inventory inventory);
	public void editProduct(Inventory inventory);
	public void addProduct(Inventory inventory);
	public void deleteProduct(Inventory inventory);
	public Inventory findProductById(Long id);
	public void editProductQuantity(Long id, int newQty);
	public List<Inventory> findProductBySubCat(long subcatId);
	public List<Inventory> findProductByCat(long catId);
	public List<Inventory> findProductByBrand(long brandId);

	public List<Brand> listBrand();
	public void addBrand(Brand brand);
	public void deleteBrand(Brand brand);
	public ArrayList<String> findAllBrandNames();
	public Brand findBrandByName(String name);
	public Brand findBrandById(Long id);
	public List<Inventory> list(String keyword);
  
	public List<Subcategory> listSubcategory();
	public void addSubcategory(Subcategory subcategory);
	public void deleteSubcategory(Subcategory subcategory);
	public ArrayList<String> findAllSubcatNames();
	public Subcategory findSubcatByName(String name);
	public Subcategory findSubcatById(Long id);
	
	public void addCategory(Category category);
	public void deleteCategory(Category category);
	public List<Category> listCategory();
	public ArrayList<String> findAllCategoryNames();
	public Category findCatByName(String name);

	public Category findCategoryById(Long id);
	public Inventory getProduct(long id);
	public List<Subcategory> findSubcatByCatId(Long id);
	
	public void deleteProducts(List<Inventory> invList);
   
	
	public Page<Inventory> findPaginated(String keyword, int pageNo, int pageSize, String sortField, String sortDirection);
	

}


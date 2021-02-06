package sg.edu.iss.team8ca.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.team8ca.model.Brand;
import sg.edu.iss.team8ca.model.Supplier;

public interface SupplierInterface {

	public boolean saveSupplier(Supplier sup);
	public void deleteSupplier(Supplier sup);
	public List<Supplier> findAllSupplier();
	public Supplier findSupplierById(long id);
	public ArrayList<String> findAllSupplierNames();
	public Supplier findSupplierByName(String name);
	public Page<Supplier> findBykeywordContaining(String string, int pageNo, int pageSize, String sortField,
			String sortDir);
	public List<Brand> findBrandBySupplier(Long id);
}

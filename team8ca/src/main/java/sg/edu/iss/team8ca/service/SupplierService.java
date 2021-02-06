package sg.edu.iss.team8ca.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.team8ca.model.Brand;
import sg.edu.iss.team8ca.model.Supplier;
import sg.edu.iss.team8ca.repo.BrandRepo;
import sg.edu.iss.team8ca.repo.SupplierRepo;

@Service
public class SupplierService implements SupplierInterface {

	@Autowired
	SupplierRepo srepo;
	
	@Autowired
	BrandRepo brepo;

	@Override
	@Transactional
	public boolean saveSupplier(Supplier sup) {
		if (srepo.save(sup) != null)
			return true;
		else
			return false;
	}

	@Override
	@Transactional
	public void deleteSupplier(Supplier sup) {
		srepo.delete(sup);

	}

	@Override
	@Transactional (readOnly = true)
	public List<Supplier> findAllSupplier() {
		return srepo.findAll();
	}

	@Override
	@Transactional
	public Supplier findSupplierById(long id) {
		return srepo.findById(id).get();
	}

	@Transactional
	public ArrayList<String> findAllSupplierNames() {
		List<Supplier> suppliers = srepo.findAll();
		ArrayList<String> names = new ArrayList<String>();
		for (Iterator<Supplier> iterator = suppliers.iterator(); iterator.hasNext();) {
			Supplier supplier = (Supplier) iterator.next();
			names.add(supplier.getCompanyName());
		}
		return names;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Supplier findSupplierByName(String name) {
		return srepo.findSupplierByName(name).get(0);
	}
	

	@Override
	@Transactional (readOnly = true)
	public Page<Supplier> findBykeywordContaining(String keyword, int pageNo, int pageSize, String sortField,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		if (keyword == null) {
			return srepo.findAll(pageable);
		}
		return srepo.findBykeywordContaining(keyword, pageable);
	}
	
	@Override
	@Transactional (readOnly = true)
	public List<Brand> findBrandBySupplier(Long id){
		return brepo.findBrandBySupplier(id);
	}
	

}

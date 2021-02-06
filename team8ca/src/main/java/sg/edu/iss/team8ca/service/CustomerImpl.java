package sg.edu.iss.team8ca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.team8ca.model.Customer;
import sg.edu.iss.team8ca.repo.CustomerRepo;

@Service
public class CustomerImpl implements CustomerInterface {

	@Autowired
	private CustomerRepo cusrepo;

	@Override
	@Transactional
	public void saveCustomer(Customer cus) {
		cusrepo.save(cus);
	}

	@Override
	@Transactional
	public void deleteCustomer(Customer cus) {
		cusrepo.delete(cus);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAllCustomer() {
		return cusrepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Customer findCustomerById(long id) {
		return cusrepo.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true)
	public Customer findCustomerByName(String name) {
		return cusrepo.findCusByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Customer findCustomerByNameEmailContactAddress(String name, String email, String contactNo, String address) {
		return cusrepo.findCusByNameEmailContactAddress(name, email, contactNo, address);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> cusSearch(String keyword) {
		return cusrepo.cusSearch(keyword);
	}

	@Override
	@Transactional
	public void addCustomer(Customer customer) {
		cusrepo.save(customer);
	};

	@Override
	@Transactional
	public Page<Customer> cusSearchPage(String keyword, int pageNo, int pageSize, String sortField,
			String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		if (keyword == null) {
			return cusrepo.findAll(pageable);
		}
		return cusrepo.cusSearchPage(keyword, pageable);
	}
}

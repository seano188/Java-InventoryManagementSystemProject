package sg.edu.iss.team8ca.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8ca.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
	@Query("SELECT c FROM Customer c WHERE c.customerName = :name")
	public Customer findCusByName(@Param("name") String name);

	@Query("SELECT c FROM Customer c WHERE c.customerName = :name AND c.email = :email AND c.contactNo =:contactNo AND c.address = :address")
	public Customer findCusByNameEmailContactAddress(@Param("name") String name, @Param("email") String email,
			@Param("contactNo") String contactNo, @Param("address") String address);

	@Query("SELECT c FROM Customer c WHERE c.customerName LIKE %?1%" + "OR c.id LIKE %?1%" + "OR c.contactNo LIKE %?1%"
			+ "OR c.email LIKE %?1%")
	public List<Customer> cusSearch(String keyword);

	@Query("SELECT c FROM Customer c WHERE c.customerName LIKE %?1%" + "OR c.id LIKE %?1%" + "OR c.contactNo LIKE %?1%"
			+ "OR c.email LIKE %?1%")
	public Page<Customer> cusSearchPage(String keyword, Pageable pageable);

}

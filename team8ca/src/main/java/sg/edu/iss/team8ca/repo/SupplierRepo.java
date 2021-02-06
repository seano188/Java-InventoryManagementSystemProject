package sg.edu.iss.team8ca.repo;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8ca.model.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {

	@Query("Select s from Supplier s where s.companyName = :name")
	 List<Supplier> findSupplierByName(@Param("name") String name);

	
	
	 @Query("SELECT s FROM Supplier s WHERE "
				+ "s.companyName LIKE %?1%"
				+ "OR s.postalCode LIKE %?1%"
				+ "OR s.email LIKE %?1%"
				+ "OR s.contactNo LIKE %?1%"
				+ "OR s.address LIKE %?1%")	
	public Page<Supplier> findBykeywordContaining(String keyword, Pageable pageable);
}

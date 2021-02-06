package sg.edu.iss.team8ca.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8ca.model.Brand;

public interface BrandRepo extends JpaRepository<Brand, Long> {
	@Query("Select b from Brand b where b.brandName = :name")
	 List<Brand> findBrandByName(@Param("name") String name);
	
	@Query("Select b from Brand b where b.id = :id")
	 List<Brand> findBrandById(@Param("id") Long id);
	
	@Query("SELECT b from Brand b where b.supplier.id =:id")
	List<Brand> findBrandBySupplier(@Param("id") Long id);
}

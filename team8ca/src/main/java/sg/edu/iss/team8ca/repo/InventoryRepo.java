
package sg.edu.iss.team8ca.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8ca.model.Inventory;


public interface InventoryRepo extends JpaRepository<Inventory, Long> {
	
	@Query("SELECT inv FROM Inventory inv WHERE inv.id = :id")
	public Inventory findInvById(@Param("id") long id);
	
	@Query("SELECT inv FROM Inventory inv WHERE inv.productName = :name")
	public Inventory findInvByName(@Param("name") String name);
	
	@Query("SELECT i FROM Inventory i WHERE i.productName LIKE %?1%"
			+ "OR i.description LIKE %?1%"
			+ "OR i.colour LIKE %?1%"
			+ "OR i.dimension LIKE %?1%"
			+ "OR i.brand.brandName LIKE %?1%"
			+ "OR i.brand.manufacturerName LIKE %?1%")	
	public List<Inventory> invSearch(String keyword);
	
	@Query(value="select * from inventory i where i.brand_id in (select b.id from brand b where b.supplier_id = :id)", nativeQuery = true)
	public List<Inventory> reorderreport(@Param("id") long id);
	
	@Query("SELECT i FROM Inventory i WHERE i.productName LIKE %?1%"
			+ "OR i.description LIKE %?1%"
			+ "OR i.colour LIKE %?1%"
			+ "OR i.dimension LIKE %?1%"
			+ "OR i.brand.brandName LIKE %?1%"
			+ "OR i.brand.manufacturerName LIKE %?1%")	
	public Page<Inventory> findBykeywordContaining(String keyword, Pageable pageable);
	
	@Query("SELECT inv FROM Inventory inv WHERE inv.subcategory.id = :id")
	public List<Inventory> findInvBySubcat(@Param("id")long id);
	
	@Query("SELECT inv FROM Inventory inv WHERE inv.subcategory.category.id = :id")
	public List<Inventory> findInvByCat(@Param("id")long id);
	
	@Query("SELECT inv FROM Inventory inv WHERE inv.brand.id = :id")
	public List<Inventory> findInvByBrand(@Param("id")long id);

}
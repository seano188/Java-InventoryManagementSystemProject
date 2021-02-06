
package sg.edu.iss.team8ca.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8ca.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	@Query("Select c from Category c where c.categoryName = :name")
	 List<Category> findCatByName(@Param("name") String name);
	
	@Query("Select c from Category c where c.id = :id")
	 List<Category> findCategoryById(@Param("id") Long id);
}

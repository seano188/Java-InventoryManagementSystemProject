
package sg.edu.iss.team8ca.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8ca.model.InvUsage;

public interface InvUsageRepo extends JpaRepository<InvUsage, Long> {
	
	@Query("SELECT iu FROM InvUsage iu WHERE iu.tasks LIKE %?1%" + "OR iu.usageDate LIKE %?1%")
	public Page<InvUsage> iuSearchPage(String keyword, Pageable pageable);
	
	@Query("SELECT iu FROM InvUsage iu WHERE iu.customer.id =:id")
	public List<InvUsage> findUsageByCus(@Param("id")long id);
	
	@Query("SELECT iu FROM InvUsage iu WHERE iu.user.id =:id")
	public List<InvUsage> findUsageByUser(@Param("id") long id);	
}

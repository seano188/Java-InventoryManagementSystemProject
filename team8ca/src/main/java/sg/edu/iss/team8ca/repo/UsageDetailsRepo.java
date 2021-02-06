package sg.edu.iss.team8ca.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8ca.model.UsageDetails;

public interface UsageDetailsRepo extends JpaRepository<UsageDetails, Long> {
	
	@Query("SELECT ud FROM UsageDetails ud WHERE ud.invUsage.id = :id")
	public List<UsageDetails> findUdById(@Param("id") Long id);
	
	@Query("SELECT ud FROM UsageDetails ud WHERE ud.inventory.id = :id AND (ud.invUsage.usageDate BETWEEN :startDate AND :endDate)")
	public List<UsageDetails> listUsageForInvId(@Param("id")Long id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);	
	
	@Query("SELECT ud FROM UsageDetails ud WHERE ud.inventory.id = :id AND ud.invUsage.id = :id1")
	public List<UsageDetails> listUdForInvIdUsageId(@Param("id") Long id, @Param("id1") Long id1);
	
	@Query("SELECT ud FROM UsageDetails ud WHERE ud.inventory.id = :id")
	public List<UsageDetails> listUsageForInv(@Param("id")Long id);	
}

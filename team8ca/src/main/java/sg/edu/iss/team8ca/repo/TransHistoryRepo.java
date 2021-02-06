
package sg.edu.iss.team8ca.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8ca.model.TransHistory;
import sg.edu.iss.team8ca.model.TransType;

public interface TransHistoryRepo extends JpaRepository<TransHistory, Long> {
	@Query("SELECT th FROM TransHistory th WHERE th.inventory.id = :id AND (th.transDate BETWEEN :startDate AND :endDate) ORDER BY th.transDate ASC")
	public List<TransHistory> listTransHisForInvId(@Param("id")long id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query("SELECT th FROM TransHistory th WHERE th.inventory.id = :id")
	public List<TransHistory> listTransHis(@Param("id")long id);
	
	@Query("SELECT th FROM TransHistory th WHERE th.inventory.id = :id AND th.transType = :type")
	public List<TransHistory> listTransHisForIdType(@Param("id")long id, @Param("type")TransType type);
	
	@Query("SELECT th FROM TransHistory th WHERE th.inventory.id = :id AND th.transType = :type "
			+ "AND (th.transDate BETWEEN :startDate AND :endDate) ORDER BY th.transDate ASC")
	public List<TransHistory> listTransHisForDateType(@Param("id")long id, @Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate, @Param("type")TransType type);
	
	@Query("SELECT th FROM TransHistory th WHERE th.user.id =:id")
	public List<TransHistory> listTransForUser(@Param("id") long id);
}

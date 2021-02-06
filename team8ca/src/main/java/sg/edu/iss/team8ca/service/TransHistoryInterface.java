package sg.edu.iss.team8ca.service;

import java.time.LocalDate;
import java.util.List;

import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.model.TransHistory;

public interface TransHistoryInterface {
	public List<TransHistory> listAllTransHis();
	public List<TransHistory> listTransHisForId(long id);
	public List<TransHistory> listTransHisForDate(long id, LocalDate startDate, LocalDate endDate);
	
	public List<TransHistory> listTransHisForIdType(long id, String transType);
	public List<TransHistory> listTransHisForDateType(long id, LocalDate startDate, LocalDate endDate, String transType);
	
	public Inventory findInvById(long id);
	public void saveTrans(TransHistory transHistory);
	
	public List<TransHistory> listTransForUser(long id);
	
}

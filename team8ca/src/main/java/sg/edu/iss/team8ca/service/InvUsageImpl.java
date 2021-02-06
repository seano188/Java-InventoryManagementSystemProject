package sg.edu.iss.team8ca.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.team8ca.model.InvUsage;
import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.model.TransHistory;
import sg.edu.iss.team8ca.model.UsageDetails;
import sg.edu.iss.team8ca.repo.InvUsageRepo;
import sg.edu.iss.team8ca.repo.InventoryRepo;
import sg.edu.iss.team8ca.repo.TransHistoryRepo;
import sg.edu.iss.team8ca.repo.UsageDetailsRepo;

@Service
public class InvUsageImpl implements InvUsageInterface {

	@Autowired
	InvUsageRepo iurepo;

	@Autowired
	UsageDetailsRepo udrepo;

	@Autowired
	TransHistoryRepo threpo;
	
	@Autowired
	InventoryRepo irepo;
	
//	record creation
	@Override
	@Transactional
	public void addUsage (InvUsage invUsage) {
		iurepo.save(invUsage);
	}
	
	@Override
	@Transactional
	public void addUsageDetails (UsageDetails usageDetails){
		udrepo.save(usageDetails);
	};
	
	@Override
	@Transactional
	public void addTransHistory (TransHistory transHistory) {
		threpo.save(transHistory);
	};
	
//	record deletion
	@Override
	@Transactional
	public void deleteUsage (InvUsage invUsage) {
		iurepo.delete(invUsage);
	};
	
	@Override
	@Transactional
	public void deleteUsageDetails (UsageDetails usageDetails) {
		udrepo.delete(usageDetails);
	};
	
	@Override
	@Transactional
	public void deleteTransHistory(TransHistory transHistory) {
		threpo.delete(transHistory);
	};
	
//	display record
	@Override
	@Transactional (readOnly = true)
	public List<InvUsage> listAllUsageRecord(){
		return iurepo.findAll();
	};
	
	@Override
	@Transactional (readOnly = true)
	public List<UsageDetails> listUsageForInv(Long id){
		return udrepo.listUsageForInv(id);
	}
	
	@Override
	@Transactional (readOnly = true)
	public Page<InvUsage> iuSearchPage(String keyword, int pageNo, int pageSize, String sortField,
			String sortDirection){
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		if (keyword == null) {
			return iurepo.findAll(pageable);
		}
		return iurepo.iuSearchPage(keyword, pageable);
	}
		
	public List<UsageDetails> listAllUsageDetails(){
		return udrepo.findAll();
	};	
	
	@Override
	@Transactional (readOnly = true)
	public InvUsage findUsageById(Long id){
		return iurepo.findById(id).get();
	};	
	
	@Override
	@Transactional (readOnly = true)
	public UsageDetails findUsageDetailsById(Long id){
		return udrepo.findById(id).get();
	};	
	
	@Override
	@Transactional (readOnly = true)
	public List<UsageDetails> listDetailsForUdId(Long id){
		return udrepo.findUdById(id);
	};
	
	@Override
	@Transactional (readOnly = true)
	public List<UsageDetails> listUsageForInvId(Long id, LocalDate startDate, LocalDate endDate){	
		return udrepo.listUsageForInvId(id, startDate, endDate);
	};
	
	@Override
	@Transactional (readOnly = true)
	public List<Inventory> listAllInventory(){
		return irepo.findAll();
	};
	
	@Override
	@Transactional (readOnly = true)
	public List<Inventory> invSearch(String keyword){
		return irepo.invSearch(keyword);
	};
	
	@Override
	@Transactional (readOnly = true)
	public Inventory findInvById(Long id){
		return irepo.findById(id).get();
	};
	
	@Override
	@Transactional (readOnly = true)
	public List<UsageDetails> listUdForInvIdUsageId(Long invid, Long iuid) {
		return udrepo.listUdForInvIdUsageId(invid, iuid);
	}
	
	@Override
	@Transactional (readOnly = true)
	public List<InvUsage> findUsageByCus(Long id){
		return iurepo.findUsageByCus(id);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<InvUsage> findUsageByUser(Long id){
		return iurepo.findUsageByUser(id);
	}
	
	
	
//	update record
	
	@Override
	@Transactional
	public void reduceInventory(int quantity, Inventory inventory) {
		inventory.setStockQty(inventory.getStockQty() - quantity);
	};
	
}

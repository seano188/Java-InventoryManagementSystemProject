package sg.edu.iss.team8ca.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.model.Supplier;
import sg.edu.iss.team8ca.repo.InventoryRepo;
import sg.edu.iss.team8ca.repo.SupplierRepo;

@Service
public class ReorderReportService implements ReorderReportInterface {

	@Autowired
	private InventoryRepo invrepo;

	@Autowired
	SupplierRepo suprepo;
	
	@Transactional
	public String printDatFile(long id) {
		Supplier sup = new Supplier();
		BufferedWriter bw = null;
		{
			//file path
			String filepath = System.getProperty("user.home");
			String fileName = "report_" + id + ".dat";
			sup = suprepo.findById(id).get();
			try {
				List<Inventory> invList = invrepo.reorderreport(id);
				if(invList.size()==0) {
					return "Products are in Stock!";
				}
				
				File file = new File(filepath +"\\"+ fileName);
				
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				double total = 0;
				int orderQty = 0;
				bw.write("\t\t\tInventory Reorder Report for products from " + sup.getCompanyName() + "(" + id + ")");
				bw.newLine();
				bw.write("---------------------------------------------------------------------------------------");
				bw.newLine();
				bw.write("=======================================================================================");
				bw.write("\nPart No. Unit.Price\t Quantity\t Reorder Qty.\t Min.Ord.Qty\t Ord.Qty\t Price\n");
				bw.write("=======================================================================================\n");
				for(Inventory inv : invList) {
					orderQty=0;
					if(inv.getReorderLevel() >= inv.getStockQty()) {
						if((inv.getReorderLevel()-inv.getStockQty())<=inv.getMinimumOrder()) {
							orderQty = inv.getMinimumOrder();					
						}
						else{
							orderQty = (inv.getReorderLevel()-inv.getStockQty());
						}
					}
					
					double price=orderQty*inv.getOriginalPrice();
					total += price;
					bw.write(String.format("%03d", inv.getId()) + "\t\t\t" + String.format("%,.2f",inv.getOriginalPrice())+ "\t\t" + inv.getStockQty()+"\t\t\t" + inv.getReorderLevel() +"\t\t\t\t" 
					+ inv.getMinimumOrder() + "\t\t\t\t" +orderQty +"\t\t " + String.format("%,.2f", price));
					bw.newLine();
				}
					bw.write("====================================================================================");
					bw.newLine();
					bw.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTotal\t " +String.format("%,.2f", total));
					bw.write("\n\n\n\n\n\t\t\t\t\t\t\t\t\t\t---End of the Report-----");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try {
					if (bw != null)
						bw.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			return "Generated in "+filepath +"\\"+ fileName;
		}
		
	}
}

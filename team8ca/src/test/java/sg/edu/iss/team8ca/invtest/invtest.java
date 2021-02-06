package sg.edu.iss.team8ca.invtest;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.repo.InventoryRepo;

@SpringBootTest
public class invtest {

	@Autowired
	private InventoryRepo invrepo;
	
	@Test
	void reorderReport() {
		List<Inventory> invList = invrepo.reorderreport(2l);
		for (Iterator<Inventory> iterator = invList.iterator(); iterator.hasNext();) {
			Inventory mem = iterator.next();
			System.out.println(mem.toString());
		}
	}
}

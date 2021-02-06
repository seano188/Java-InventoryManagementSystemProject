package sg.edu.iss.team8ca.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import sg.edu.iss.team8ca.model.Brand;
import sg.edu.iss.team8ca.model.Category;
import sg.edu.iss.team8ca.model.Customer;
import sg.edu.iss.team8ca.model.InvUsage;
import sg.edu.iss.team8ca.model.Inventory;
import sg.edu.iss.team8ca.model.Subcategory;
import sg.edu.iss.team8ca.model.Supplier;
import sg.edu.iss.team8ca.model.TransHistory;
import sg.edu.iss.team8ca.model.TransType;
import sg.edu.iss.team8ca.model.UsageDetails;
import sg.edu.iss.team8ca.model.UsageReportStatus;
import sg.edu.iss.team8ca.model.User;
import sg.edu.iss.team8ca.repo.BrandRepo;
import sg.edu.iss.team8ca.repo.CategoryRepo;
import sg.edu.iss.team8ca.repo.CustomerRepo;
import sg.edu.iss.team8ca.repo.InvUsageRepo;
import sg.edu.iss.team8ca.repo.InventoryRepo;
import sg.edu.iss.team8ca.repo.RoleRepo;
import sg.edu.iss.team8ca.repo.SubcategoryRepo;
import sg.edu.iss.team8ca.repo.SupplierRepo;
import sg.edu.iss.team8ca.repo.TransHistoryRepo;
import sg.edu.iss.team8ca.repo.UsageDetailsRepo;
import sg.edu.iss.team8ca.repo.UserRepo;

@Component
public class DbSeederService implements CommandLineRunner {

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	InvUsageRepo iuRepo;

	@Autowired
	CategoryRepo catRepo;

	@Autowired
	BrandRepo brandRepo;

	@Autowired
	SubcategoryRepo subcatRepo;

	@Autowired
	SupplierRepo supRepo;

	@Autowired
	InventoryRepo invRepo;

	@Autowired
	TransHistoryRepo thRepo;

	@Autowired
	UsageDetailsRepo udRepo;

	@Autowired
	CustomerRepo cusRepo;;

	@Override
	public void run(String... args) throws Exception {
		loadUserData();
		loadSubCategory();
		loadBrand();
		loadCustomer();
		loadInv();
		loadInventory();
		loadTrans();
		loadInvUsage();
	}

	private String passwordEncoder(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		return encodedPassword;
	}

	private void loadUserData() {

		if (userRepo.count() == 0) {
			User user1 = new User("admin", passwordEncoder("password"), "Admin", "Administrator", "admin@gmail.com", "88123456",
					"Jurong East");
			userRepo.save(user1);
			User user2 = new User("sankalp", passwordEncoder("sankalp"), "Sankalp", "  ", "sankalp@gmail.com", "88654321",
					"Jurong West");
			userRepo.save(user2);
			User user3 = new User("team8ca", passwordEncoder("team8ca"), "Team", "JavaCA", "team8@gmail.com", "88147852",
					"Clementi");
			userRepo.save(user3);
			User user4 = new User("mechanic", passwordEncoder("mechanic"), "Mechanic", "Mechanic", "mechanic@gmail.com", "88258741",
					"Bedok");
			userRepo.save(user4);
			User user5 = new User("admin2", passwordEncoder("password"), "Admin2", "Administrator", "admin2@gmail.com", "88159753",
					"Jurong East");
			userRepo.save(user5);
		}
	}

	// category -> subcategory
	private void loadSubCategory() {
		Category cat1 = new Category("Accessories");
		Category cat2 = new Category("Tires");
		Category cat3 = new Category("Electrical");
		Category cat4 = new Category("Airbags");
		Category cat5 = new Category("Fuel Guage");
		Category cat6 = new Category("Windshield");
		Category cat7 = new Category("Vehicle Frame");
		Category cat8 = new Category("Speedometer");
		Category cat9 = new Category("Body");
		catRepo.save(cat1);
		catRepo.save(cat2);
		catRepo.save(cat3);
		catRepo.save(cat4);
		catRepo.save(cat5);
		catRepo.save(cat6);
		catRepo.save(cat7);
		catRepo.save(cat8);
		catRepo.save(cat9);
		Subcategory subcat1 = new Subcategory("Loose parts", "Accessories", cat1);
		Subcategory subcat2 = new Subcategory("Hyundai seat belt", "Seat belt", cat1);
		Subcategory subcat3 = new Subcategory("Toyota airbag", "Airbags", cat4);
		Subcategory subcat4 = new Subcategory("BMW lights", "Head lights", cat3);
		Subcategory subcat5 = new Subcategory("Hyundai windshield", "Windshield", cat6);
		Subcategory subcat6 = new Subcategory("Audi Bodyparts", "Bodyparts", cat8);
		subcatRepo.save(subcat1);
		subcatRepo.save(subcat2);
		subcatRepo.save(subcat3);
		subcatRepo.save(subcat4);
		subcatRepo.save(subcat5);
		subcatRepo.save(subcat6);
	}

	private void loadInvUsage() {
		User user1 = userRepo.findByUserName("admin");
		Inventory inv1 = invRepo.findInvById(1);
		Inventory inv2 = invRepo.findInvById(2);
		Inventory inv3 = invRepo.findInvById(3);
		Inventory inv4 = invRepo.findInvById(4);
		User user2 = userRepo.findByUserName("mechanic");		
		Customer customer1 = cusRepo.findAll().get(0);
		Customer customer2 = cusRepo.findAll().get(1);
		Customer customer3 = cusRepo.findAll().get(2);
		Customer customer4 = cusRepo.findAll().get(3);		
		InvUsage invUsage = new InvUsage(LocalDate.of(2019, 11, 20), LocalTime.of(13, 10), UsageReportStatus.InProgress,
				customer1, user1, "Fixing loose screws");
		iuRepo.save(invUsage);
		UsageDetails ud = new UsageDetails(inv1, invUsage, LocalDate.of(2019, 11, 20), LocalTime.of(11, 10), 2);
		udRepo.save(ud);
		UsageDetails ud1 = new UsageDetails(inv2, invUsage, LocalDate.of(2019, 11, 21), LocalTime.of(15, 50), 3);
		udRepo.save(ud1);
		UsageDetails ud2 = new UsageDetails(inv3, invUsage, LocalDate.of(2019, 11, 22), LocalTime.of(9, 45), 4);
		udRepo.save(ud2);
		UsageDetails ud3 = new UsageDetails(inv4, invUsage, LocalDate.of(2019, 11, 23), LocalTime.of(8, 30), 5);
		udRepo.save(ud3);
		inv1.setStockQty(inv1.getStockQty()-2);
		invRepo.save(inv1);
		inv2.setStockQty(inv2.getStockQty()-3);
		invRepo.save(inv2);		
		inv3.setStockQty(inv3.getStockQty()-4);
		invRepo.save(inv3);			
		inv4.setStockQty(inv4.getStockQty()-5);
		invRepo.save(inv4);		
		TransHistory trans1 = new TransHistory(TransType.Usage, -2, inv1, LocalDate.of(2019, 11, 20),
				LocalTime.of(11, 10), user1);
		TransHistory trans2 = new TransHistory(TransType.Usage, -3, inv2, LocalDate.of(2019, 11, 21),
				LocalTime.of(15, 50), user1);
		TransHistory trans3 = new TransHistory(TransType.Usage, -4, inv3, LocalDate.of(2019, 11, 22),
				LocalTime.of(9, 45), user1);
		TransHistory trans4 = new TransHistory(TransType.Usage, -5, inv4, LocalDate.of(2019, 11, 23),
				LocalTime.of(8, 30), user1);
		thRepo.save(trans1);
		thRepo.save(trans2);
		thRepo.save(trans3);
		thRepo.save(trans4);	
		
		InvUsage invUsage1 = new InvUsage(LocalDate.of(2019, 11, 25), LocalTime.of(15, 10), UsageReportStatus.InProgress,
				customer2, user2, "Fixing Hyundai tires");
		iuRepo.save(invUsage1);
		UsageDetails ud4 = new UsageDetails(inv1, invUsage1, LocalDate.of(2019, 11, 25), LocalTime.of(15, 10), 3);
		udRepo.save(ud4);
		UsageDetails ud5 = new UsageDetails(inv2, invUsage1, LocalDate.of(2019, 11, 25), LocalTime.of(15, 10), 4);
		udRepo.save(ud5);
		UsageDetails ud6 = new UsageDetails(inv3, invUsage1, LocalDate.of(2019, 11, 25), LocalTime.of(15, 10), 3);
		udRepo.save(ud6);
		UsageDetails ud7 = new UsageDetails(inv4, invUsage1, LocalDate.of(2019, 11, 25), LocalTime.of(15, 10), 7);
		udRepo.save(ud7);
		inv1.setStockQty(inv1.getStockQty()-3);
		invRepo.save(inv1);
		inv2.setStockQty(inv2.getStockQty()-4);
		invRepo.save(inv2);		
		inv3.setStockQty(inv3.getStockQty()-3);
		invRepo.save(inv3);			
		inv4.setStockQty(inv4.getStockQty()-7);
		invRepo.save(inv4);
		TransHistory trans5 = new TransHistory(TransType.Usage, -2, inv1, LocalDate.of(2019, 11, 25),
				LocalTime.of(15, 10), user2);
		TransHistory trans6 = new TransHistory(TransType.Usage, -3, inv2, LocalDate.of(2019, 11, 25),
				LocalTime.of(15, 10), user2);
		TransHistory trans7 = new TransHistory(TransType.Usage, -4, inv3, LocalDate.of(2019, 11, 25),
				LocalTime.of(15, 10), user2);
		TransHistory trans8 = new TransHistory(TransType.Usage, -5, inv4, LocalDate.of(2019, 11, 26),
				LocalTime.of(15, 10), user2);
		thRepo.save(trans5);
		thRepo.save(trans6);
		thRepo.save(trans7);
		thRepo.save(trans8);
		
		InvUsage invUsage2 = new InvUsage(LocalDate.of(2019, 12, 02), LocalTime.of(18, 10), UsageReportStatus.InProgress,
				customer3, user2, "Fixing Honda steering");
		iuRepo.save(invUsage2);
		UsageDetails ud8 = new UsageDetails(inv1, invUsage2, LocalDate.of(2019, 12, 02), LocalTime.of(18, 10), 5);
		udRepo.save(ud8);
		UsageDetails ud9 = new UsageDetails(inv2, invUsage2, LocalDate.of(2019, 12, 02), LocalTime.of(18, 10), 4);
		udRepo.save(ud9);
		UsageDetails ud10 = new UsageDetails(inv3, invUsage2, LocalDate.of(2019, 12, 02), LocalTime.of(18, 10), 5);
		udRepo.save(ud10);
		UsageDetails ud11 = new UsageDetails(inv4, invUsage2, LocalDate.of(2019, 12, 02), LocalTime.of(18, 10), 7);
		udRepo.save(ud11);
		inv1.setStockQty(inv1.getStockQty()-5);
		invRepo.save(inv1);
		inv2.setStockQty(inv2.getStockQty()-4);
		invRepo.save(inv2);		
		inv3.setStockQty(inv3.getStockQty()-5);
		invRepo.save(inv3);			
		inv4.setStockQty(inv4.getStockQty()-7);
		invRepo.save(inv4);
		TransHistory trans9 = new TransHistory(TransType.Usage, -2, inv1, LocalDate.of(2019, 12, 02),
				LocalTime.of(18, 10), user2);
		TransHistory trans10 = new TransHistory(TransType.Usage, -3, inv2, LocalDate.of(2019, 12, 02),
				LocalTime.of(18, 10), user2);
		TransHistory trans11 = new TransHistory(TransType.Usage, -4, inv3, LocalDate.of(2019, 12, 02),
				LocalTime.of(18, 10), user2);
		TransHistory trans12 = new TransHistory(TransType.Usage, -5, inv4, LocalDate.of(2019, 12, 02),
				LocalTime.of(18, 10), user2);
		thRepo.save(trans9);
		thRepo.save(trans10);
		thRepo.save(trans11);
		thRepo.save(trans12);
				
		InvUsage invUsage3 = new InvUsage(LocalDate.of(2019, 12, 15), LocalTime.of(10, 5), UsageReportStatus.InProgress,
				customer4, user1, "Fixing engine loose parts");
		iuRepo.save(invUsage3);		
		UsageDetails ud12 = new UsageDetails(inv1, invUsage3, LocalDate.of(2019, 12, 15), LocalTime.of(10, 5), 5);
		udRepo.save(ud12);
		UsageDetails ud13 = new UsageDetails(inv2, invUsage3, LocalDate.of(2019, 12, 15), LocalTime.of(10, 5), 8);
		udRepo.save(ud13);
		UsageDetails ud14 = new UsageDetails(inv3, invUsage3, LocalDate.of(2019, 12, 15), LocalTime.of(10, 5), 5);
		udRepo.save(ud14);
		UsageDetails ud15 = new UsageDetails(inv4, invUsage3, LocalDate.of(2019, 12, 15), LocalTime.of(10, 5), 9);
		udRepo.save(ud15);
		inv1.setStockQty(inv1.getStockQty()-5);
		invRepo.save(inv1);
		inv2.setStockQty(inv2.getStockQty()-8);
		invRepo.save(inv2);		
		inv3.setStockQty(inv3.getStockQty()-5);
		invRepo.save(inv3);			
		inv4.setStockQty(inv4.getStockQty()-9);
		invRepo.save(inv4);
		TransHistory trans13 = new TransHistory(TransType.Usage, -2, inv1, LocalDate.of(2019, 12, 15),
				LocalTime.of(10, 5), user1);
		TransHistory trans14 = new TransHistory(TransType.Usage, -3, inv2, LocalDate.of(2019, 12, 15),
				LocalTime.of(10, 5), user1);
		TransHistory trans15 = new TransHistory(TransType.Usage, -4, inv3, LocalDate.of(2019, 12, 15),
				LocalTime.of(10, 5), user1);
		TransHistory trans16 = new TransHistory(TransType.Usage, -5, inv4, LocalDate.of(2019, 12, 15),
				LocalTime.of(10, 5), user1);
		thRepo.save(trans13);
		thRepo.save(trans14);
		thRepo.save(trans15);
		thRepo.save(trans16);		
		
		InvUsage invUsage4 = new InvUsage(LocalDate.of(2019, 12, 24), LocalTime.of(10, 5), UsageReportStatus.InProgress,
				customer3, user1, "Fixing exhaust pipes");
		iuRepo.save(invUsage4);		
		UsageDetails ud16 = new UsageDetails(inv1, invUsage4, LocalDate.of(2019, 12, 24), LocalTime.of(10, 5), 10);
		udRepo.save(ud16);
		UsageDetails ud17 = new UsageDetails(inv2, invUsage4, LocalDate.of(2019, 12, 24), LocalTime.of(10, 5), 8);
		udRepo.save(ud17);
		UsageDetails ud18 = new UsageDetails(inv3, invUsage4, LocalDate.of(2019, 12, 24), LocalTime.of(10, 5), 8);
		udRepo.save(ud18);
		UsageDetails ud19 = new UsageDetails(inv4, invUsage4, LocalDate.of(2019, 12, 24), LocalTime.of(10, 5), 9);
		udRepo.save(ud19);
		inv1.setStockQty(inv1.getStockQty()-10);
		invRepo.save(inv1);
		inv2.setStockQty(inv2.getStockQty()-8);
		invRepo.save(inv2);		
		inv3.setStockQty(inv3.getStockQty()-8);
		invRepo.save(inv3);			
		inv4.setStockQty(inv4.getStockQty()-9);
		invRepo.save(inv4);
		TransHistory trans17 = new TransHistory(TransType.Usage, -2, inv1, LocalDate.of(2019, 12, 24),
				LocalTime.of(10, 5), user1);
		TransHistory trans18 = new TransHistory(TransType.Usage, -3, inv2, LocalDate.of(2019, 12, 24),
				LocalTime.of(10, 5), user1);
		TransHistory trans19 = new TransHistory(TransType.Usage, -4, inv3, LocalDate.of(2019, 12, 24),
				LocalTime.of(10, 5), user1);
		TransHistory trans20 = new TransHistory(TransType.Usage, -5, inv4, LocalDate.of(2019, 12, 24),
				LocalTime.of(10, 5), user1);
		thRepo.save(trans17);
		thRepo.save(trans18);
		thRepo.save(trans19);
		thRepo.save(trans20);	
		
		InvUsage invUsage5 = new InvUsage(LocalDate.of(2019, 12, 30), LocalTime.of(10, 5), UsageReportStatus.InProgress,
				customer3, user1, "Fixing car seat mechanism");
		iuRepo.save(invUsage5);		
		UsageDetails ud20 = new UsageDetails(inv1, invUsage5, LocalDate.of(2019, 12, 30), LocalTime.of(10, 5), 15);
		udRepo.save(ud20);
		UsageDetails ud21 = new UsageDetails(inv2, invUsage5, LocalDate.of(2019, 12, 30), LocalTime.of(10, 5), 18);
		udRepo.save(ud21);
		UsageDetails ud22 = new UsageDetails(inv3, invUsage5, LocalDate.of(2019, 12, 30), LocalTime.of(10, 5), 15);
		udRepo.save(ud22);
		UsageDetails ud23 = new UsageDetails(inv4, invUsage5, LocalDate.of(2019, 12, 30), LocalTime.of(10, 5), 19);
		udRepo.save(ud23);
		inv1.setStockQty(inv1.getStockQty()-15);
		invRepo.save(inv1);
		inv2.setStockQty(inv2.getStockQty()-18);
		invRepo.save(inv2);		
		inv3.setStockQty(inv3.getStockQty()-15);
		invRepo.save(inv3);			
		inv4.setStockQty(inv4.getStockQty()-19);
		invRepo.save(inv4);
		TransHistory trans21 = new TransHistory(TransType.Usage, -2, inv1, LocalDate.of(2019, 12, 30),
				LocalTime.of(10, 5), user1);
		TransHistory trans22 = new TransHistory(TransType.Usage, -3, inv2, LocalDate.of(2019, 12, 30),
				LocalTime.of(10, 5), user1);
		TransHistory trans23 = new TransHistory(TransType.Usage, -4, inv3, LocalDate.of(2019, 12, 30),
				LocalTime.of(10, 5), user1);
		TransHistory trans24 = new TransHistory(TransType.Usage, -5, inv4, LocalDate.of(2019, 12, 30),
				LocalTime.of(10, 5), user1);
		thRepo.save(trans21);
		thRepo.save(trans22);
		thRepo.save(trans23);
		thRepo.save(trans24);	
	}

	private void loadInventory() {
		Subcategory Tires = subcatRepo.findSubcatByName("loose parts").get(0);
		Subcategory Accessories = subcatRepo.findSubcatByName("loose parts").get(0);
		Subcategory Electrical = subcatRepo.findSubcatByName("BMW lights").get(0);
		Subcategory Windshield = subcatRepo.findSubcatByName("loose parts").get(0);
		Subcategory Body = subcatRepo.findSubcatByName("loose parts").get(0);
		Brand Denso = brandRepo.findBrandByName("Denso Corp").get(0);
		Brand JBL = brandRepo.findBrandByName("JBL").get(0);
		Brand Bosch = brandRepo.findBrandByName("Robert Bosch").get(0);
		Brand Lear = brandRepo.findBrandByName("Lear Corp").get(0);
		Brand Magna = brandRepo.findBrandByName("Magna International").get(0);
		Inventory inv1 = new Inventory("200 screws", "200 pieces of screws", 10.00, 11.00, 13.00, 12.00, 100, 500, 200,
				"Orange", "5mm x 1mm", Accessories, Denso);
		Inventory inv2 = new Inventory("Bumper", "Attached at front/rear end", 50.00, 15.00, 65.00, 60.00, 100, 5, 1,
				"Blue", "200cm x 40cm", Body, Bosch);
		Inventory inv3 = new Inventory("Tires", "Rubber parts of wheel", 10.00, 5.50, 6.50, 6.00, 100, 8, 4, "Black",
				"26rad", Tires, Bosch);
		Inventory inv4 = new Inventory("Rims", "Outer edge of wheel", 20.00, 22.00, 26.00, 24.00, 100, 8, 4, "Silver",
				"25rad", Tires, Bosch);
		Inventory inv5 = new Inventory("Glass", "For windows", 15.00, 17.00, 21.00, 19.00, 6, 8, 1, "Clear",
				"40mx40cm", Windshield, Magna);
		Inventory inv6 = new Inventory("Decklid", "Cover of the trunk", 50.00, 55.00, 65.00, 60.00, 3, 5, 1, "Blue",
				"25rad", Body, Magna);
		Inventory inv7 = new Inventory("Locks", "Cardoor locks", 25.00, 28.00, 32.00, 30.00, 20, 30, 1, "Silver",
				"10mmx10mm", Windshield, Denso);
		Inventory inv8 = new Inventory("Sunroof", "Foldable roof", 180.00, 200.00, 240.00, 220.00, 1, 3, 1,
				"Matt Black", "25rad", Body, Magna);
		Inventory inv9 = new Inventory("Fuel tank", "Container for fuel", 105.00, 110.00, 130.00, 120.00, 5, 10, 1,
				"Silver", "25rad", Body, Magna);
		Inventory inv10 = new Inventory("Video Player", "Plays videos", 80.00, 90.00, 110.00, 100.00, 5, 2, 1, "Black",
				"25rad", Electrical, JBL);
		Inventory inv11 = new Inventory("Subwoofer", "Sounds base", 45.00, 50.00, 56.00, 53.00, 5, 2, 1, "Black",
				"25rad", Electrical, JBL);
		Inventory inv12 = new Inventory("Speaker", "Plays sounds", 30.00, 33.00, 38.00, 36.00, 27, 9, 3, "Black",
				"25rad", Electrical, JBL);
		Inventory inv13 = new Inventory("Car Camera", "Records Video", 115.00, 120.00, 135.00, 130.00, 10, 5, 1, "Silver",
				"25rad", Electrical, JBL);
		Inventory inv14 = new Inventory("Car Battery", "Used to start a vehicle", 25.00, 28.00, 33.00, 30.00, 20, 10, 1, "Green",
				"25rad", Electrical, Magna);
		Inventory inv15 = new Inventory("Tachometer", "Measure rotation speed", 20.00, 22.00, 26.00, 24.00, 20, 10, 1, "Black",
				"25rad", Accessories, Lear);
		Inventory inv16 = new Inventory("Voltmeter", "Measure electric potential", 20.00, 22.00, 26.00, 24.00, 20, 10, 1, "Black",
				"25rad", Accessories, Lear);
		Inventory inv17 = new Inventory("Tail Lights", "Car backlights", 10.00, 6.00, 8.00, 7.00, 20, 10, 10, "Yellow",
				"5rad", Accessories, Lear);
		Inventory inv18 = new Inventory("Headlights", "Car frontlights", 20.00, 22.00, 26.00, 24.00, 20, 10, 10,
				"Yellow", "5rad", Accessories, Lear);
		Inventory inv19 = new Inventory("Spotlight", "Bright beam of light", 20.00, 22.00, 26.00, 24.00, 20, 10, 10,
				"White", "5rad", Accessories, Lear);
		Inventory inv20 = new Inventory("Windshield", "Window at the front", 100.00, 120.00, 160.00, 140.00, 20, 10, 10,
				"Black", "1mx0.5m", Windshield, Lear);
		Inventory inv21 = new Inventory("Rear mirror", "Inside mirror", 12.00, 14.00, 18.00, 16.00, 30, 20, 2, "Black",
				"40cmx20cm", Accessories, Magna);
		Inventory inv22 = new Inventory("Side mirror", "Mirror for the sides", 20.00, 22.00, 28.00, 24.00, 20, 10, 2,
				"Black", "20cmx30cm", Accessories, Magna);
		Inventory inv23 = new Inventory("Seatbelts", "For safety", 32.00, 34.00, 38.00, 36.00, 20, 10, 20, "Black",
				"20cmx30cm", Accessories, Magna);
		Inventory inv24 = new Inventory("Engine cover", "Cover for the engine", 120.00, 140.00, 180.00, 160.00, 7, 5, 2,
				"Black", "100cmx30cm", Body, Bosch);
		Inventory inv25 = new Inventory("Engine cover", "Cover for the engine", 120.00, 140.00, 180.00, 160.00, 10, 5,
				2, "White", "100cmx30cm", Body, Bosch);
		Inventory inv26 = new Inventory("Engine cover", "Cover for the engine", 120.00, 140.00, 180.00, 160.00, 9, 5, 2,
				"Red", "100cmx30cm", Body, Bosch);
		Inventory inv27 = new Inventory("Spotlight", "Bright beam of light", 20.00, 22.00, 26.00, 24.00, 20, 10, 10,
				"White", "5rad", Accessories, Magna);
		Inventory inv28 = new Inventory("Locks", "Cardoor locks", 25.00, 28.00, 32.00, 30.00, 100, 30, 1, "Black",
				"10mmx10mm", Accessories, Denso);
		Inventory inv29 = new Inventory("Speaker", "Plays sounds", 30.00, 33.00, 38.00, 36.00, 27, 9, 3, "Silver",
				"25rad", Electrical, Denso);
		Inventory inv30 = new Inventory("Airbag", "For safety by Magna", 80.00, 82.00, 86.00, 84.00, 20, 10, 2, "Black",
				"1unit", Accessories, Magna);
		Inventory inv31 = new Inventory("Airbag", "For safety by Denso", 100.00, 120.00, 160.00, 140.00, 10, 10, 2,
				"Black", "1unit", Accessories, Denso);
		Inventory inv32 = new Inventory("Door", "Black door for car", 200.00, 220.00, 280.00, 240.00, 14, 10, 6,
				"Black", "20cmx30cm", Body, Magna);
		Inventory inv33 = new Inventory("Door", "White door for car", 200.00, 220.00, 280.00, 240.00, 12, 10, 6,
				"White", "20cmx30cm", Body, Lear);
		Inventory inv34 = new Inventory("Door", "Red door for car", 200.00, 220.00, 280.00, 240.00, 5, 10, 6, "Red",
				"20cmx30cm", Body, Bosch);
		Inventory inv35 = new Inventory("Door", "Silver door for car", 200.00, 220.00, 280.00, 240.00, 5, 10, 6,
				"Silver", "20cmx30cm", Body, Bosch);
		Inventory inv36 = new Inventory("Bosch car key", "Window at the front", 20.00, 22.00, 30.00, 24.00, 15, 10, 10,
				"Black", "20cmx30cm", Accessories, Bosch);
		Inventory inv37 = new Inventory("Magna car key", "Window at the front", 20.00, 22.00, 30.00, 24.00, 15, 10, 10,
				"Black", "20cmx30cm", Accessories, Magna);
		Inventory inv38 = new Inventory("Lear car key", "Window at the front", 20.00, 22.00, 30.00, 24.00, 15, 10, 10,
				"Black", "20cmx30cm", Accessories, Lear);
		Inventory inv39 = new Inventory("Car Camera", "Records Video", 115.00, 120.00, 135.00, 130.00, 10, 5, 1,
				"Black", "25rad", Electrical, JBL);
		Inventory inv40 = new Inventory("Black decklid", "Cover of the trunk", 50.00, 55.00, 65.00, 60.00, 10, 5, 1,
				"Black", "25rad", Body, Magna);
		Inventory inv41 = new Inventory("White Decklid", "Cover of the trunk", 50.00, 55.00, 65.00, 60.00, 10, 5, 1,
				"White", "25rad", Body, Magna);
		Inventory inv42 = new Inventory("Red Decklid", "Cover of the trunk", 50.00, 55.00, 65.00, 60.00, 10, 5, 1,
				"Red", "25rad", Body, Magna);
		Inventory inv43 = new Inventory("Side mirror", "Mirror for the sides", 20.00, 22.00, 28.00, 24.00, 20, 10, 2,
				"White", "20cmx30cm", Accessories, Magna);
		invRepo.save(inv1);
		invRepo.save(inv2);
		invRepo.save(inv3);
		invRepo.save(inv4);
		invRepo.save(inv5);
		invRepo.save(inv6);
		invRepo.save(inv7);
		invRepo.save(inv8);
		invRepo.save(inv9);
		invRepo.save(inv10);
		invRepo.save(inv11);
		invRepo.save(inv12);
		invRepo.save(inv13);
		invRepo.save(inv14);
		invRepo.save(inv15);
		invRepo.save(inv16);
		invRepo.save(inv17);
		invRepo.save(inv18);
		invRepo.save(inv19);
		invRepo.save(inv20);
		invRepo.save(inv21);
		invRepo.save(inv22);
		invRepo.save(inv23);
		invRepo.save(inv24);
		invRepo.save(inv25);
		invRepo.save(inv26);
		invRepo.save(inv27);
		invRepo.save(inv28);
		invRepo.save(inv29);
		invRepo.save(inv30);
		invRepo.save(inv31);
		invRepo.save(inv32);
		invRepo.save(inv33);
		invRepo.save(inv34);
		invRepo.save(inv35);
		invRepo.save(inv36);
		invRepo.save(inv37);
		invRepo.save(inv38);
		invRepo.save(inv39);
		invRepo.save(inv40);
		invRepo.save(inv41);
		invRepo.save(inv42);
		invRepo.save(inv43);
	}

	// supplier -> brand
	private void loadBrand() {
		Supplier supplier = new Supplier("Tan Tiong Suppliers", "91232456", "tts@gmail.com", "Tan Tion Avenue", 312456);
		Supplier supplier1 = new Supplier("Sin Guan Auto Parts", "88170202", "singuan@gmail.com", "67 Veerasamy road",
				661712);
		Supplier supplier2 = new Supplier("Kin Hua Motor Co", "91102034", "kinhua@yahoo.com", "77 Jalan Lembah Kalang",
				144610);
		Supplier supplier3 = new Supplier("He Xing Auto Supply", "80123412", "hexing@gmail.com", "78 Dunlop Street",
				172031);
		Supplier supplier4 = new Supplier("Unico Motor Pte Ltd", "99102233", "unico@gmail.com", "22 Upper Weld road",
				660201);
		Supplier supplier5 = new Supplier("Min Ghee Auto Pte Ltd", "98129345", "minghee@gmail.com", "123 Sin Ming road",
				881023);
		supRepo.save(supplier);
		supRepo.save(supplier1);
		supRepo.save(supplier2);
		supRepo.save(supplier3);
		supRepo.save(supplier4);
		supRepo.save(supplier5);
		Brand brand = new Brand("TonyHawk", "Tony Manufacturing", supplier);
		Brand brand1 = new Brand("Robert Bosch", "Bosch Manufacturing", supplier1);
		Brand brand2 = new Brand("Denso Corp", "Denso Manufacturing", supplier2);
		Brand brand3 = new Brand("Magna International", "Magna Manufacturing", supplier3);
		Brand brand4 = new Brand("Lear Corp", "Lear Manufacturing", supplier1);
		Brand brand5 = new Brand("AC Delco", "Delco Manufacturing", supplier);
		Brand brand6 = new Brand("JBL", "JBL Manufacturing", supplier);
		brandRepo.save(brand);
		brandRepo.save(brand1);
		brandRepo.save(brand2);
		brandRepo.save(brand3);
		brandRepo.save(brand4);
		brandRepo.save(brand5);
		brandRepo.save(brand6);
	}

	// brand + subcategory -> inventory
	private void loadInv() {
		Subcategory subcat2 = subcatRepo.findSubcatByName("loose parts").get(0);
		Brand brand2 = brandRepo.findBrandByName("TonyHawk").get(0);
		Inventory inv = new Inventory("100 screws", "100 pieces of screws", 10.00, 11.00, 12.00, 13.00, 1000, 500, 200,
				"Orange", "5mm x 1mm", subcat2, brand2);
		invRepo.save(inv);
	}

	// load new inventory into transHistory
	private void loadTrans() {
		User user1 = userRepo.findByUserName("admin");
		for (long id = 1; id <= invRepo.count(); id++) {
			Inventory inv = invRepo.findInvById(id);
			TransHistory trans = new TransHistory(TransType.NewInventory, inv.getStockQty(), inv,
					LocalDate.of(2019, 11, 10), LocalTime.of(21, 30), user1);
			thRepo.save(trans);
		}
	}

	private void loadCustomer() {
		Customer customer1 = new Customer("Timothy", "+659124526", "Tim@gmail.com", "Ang Mo Kio Avenue 5");
		cusRepo.save(customer1);
		Customer customer2 = new Customer("James", "+6595876245", "James@gmail.com", "Bedok Avenue 2");
		cusRepo.save(customer2);
		Customer customer3 = new Customer("Tilly", "+6594576527", "Tilly@gmail.com", "Orchard Ln 2");
		cusRepo.save(customer3);
		Customer customer4 = new Customer("Sumarah", "+6595467293", "Sumarah@gmail.com", "Grange Road Leornie Hill");
		cusRepo.save(customer4);
		Customer customer5 = new Customer("Jimmy", "+6592467587", "Jimmy@gmail.com", "Toa Payoh Lorong 3");
		cusRepo.save(customer5);
		Customer customer6 = new Customer("Angelo", "+6595246758", "Angelo@gmail.com", "Thomson Avenue 8");
		cusRepo.save(customer6);
		Customer customer7 = new Customer("Shamus", "+6599642348", "Shamus@gmail.com", "Bukit Timah Avenue 2");
		cusRepo.save(customer7);
		Customer customer8 = new Customer("Tricia", "+6595234678", "Tricia@gmail.com", "Ang Mo Kio Avenue 2");
		cusRepo.save(customer8);
		Customer customer9 = new Customer("Ika", "+65954672549", "Ikah@gmail.com", "Tampines Avenue 8");
		cusRepo.save(customer9);
		Customer customer10 = new Customer("King", "+65952346820", "King@gmail.com", "Chinese Garden View");
		cusRepo.save(customer10);
		Customer customer11 = new Customer("Prince", "+6596254203", "Prince@gmail.com", "Jurong Lake View");
		cusRepo.save(customer11);
		Customer customer12 = new Customer("Teriyaki", "+65956485965", "Teriyaki@gmail.com", "Holland Avenue 2");
		cusRepo.save(customer12);
	}

}

package sg.edu.iss.team8ca.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Supplier {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String companyName;
	private String contactNo;
	private String email;
	private String address;
	private int postalCode;
	
	@OneToMany(mappedBy="supplier",cascade = CascadeType.REMOVE)
	private List<Brand> brand;

	public Supplier(String companyName, String contactNo, String email, String address, int postalCode,
			List<Brand> brand) {
		super();
		this.companyName = companyName;
		this.contactNo = contactNo;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
		this.brand = brand;
	}
	
	public Supplier(String companyName, String contactNo, String email, String address, int postalCode) {
		super();
		this.companyName = companyName;
		this.contactNo = contactNo;
		this.email = email;
		this.address = address;
		this.postalCode = postalCode;
	}
	
	
	
	
}

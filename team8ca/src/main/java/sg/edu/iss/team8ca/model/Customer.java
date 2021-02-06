package sg.edu.iss.team8ca.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	private long id;
	
	@OneToMany(mappedBy="customer")
	private List<InvUsage> invUsage;
	
	@NotNull
	private String customerName;
	
	@NotNull
	private String contactNo;
	
	@Email(message = "*Please input a valid email")
	private String email;
	@NotNull
	private String address;
	
	public Customer(List<InvUsage> invUsage, @NotNull String customerName,
			@NotNull String contactNo,
			@NotNull String email,
			@NotNull String address) {
		super();
		this.invUsage = invUsage;
		this.customerName = customerName;
		this.contactNo = contactNo;
		this.email = email;
		this.address = address;
	}

	public Customer(@NotNull String customerName,
			@NotNull String contactNo,
			@NotNull String email,
			@NotNull String address) {
		super();
		this.customerName = customerName;
		this.contactNo = contactNo;
		this.email = email;
		this.address = address;
	}
	
	

}

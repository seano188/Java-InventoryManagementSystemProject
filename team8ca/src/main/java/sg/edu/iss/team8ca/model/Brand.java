 package sg.edu.iss.team8ca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Brand {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	private long id;
	private String brandName;
	private String manufacturerName;
	
	@ManyToOne
	private Supplier supplier;

	public Brand(String brandName, String manufacturerName, Supplier supplier) {
		super();
		this.brandName = brandName;
		this.manufacturerName = manufacturerName;
		this.supplier = supplier;
	}
	
	
	
}

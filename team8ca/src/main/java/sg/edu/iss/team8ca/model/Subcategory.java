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
public class Subcategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	private long id;
	private String subcategoryName;
	private String subcategoryType;
	
	@ManyToOne
	private Category category;

	public Subcategory(String subcategoryName, String subcategoryType, Category category) {
		super();
		this.subcategoryName = subcategoryName;
		this.subcategoryType = subcategoryType;
		this.category = category;
	}
	
	
	
	
}

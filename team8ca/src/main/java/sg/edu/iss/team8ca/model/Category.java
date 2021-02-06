package sg.edu.iss.team8ca.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	private long id;
	@NotNull
	private String categoryName;
	
	@OneToMany(mappedBy="category",cascade = CascadeType.REMOVE)
	private List<Subcategory> subcategory;

	public Category(String categoryName, List<Subcategory> subcategory) {
		super();
		this.categoryName = categoryName;
		this.subcategory = subcategory;
	}
	
	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}
	
}

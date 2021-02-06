
package sg.edu.iss.team8ca.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull
	private String productName;
	private String description;
	@NotNull
	private double originalPrice;
	private double wholesalePrice;
	private double retailPrice;
	private double partnerPrice;
	private int stockQty;
	private int reorderLevel;
	private int minimumOrder;
	private String colour;
	private String dimension;
	
	@ManyToOne
	private Subcategory subcategory;

	@ManyToOne
	private Brand brand;

	@OneToMany(mappedBy = "inventory")
	private List<UsageDetails> usageDetails;

	@OneToMany(mappedBy = "inventory")
	private List<TransHistory> transHistory;

	public Inventory(@NotNull String productName, String description, double originalPrice, double wholesalePrice,
			double retailPrice, double partnerPrice, int stockQty,int reorderLevel, int minimumOrder, String colour,
			String dimension, Subcategory subcategory, Brand brand, List<UsageDetails> usageDetails,
			List<TransHistory> transHistory) {
		super();
		this.productName = productName;
		this.description = description;
		this.originalPrice = originalPrice;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
		this.partnerPrice = partnerPrice;
		this.stockQty = stockQty;
		this.reorderLevel = reorderLevel;
		this.minimumOrder = minimumOrder;
		this.colour = colour;
		this.dimension = dimension;
		this.subcategory = subcategory;
		this.brand = brand;
		this.usageDetails = usageDetails;
		this.transHistory = transHistory;
	}
	
	public Inventory(@NotNull String productName, String description, double originalPrice, double wholesalePrice,
			double retailPrice, double partnerPrice, int stockQty, int reorderLevel, int minimumOrder, String colour,
			String dimension, Subcategory subcategory, Brand brand) {
		super();
		this.productName = productName;
		this.description = description;
		this.originalPrice = originalPrice;
		this.wholesalePrice = wholesalePrice;
		this.retailPrice = retailPrice;
		this.partnerPrice = partnerPrice;
		this.stockQty = stockQty;
		this.reorderLevel = reorderLevel;
		this.minimumOrder = minimumOrder;
		this.colour = colour;
		this.dimension = dimension;
		this.subcategory = subcategory;
		this.brand = brand;
	}

	@Override
	public String toString() {
		return id + "\t" + originalPrice + "\t" + stockQty + "\t"
				+ reorderLevel + "\t" + minimumOrder + minimumOrder + (originalPrice*minimumOrder);
	}

}

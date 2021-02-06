
package sg.edu.iss.team8ca.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class UsageDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Inventory inventory;

	@ManyToOne
	private InvUsage invUsage;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime time;
	private long quantity;

	public UsageDetails(Inventory inventory, InvUsage invUsage, LocalDate date, LocalTime time, long quantity) {
		super();
		this.inventory = inventory;
		this.invUsage = invUsage;
		this.date = date;
		this.time = time;
		this.quantity = quantity;
	}
}

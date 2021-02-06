
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
public class TransHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private TransType transType;

	@ManyToOne
	private Inventory inventory;
	private int quantity;
	private int remainingStock;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate transDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime transTime;

	@ManyToOne
	private User user;

	public TransHistory(TransType transType, Integer quantity, Inventory inventory, LocalDate transDate, LocalTime transTime, User user) {
		super();
		this.transType = transType;
		this.quantity = quantity;
		this.inventory = inventory;
		this.transDate = transDate;
		this.transTime = transTime;
		this.user = user;
		this.remainingStock = this.inventory.getStockQty();
	}
}

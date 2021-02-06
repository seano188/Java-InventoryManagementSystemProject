
package sg.edu.iss.team8ca.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class InvUsage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate usageDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime usageTime;
	private UsageReportStatus usageReportStatus;
	@OneToMany(mappedBy = "invUsage")
	private List<UsageDetails> usageDetails;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Customer customer;
	
	private String tasks;
	
	public InvUsage(LocalDate usageDate, LocalTime usageTime, UsageReportStatus usageReportStatus, User user) {
		super();
		this.usageDate = usageDate;
		this.usageTime = usageTime;
		this.usageReportStatus = usageReportStatus;
		this.user = user;
	}
	
	public InvUsage(LocalDate usageDate, LocalTime usageTime, UsageReportStatus usageReportStatus, User user, String tasks) {
		super();
		this.usageDate = usageDate;
		this.usageTime = usageTime;
		this.usageReportStatus = usageReportStatus;
		this.user = user;
		this.tasks = tasks;
	}
	
	public InvUsage(LocalDate usageDate, LocalTime usageTime, UsageReportStatus usageReportStatus, Customer customer, User user, String tasks) {
		super();
		this.usageDate = usageDate;
		this.usageTime = usageTime;
		this.usageReportStatus = usageReportStatus;
		this.customer=customer;
		this.user = user;
		this.tasks = tasks;
	}	
	
	public InvUsage(LocalDate usageDate, LocalTime usageTime, List<UsageDetails> usageDetails, UsageReportStatus usageReportStatus, Customer customer, User user, String tasks) {
		super();
		this.usageDate = usageDate;
		this.usageTime = usageTime;
		this.usageDetails = usageDetails;
		this.usageReportStatus = usageReportStatus;
		this.user = user;
		this.tasks = tasks;
		this.customer=customer;
	}
}

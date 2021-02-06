package sg.edu.iss.team8ca.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;
	@Column(name = "username")
	@Length(min = 5, message = "*Your user name must have at least 5 characters")
	@NotEmpty(message = "*Please enter your username")
	private String userName;
	@Column(name = "password")
	@Length(min = 8, message = "*Your password must have at least 8 characters")
	@NotEmpty(message = "*Please enter your password")
	private String password;
	@Column(name = "active")
	private Boolean active;
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	private String firstName;
	private String lastName;
	private String email;
	private String contactNo;
	private String address;
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	@OneToMany(mappedBy = "user")
	private List<TransHistory> transHistory;

	@OneToMany(mappedBy = "user")
	private List<InvUsage> invUsage;

	public User(long id,
			@Length(min = 5, message = "*Your user name must have at least 5 characters") @NotEmpty(message = "*Please enter your username") String userName,
			@Length(min = 8, message = "*Your password must have at least 8 characters") @NotEmpty(message = "*Please enter your password") String password,
			String firstName, String lastName, String email, String contactNo, String address) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNo = contactNo;
		this.address = address;
	}

	public User(
			@Length(min = 5, message = "*Your user name must have at least 5 characters") @NotEmpty(message = "*Please enter your username") String userName,
			@Length(min = 8, message = "*Your password must have at least 8 characters") @NotEmpty(message = "*Please enter your password") String password,
			String firstName, String lastName, String email, String contactNo, String address) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNo = contactNo;
		this.address = address;
	}


}

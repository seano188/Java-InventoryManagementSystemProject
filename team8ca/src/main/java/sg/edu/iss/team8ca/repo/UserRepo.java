package sg.edu.iss.team8ca.repo;

import sg.edu.iss.team8ca.model.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    
    
    @Query("SELECT i FROM User i WHERE i.firstName LIKE %?1%"
			+ "OR i.userName LIKE %?1%"
			+ "OR i.lastName LIKE %?1%"
			+ "OR i.email LIKE %?1%"
			+ "OR i.contactNo LIKE %?1%"
			+ "OR i.address LIKE %?1%")	
	public Page<User> findBykeywordContaining(String keyword, Pageable pageable);

}
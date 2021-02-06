package sg.edu.iss.team8ca.repo;

import sg.edu.iss.team8ca.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}
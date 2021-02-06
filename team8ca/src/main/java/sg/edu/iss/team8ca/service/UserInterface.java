package sg.edu.iss.team8ca.service;

import java.util.List;

import org.springframework.data.domain.Page;

import sg.edu.iss.team8ca.model.*;

public interface UserInterface {
	
	public void deleteUser(User user);
	public List<User> findAllUser();
	public User findUserById(Long id);
	public User saveUser(User user);
	public User findUserByUserName(String userName);
	public Page<User> findBykeywordContaining(String keyword, int pageNo, int pageSize, String sortField,
			String sortDirection);
	
}


package com.vesmer.web.timontey.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.domain.User;
import com.vesmer.web.timontey.repository.RoleRepository;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.repository.UserRepository;
import com.vesmer.web.timontey.service.UserService;

@Service
@Transactional
public class UserSeviceImp implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private RoleRepository roleRepository;
		
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<User> getAll() {
		List<User> userList = userRepository.getAll();
		for (int i = 0; i < userList.size(); ++i) {
			User user = userList.get(i);

			long employeeId = user.getId();
			Optional<Employee> optEmployee = staffRepository.findById(employeeId);
			if (optEmployee.isPresent()) {
				Employee employee = optEmployee.get();
				user.setEmployee(employee);
			} else {
				user.setFirstName("not found");
			}

			List<Role> roles = roleRepository.getRolesForUser(user);
			user.setRoles(roles);
		}

		return userList;
	}

	@Override
	public User save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);

		saveRolesToUser(user, user.getRoles());
		
		User fullUser = getUserByUsername(user.getUsername()).get();
		return fullUser;
	}
	
	@Override
	public Optional<User> getUserByUsername(String username) {
		Optional<User> optUser = userRepository.getUserByUsername(username);

		if (optUser.isPresent()) {
			User user = optUser.get();

			long employeeId = user.getId();
			Optional<Employee> optEmployee = staffRepository.findById(employeeId);
			if (optEmployee.isPresent()) {
				Employee employee = optEmployee.get();
				user.setEmployee(employee);
			} else {
				user.setLastName("not found");
			}

			List<Role> roles = roleRepository.getRolesForUser(user);
			user.setRoles(roles);
		}

		return optUser;
	}
	
	@Override
	public User update(User user) {
		userRepository.update(user);
		
		roleRepository.deleteRolesFromUser(user.getUsername());
		saveRolesToUser(user, user.getRoles());

		User fullUser = getUserByUsername(user.getUsername()).get();
		return fullUser;
	}
	
	private void saveRolesToUser(User user, List<Role> roles) {
		for (int i = 0; i < roles.size(); ++i) {
			roleRepository.saveRoleToUser(user, roles.get(i));
		}
	}

	@Override
	public void delete(String username) {
		try {
			roleRepository.deleteRolesFromUser(username);
			userRepository.delete(username);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Delete failing: " + e.getMessage());
		}
	}

}

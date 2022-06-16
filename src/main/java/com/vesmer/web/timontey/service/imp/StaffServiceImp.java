package com.vesmer.web.timontey.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.service.StaffService;

@Service
@Transactional
public class StaffServiceImp implements StaffService {
	private final StaffRepository staffRepository;

	@Autowired
	public StaffServiceImp(StaffRepository staffRepository) {
		this.staffRepository = staffRepository;
	}

	@Override
	public List<Employee> getAll() {
		return staffRepository.getAll();
	}
	
	@Override
	public List<Employee> getEmployeesByRole(long roleId) {
		return staffRepository.getEmployeesByRole(roleId);
	}

	@Override
	public Employee save(Employee employee) {
		long id = staffRepository.save(employee);
		employee.setId(id);
		return employee;
	}

	@Override
	public Employee update(Employee employee) {
		staffRepository.update(employee);
		return employee;
	}

	@Override
	public void delete(long id) {
		try {
			staffRepository.delete(id);
		} catch (EmptyResultDataAccessException ex) {
			System.out.println("Delete failing: " + ex.getMessage());
		}
	}

	@Override
	public Optional<Employee> getEmployeeById(long id) {
		return staffRepository.findById(id);
	}
}

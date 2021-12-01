package com.vesmer.web.timontey.service;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.Employee;

public interface StaffService {

	List<Employee> getAll();

	Employee save(Employee employee);

	Employee update(Employee employee);

	void delete(long id);

	Optional<Employee> getEmployeeById(long id);

	List<Employee> getEmployeesByRole(long roleId);

}

package com.vesmer.web.timontey.repository;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.Employee;

public interface StaffRepository {

	List<Employee> getAll();

	long save(Employee employee);

	int update(Employee employee);

	int delete(long id);

	Optional<Employee> findById(long id);

	List<Employee> getEmployeesByRole(long roleId);

}

package com.vesmer.web.timontey.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.service.StaffService;

@Service
@Transactional
public class StaffServiceImp implements StaffService {
	@Autowired
	private StaffRepository staffRepository;

	@Override
	public List<Employee> getAll() {
		return staffRepository.getAll();
	}

	@Override
	public Employee save(Employee emloyee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee update(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Employee> getEmployeeById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.vesmer.web.timontey.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vesmer.web.timontey.domain.Role;
import com.vesmer.web.timontey.repository.RoleRepository;
import com.vesmer.web.timontey.service.RoleService;

@Service
@Transactional
public class RoleServiceImp implements RoleService {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleServiceImp(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<Role> getAll() {
		return roleRepository.getAll();
	}
}

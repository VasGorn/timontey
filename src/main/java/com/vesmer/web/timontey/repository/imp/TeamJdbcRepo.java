package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.domain.Team;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.repository.TeamRepository;
import com.vesmer.web.timontey.rowmapper.EmployeeRowMapper;

@Repository
public class TeamJdbcRepo implements TeamRepository {
	private final JdbcTemplate jdbcTemplate;
	private final StaffRepository staffRepository;

	@Autowired
	public TeamJdbcRepo(JdbcTemplate jdbcTemplate, StaffRepository staffRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.staffRepository = staffRepository;
	}

	private static final String SELECT_EMPLOYEES_BY_MASTER_ID_SQL = 
		"SELECT s.id, s.last_name, s.first_name, s.middle_name "
		+ "FROM staff AS s, master_employees AS me "
		+ "WHERE s.id = me.employee_id AND me.master_id = ?;";
	private static final String INSERT_EMPLOYEE_TO_PERFORMER_SQL = 
		"INSERT INTO master_employees (master_id, employee_id) "
		+ "VALUES (?, ?);";
	private static final String DELETE_EMPLOYEE_FROM_PERFORMER_SQL = 
		"DELETE FROM master_employees WHERE master_id = ? AND employee_id = ?;";

	@Override
	public Team getTeam(long performerId) {
		List<Employee> teamList = 
				(List<Employee>) jdbcTemplate.query(SELECT_EMPLOYEES_BY_MASTER_ID_SQL,
				new EmployeeRowMapper(), performerId);
		Employee performer = staffRepository.findById(performerId).get();

		Team team = new Team();
		team.setPerformer(performer);
		team.setEmployeeList(teamList);
		return team;
	}
	
	@Override
	public Team saveEmployeeToPerformer(long performerId, long employeeId) {
		jdbcTemplate.update(INSERT_EMPLOYEE_TO_PERFORMER_SQL, performerId, employeeId);

		List<Employee> teamList = 
				(List<Employee>) jdbcTemplate.query(SELECT_EMPLOYEES_BY_MASTER_ID_SQL,
				new EmployeeRowMapper(), performerId);
		Employee performer = staffRepository.findById(performerId).get();

		Team team = new Team();
		team.setPerformer(performer);
		team.setEmployeeList(teamList);
		return team;
	}
	
	@Override
	public int delete(long performerId, long employeeId) {
		return jdbcTemplate.update(DELETE_EMPLOYEE_FROM_PERFORMER_SQL, performerId,
				employeeId);
	}
}

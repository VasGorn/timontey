package com.vesmer.web.timontey.repository.imp;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.Employee;
import com.vesmer.web.timontey.repository.StaffRepository;
import com.vesmer.web.timontey.rowmapper.EmployeeRowMapper;

@Repository
public class StaffJdbcRepo implements StaffRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StaffJdbcRepo(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final String SELECT_ALL_SQL	= "SELECT * FROM staff;";
	private static final String INSERT_SQL		= "INSERT INTO staff (last_name, first_name, middle_name) "
												   + "VALUES (?, ?, ?);";
	private static final String UPDATE_SQL		= "UPDATE staff SET last_name=?, first_name=?, middle_name=? "
												   + "WHERE id=?;";
	private static final String DELETE_SQL		= "DELETE FROM staff WHERE id=?;";
	private static final String SELECT_ONE_SQL	= "SELECT * FROM staff WHERE id=?;";
	private static final String SELECT_EMPLOYEE_BY_ROLE_ID_SQL = 
		"SELECT s.id, s.last_name, s.first_name, s.middle_name "
		+ "FROM staff AS s, "
		+ "users AS u, "
		+ "user_role AS ur "
		+ "WHERE s.id = u.employee_id"
		+ "  AND u.username = ur.username"
		+ "  AND ur.role_id = ?;";

	@Override
	public List<Employee> getAll() {
		List<Employee> list = (List<Employee>) jdbcTemplate.query(SELECT_ALL_SQL,
				new EmployeeRowMapper());
		System.out.println(Arrays.toString(list.toArray()));
		return list;
	}
	
	@Override
	public List<Employee> getEmployeesByRole(long roleId) {
		List<Employee> list = (List<Employee>) jdbcTemplate.query(
				SELECT_EMPLOYEE_BY_ROLE_ID_SQL,
				new EmployeeRowMapper(), roleId
		);
		return list;
	}

	@Override
	public long save(Employee employee) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
	          ps.setString(1, employee.getLastName());
	          ps.setString(2, employee.getFirstName());
	          ps.setString(3, employee.getMiddleName());
	          return ps;
	        }, keyHolder);
	    
	    long newId;
	    
	    if (keyHolder.getKeys().size() > 1) {
	        newId = ((Number) keyHolder.getKeys().get("id")).longValue();
	    } else {
	        newId= keyHolder.getKey().longValue();
	    }

        return newId;
	}

	@Override
	public int update(Employee employee) {
		return jdbcTemplate.update(UPDATE_SQL, employee.getLastName(), 
				employee.getFirstName(), employee.getMiddleName(), employee.getId());
	}

	@Override
	public int delete(long id) {
		return jdbcTemplate.update(DELETE_SQL, id);
	}

	@Override
	public Optional<Employee> findById(long id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_ONE_SQL, 
				new EmployeeRowMapper(), id));
		} catch (EmptyResultDataAccessException ex){
			return Optional.empty();
		}
	}
}

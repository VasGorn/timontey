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

import com.vesmer.web.timontey.domain.Expenses;
import com.vesmer.web.timontey.repository.ExpensesRepository;
import com.vesmer.web.timontey.rowmapper.ExpensesRowMapper;

@Repository
public class ExpensesJdbcRepo implements ExpensesRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ExpensesJdbcRepo(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final String SELECT_ALL_SQL	= "SELECT * FROM expenses;";
	private static final String INSERT_SQL		= "INSERT INTO expenses (name) VALUES "
												   + "(?);";
	private static final String UPDATE_SQL		= "UPDATE expenses SET name=? "
												   + "WHERE id=?;";
	private static final String DELETE_SQL		= "DELETE FROM expenses WHERE id=?;";
	private static final String SELECT_ONE_SQL	= "SELECT * FROM expenses WHERE id=?;";

	@Override
	public List<Expenses> getAll() {
		List<Expenses> list = (List<Expenses>) jdbcTemplate.query(SELECT_ALL_SQL,
				new ExpensesRowMapper());
		System.out.println(Arrays.toString(list.toArray()));
		return list;
	
	}

	@Override
	public long save(Expenses expenses) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
	          ps.setString(1, expenses.getName());
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
	public int update(Expenses expenses) {
		return jdbcTemplate.update(UPDATE_SQL, expenses.getName(), expenses.getId());
	}

	@Override
	public Optional<Expenses> findById(Long id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_ONE_SQL, 
				new ExpensesRowMapper(), id));
		} catch (EmptyResultDataAccessException ex){
			return Optional.empty();
		}
	}

	@Override
	public int delete(Long id) {
		return jdbcTemplate.update(DELETE_SQL, id);
	}
}

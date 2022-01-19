package com.vesmer.web.timontey.repository.imp;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.QuotaMoney;
import com.vesmer.web.timontey.repository.QuotaMoneyRepository;
import com.vesmer.web.timontey.rowmapper.QuotaMoneyRowMapper;

@Repository
public class QuotaMoneyJdbcRepo implements QuotaMoneyRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECT_QUOTA_MONEY_BY_MANAGER_ID_SQL = 
		"SELECT q.id, q.order_id, q.employee_id, "
		+ "q.money_limit FROM quota_money AS q, "
		+ "orders AS o "
		+ "WHERE o.id=q.order_id AND "
		+ "o.manager_id=?;";
	private static final String INSERT_QUOTA_MONEY_SQL	= 
			"INSERT INTO quota_money (order_id, employee_id, "
			+ "money_limit) VALUES (?, ?, ?);";
	private static final String UPDATE_QUOTA_MONEY_SQL	= 
			"UPDATE quota_money SET money_limit=? "
			+ "WHERE id=?;";
	private static final String DELETE_QUOTA_MONEY_SQL	= 
			"DELETE FROM quota_money WHERE id=?;";
	private static final String SELECT_QUOTA_MONEY_BY_ID_SQL = 
			"SELECT * FROM quota_money  "
			+ "WHERE id = ?;";
	private static final String SELECT_QUOTA_MONEY_BY_EMPLOYEE_ID_SQL = 
			"SELECT * FROM quota_money  "
			+ "WHERE employee_id = ?;";

	@Override
	public List<QuotaMoney> getQuotaMoneysForManager(long managerId) {
		return jdbcTemplate.query(SELECT_QUOTA_MONEY_BY_MANAGER_ID_SQL, 
				new QuotaMoneyRowMapper(), managerId);
	}
	
	@Override
	public int update(QuotaMoney quotaMoney) {
		return jdbcTemplate.update(UPDATE_QUOTA_MONEY_SQL, quotaMoney.getMoneyLimit(), quotaMoney.getId());
	}

	@Override
	public long save(QuotaMoney quotaMoney) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(INSERT_QUOTA_MONEY_SQL, Statement.RETURN_GENERATED_KEYS);
	          ps.setLong(1, quotaMoney.getOrder().getId());
	          ps.setLong(2, quotaMoney.getEmployee().getId());
	          ps.setFloat(3, quotaMoney.getMoneyLimit());
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
	public void delete(long quotaMoneyId) {
		jdbcTemplate.update(DELETE_QUOTA_MONEY_SQL, quotaMoneyId);
	}

	@Override
	public Optional<QuotaMoney> findQuotaMoneyById(long id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_QUOTA_MONEY_BY_ID_SQL, 
				new QuotaMoneyRowMapper(), id));
		} catch (EmptyResultDataAccessException ex){
			return Optional.empty();
		}
	}

	@Override
	public List<QuotaMoney> getQuotaMoneysForEmployee(long employeeId) {
		return jdbcTemplate.query(SELECT_QUOTA_MONEY_BY_EMPLOYEE_ID_SQL,
				new QuotaMoneyRowMapper(), employeeId);
	}

}

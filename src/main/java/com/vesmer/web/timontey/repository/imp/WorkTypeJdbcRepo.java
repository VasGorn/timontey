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

import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.repository.WorkTypeRepository;
import com.vesmer.web.timontey.rowmapper.WorkTypeRowMapper;

@Repository
public class WorkTypeJdbcRepo implements WorkTypeRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public WorkTypeJdbcRepo(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final String SELECT_WORKTYPE_BY_ROLE_ID_SQL	= "SELECT w.id, w.work_name "
																+ "FROM work_types AS w, "
																+ "roles AS r, "
																+ "role_worktype AS rw "
																+ "WHERE w.id = rw.worktype_id"
																+ "  AND r.id = rw.role_id"
																+ "  AND r.id = ?;";
	private static final String INSERT_SQL						= "INSERT INTO work_types (work_name) VALUES (?);";
	private static final String INSERT_LINK_TO_ROLE_SQL			= "INSERT INTO role_worktype (worktype_id, role_id) "
																+ "VALUES (?, ?);";
	private static final String DELETE_SQL						= "DELETE FROM work_types WHERE id = ?;";
	private static final String DELETE_LINK_FROM_ROLE_SQL		= "DELETE FROM role_worktype WHERE worktype_id = ? "
																+ "AND role_id = ?;";
	private static final String SELECT_BY_ID_SQL				= "SELECT * FROM work_types WHERE id = ?;";
	private static final String UPDATE_SQL						= "UPDATE work_types SET work_name = ? WHERE id = ?;";
	
	@Override
	public List<WorkType> getWorkTypesByRole(long roleId) {
		List<WorkType> list = (List<WorkType>) jdbcTemplate.query(SELECT_WORKTYPE_BY_ROLE_ID_SQL,
				new WorkTypeRowMapper(), roleId);
		return list;
	}
	
	@Override
	public long save(long roleId, WorkType workType) {
		long workTypeId = insertWorkType(workType);
		jdbcTemplate.update(INSERT_LINK_TO_ROLE_SQL, workTypeId, roleId);
		return workTypeId;
	}
	
	private long insertWorkType(WorkType workType) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
	          ps.setString(1, workType.getWorkTypeName());
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
	public int delete(Long roleId, Long workTypeId) {
		jdbcTemplate.update(DELETE_LINK_FROM_ROLE_SQL, workTypeId, roleId);
		return jdbcTemplate.update(DELETE_SQL, workTypeId);
	}

	@Override
	public Optional<WorkType> findById(long id) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, 
				new WorkTypeRowMapper(), id));
		} catch (EmptyResultDataAccessException ex){
			return Optional.empty();
		}
	}

	@Override
	public int update(WorkType workType) {
		return jdbcTemplate.update(UPDATE_SQL, workType.getWorkTypeName(), workType.getId());
	}
}

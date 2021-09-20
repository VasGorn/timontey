package com.vesmer.web.timontey.repository.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vesmer.web.timontey.domain.WorkType;
import com.vesmer.web.timontey.repository.WorkTypeRepository;
import com.vesmer.web.timontey.rowmapper.WorkTypeRowMapper;

@Repository
public class WorkTypeJdbcRepo implements WorkTypeRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String SELECT_WORKTYPE_BY_ROLE_ID_SQL	= "SELECT w.id, w.work_name "
																+ "FROM work_types AS w, "
																+ "roles AS r, "
																+ "role_worktype AS rw "
																+ "WHERE w.id = rw.worktype_id"
																+ "  AND r.id = rw.role_id"
																+ "  AND r.id = ?;";

	@Override
	public List<WorkType> getWorkTypesByRole(long roleId) {
		List<WorkType> list = (List<WorkType>) jdbcTemplate.query(SELECT_WORKTYPE_BY_ROLE_ID_SQL,
				new WorkTypeRowMapper(), roleId);
		return list;
	}

}

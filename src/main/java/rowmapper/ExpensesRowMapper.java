package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import domain.Expenses;

public class ExpensesRowMapper implements RowMapper<Expenses> {
	@Override
	public Expenses mapRow(ResultSet rs, int rowNum) throws SQLException {
		Expenses expenses = new Expenses();
		expenses.setId(rs.getLong("id"));
		expenses.setName(rs.getString("name"));
		return expenses;
	}
}

package repository.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import domain.Expenses;
import repository.ExpensesRepository;

@Repository
public class ExpensesJdbcRepo implements ExpensesRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Expenses> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long save(Expenses expenses) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Expenses expenses) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<Expenses> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}

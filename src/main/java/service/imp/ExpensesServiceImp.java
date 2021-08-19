package service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Expenses;
import service.ExpensesService;

@Service
@Transactional
public class ExpensesServiceImp implements ExpensesService {
	@Autowired
	private ExpensesRepository expensesRepository;

	@Override
	public List<Expenses> getAll() {
		return expensesRepository.getAll();
		return null;
	}

	@Override
	public Expenses save(Expenses expenses) {
		long id = expensesRepository.save(expenses);
		expenses.setId(id);
		return expenses;
	}

	@Override
	public Expenses update(Expenses expenses) {
		expensesRepository.update(expenses);
		return expenses;
	}

	@Override
	public Optional<Expenses> getExpensesById(Long id) {
		return expensesRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		try {
			expensesRepository.delete(id);
		} catch (EmptyResultDataAccessException ex) {
			System.out.println("Delete failing" + ex.getMessage());
		}
	}

}

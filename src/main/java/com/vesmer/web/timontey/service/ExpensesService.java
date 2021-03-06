package com.vesmer.web.timontey.service;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.Expenses;

public interface ExpensesService {

	List<Expenses> getAll();

	Expenses save(Expenses expenses);

	Expenses update(Expenses expenses);

	Optional<Expenses> getExpensesById(Long id);

	void delete(Long id);

}

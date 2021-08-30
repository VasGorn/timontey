package com.vesmer.web.timontey.repository;

import java.util.List;
import java.util.Optional;

import com.vesmer.web.timontey.domain.Expenses;

public interface ExpensesRepository {

	List<Expenses> getAll();

	long save(Expenses expenses);

	int update(Expenses expenses);

	Optional<Expenses> findById(Long id);

	int delete(Long id);

}

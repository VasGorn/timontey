package com.vesmer.web.timontey.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vesmer.web.timontey.domain.Expenses;
import com.vesmer.web.timontey.service.ExpensesService;

@RestController
@RequestMapping(path = "/rest/expenses", produces = "application/json")
public class ExpensesRest {
	@Autowired
	private ExpensesService expensesService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Expenses> expensesById(@PathVariable("id") Long id) {
		Optional<Expenses> optExpenses = expensesService.getExpensesById(id);
		System.out.println("Expenses find: " + optExpenses);
		if(optExpenses.isPresent()) {
			return new ResponseEntity<Expenses>(optExpenses.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/all")
	public List<Expenses> expensesGetAll(){
		return expensesService.getAll();
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Expenses postExpenses(@RequestBody Expenses expenses) {
		return expensesService.save(expenses);
	}
	
	@PutMapping("/{id}")
	public Expenses putExpenses(@RequestBody Expenses expenses) {
		return expensesService.update(expenses);
	}
	
	@PatchMapping(path = "/{id}", consumes = "application/json")
	public Expenses patchExpenses(@PathVariable("id") Long id, @RequestBody Expenses patch){
		Expenses expenses = expensesService.getExpensesById(id).get();
		if(patch.getName() != null) {
			expenses.setName(patch.getName());
		}
		return expensesService.update(expenses);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteExpenses(@PathVariable("id") Long id) {
		expensesService.delete(id);
	}

}

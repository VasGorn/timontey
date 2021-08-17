package controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Expenses;

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

}
